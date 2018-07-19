package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class MSC_LONGS_MAIN {
	public static void main(String[] args) {
		
		Random rand = new Random();
//		GeneticHelperMethods ghm1 = new GeneticHelperMethods();
//		RandomFunctionBuilder rfb = new RandomFunctionBuilder(1600,40);
//		String funcString = "XOR21,10#LRO9,20#LRO11,23#XOR19,2#XOR6,18#LRO14,29#XOR6,12#XOR22,15#XOR24,23#LRO11,48#XOR21,23#XOR8,1#XOR4,13#XOR2,21#LRO9,12#XOR11,24#XOR7,12#XOR7,16#XOR4,18#XOR11,19#XOR2,22#LRO12,34#XOR2,20#XOR23,6#XOR12,4#LRO20,25#LRO7,34#XOR8,0#LRO5,29#XOR18,5#XOR11,23#XOR22,7#LRO18,44#XOR19,12#XOR1,13#LRO22,21#LRO10,48#XOR8,22#XOR0,19#XOR24,22#";
//		RoundFunction f1 = new ModularRoundFunction(1600, funcString);
//		ModularSpongeConstruction_Longs msc = new ModularSpongeConstruction_Longs(256, 1600-256, 1600, f1);
//		
//		System.out.println(funcString);
//		
//		long[] message = new long[25];
//		long[] messageFlipped = new long[25];
//		for(int i = 0; i<25; i++) {
//			message[i] = rand.nextLong();
//			
//		}
//		messageFlipped = ghm1.flipRand(message);
//		msc.spongeAbsorb(message);
//		String s1 = msc.spongeSqueeze(1);
//		msc.spongePurge();
//		msc.spongeAbsorb(messageFlipped);
		
		//Direct port of STRINGS GA code
		
		//CONFIGURATION
				int _popSize = 128;
				int _funcCount = 40;
				double _populationDieOffPercent = 0.50; //A higher value is more selective and less diverse, a lower value is the opposite
				double _mutationChance = 0.34;	//A higher value will increase the chance of random mutation in offspring
				int _preserveTopNIndividuals = 4;
				int _generationCount = 100;
				int _aggressiveThreshold = 100;
				//adding -p will enable parameter entry
				try {
					if(args[0].equals("-p")) {
						Scanner s = new Scanner(System.in);
						System.out.println("Parameters:");
						System.out.print("popSize=");
						_popSize = s.nextInt();
						System.out.print("\nfuncCount=");
						_funcCount = s.nextInt();
						System.out.print("\npopulationDieOffPercent=");
						_populationDieOffPercent = s.nextDouble();
						System.out.print("\nmutationChance=");
						_mutationChance = s.nextDouble();
						System.out.print("\npreserveTopNIndividuals=");
						_preserveTopNIndividuals = s.nextInt();
						System.out.print("\ngenerationCount=");
						_generationCount = s.nextInt();
						System.out.print("\naggressiveThreshold=");
						_aggressiveThreshold = s.nextInt();
						s.close();
					}/*else if(args[0].equals("-t")){
						try {
							testFunction(args[1]);
						}catch (Exception e){
							System.out.println("Error: usage of -t flag is Configurable_GA.jar -t functionString");
						}
						
						return;
					}else if(args[0].equals("-rs")){
					try {
						generateTestRandDataSqueeze(args[1], Integer.parseInt(args[2]));
					}catch (Exception e){
						System.out.println("Error: usage of -rs flag is Configurable_GA.jar -rs functionString iterations");
					}
						return;
					}else if(args[0].equals("-rh")) {
						try {
							generateTestRandDataHash(args[1], Integer.parseInt(args[2]));
						}catch (Exception e){
							System.out.println("Error: usage of -rh flag is Configurable_GA.jar -rh functionString iterations");
						}
						return;
					}else if(args[0].equals("-rhb")) {
						try {
							generateTestRandDataHashBinary(args[1], Integer.parseInt(args[2]));
						}catch (Exception e){
							System.out.println("Error: usage of -rhb flag is Configurable_GA.jar -rh functionString iterations");
							e.printStackTrace();
						}
						return;
					}*/else if(args[0].equals("-h")) {
						System.out.println("-p : start a parameterized run of the GA");
						System.out.println("-t functionString : test the function described by functionString");
						System.out.println("-rs functionString iterations : generate pseudorandom data from function described by functionString by XOF, squeezing [iterations] times");
						System.out.println("-rh functionString iterations : generate pseudorandom data from function described by functionString using hashing of low entropy inputs, outputs [iterations] hashes");
						System.out.println("-rhb functionString iterations : generate pseudorandom data from function described by functionString using hashing of low entropy inputs, outputs [iterations] hashes. Output stored as binary data.");
						
					}
				}catch(Exception e) {
					
				}
				final double bitchangeLowerBoundAutostop = 0.4995;
				final double bitchangeUpperBoundAutostop = 0.54;
				final int popSize = _popSize;
				final int aggressiveThreshold = _aggressiveThreshold;
				final int messageCount = 8192;
				final int messageLenLongs = 4;
				final int funcCount = _funcCount;
				final int stateSize = 1600;
				final int rate = 256;
				final int capacity = 1600-rate;
				final double populationDieOffPercent = _populationDieOffPercent; //A higher value is more selective and less diverse, a lower value is the opposite
				final double mutationChance = _mutationChance;	//A higher value will increase the chance of random mutation in offspring
				final int preserveTopNIndividuals = _preserveTopNIndividuals;
				final int generationCount = _generationCount;
				boolean aggressiveMode = false;
				double[] lastScores = new double[aggressiveThreshold];
				
				//RANDOM GENERATION OF INITIAL POPULATION
				RandomFunctionBuilder functionBuilder = new RandomFunctionBuilder(stateSize,funcCount);
				String[] functionStringPop = new String[popSize];
				ModularRoundFunction[] functionPop = new ModularRoundFunction[popSize];
				ModularSpongeConstruction_Longs[] spongeArray = new ModularSpongeConstruction_Longs[popSize];
				ModularSpongeConstruction_Longs[] spongeArrayReserve = new ModularSpongeConstruction_Longs[popSize];
				long[][] messages = new long[messageCount][messageLenLongs];
				long[][] messagesFlipped = new long[messageCount][messageLenLongs];
				for(int i = 0; i < functionPop.length; i++) {
					//System.out.println(i+":");
					functionStringPop[i] = functionBuilder.genFuncString();
					functionPop[i] = new ModularRoundFunction(stateSize, functionStringPop[i]);
					spongeArray[i] = new ModularSpongeConstruction_Longs(rate,capacity,stateSize, functionPop[i]);
				}
				//GENERATION OF MESSAGE SET
				GeneticHelperMethods ghm = new GeneticHelperMethods();
				int messageDifferenceCounter = 0;
				for(int i = 0 ; i < messageCount; i++) {
					messages[i]=ghm.generateMersenneRandomString(messageLenLongs);
					messagesFlipped[i]=ghm.flipRand(messages[i]);
					for(int j = 0; j < messageLenLongs; j++) {
						if(messages[i][j]!=messagesFlipped[i][j]) {
							messageDifferenceCounter++;
						}
					}
				}
				
				System.out.println("Generated "+messageDifferenceCounter+" distinct messages");
				
				System.out.println("Population ready, running generations...");
				double[] topScores = new double[generationCount];
				double[] topBitchange = new double[generationCount];
				
				
				
				
				Date runStart = new Date();
				long runStartTime = runStart.getTime();
				//Hook exit to runtime end
				Runtime.getRuntime().addShutdownHook(new Thread() {
					public void run() {
						
						Date runEnd = new Date();
						try {
							System.out.println("Run Ended.");
							PrintWriter finalPopulationWriter = new PrintWriter("FinalRunPopulation"+runEnd.getTime()+".log");
							PrintWriter dataWriter = new PrintWriter("RunData"+runEnd.getTime()+".csv");
							finalPopulationWriter.println("Parameters:");
							finalPopulationWriter.println("popSize: "+popSize);
							finalPopulationWriter.println("messageCount: "+messageCount);
							finalPopulationWriter.println("messageLength: "+messageLenLongs);
							finalPopulationWriter.println("funcCount: "+funcCount);
							finalPopulationWriter.println("stateSize: "+stateSize);
							finalPopulationWriter.println("rate: "+rate);
							finalPopulationWriter.println("capacity: "+capacity);
							finalPopulationWriter.println("populationDieOffPercent: "+populationDieOffPercent);
							finalPopulationWriter.println("mutationChance: "+mutationChance);
							finalPopulationWriter.println("preserveTopNIndividuals: "+preserveTopNIndividuals);
							finalPopulationWriter.println("generationCount: "+generationCount);
							for(int i = 0; i < popSize; i++){
								finalPopulationWriter.print(Integer.toString(i)+"	:\n");
								finalPopulationWriter.print("Fitness Score:	"+spongeArrayReserve[i].geneticScore+"\n");
								finalPopulationWriter.print("Bitchange:	"+(spongeArrayReserve[i].bitchangeScore)+"\n");
								finalPopulationWriter.print("Round Function:\n");
								finalPopulationWriter.print(spongeArrayReserve[i].f.getFunc()+"\n");
								finalPopulationWriter.print("=================================================================\n\n");
							}
							for(int i = 0; i < generationCount; i++) {
								dataWriter.print(i+","+topScores[i]+","+topBitchange[i]+"\n");
							}
							
							System.out.println("File output succeeded, output saved to "+"FinalRunPopulation"+runEnd.getTime()+".log"+" and "+"RunData"+runEnd.getTime()+".csv");
							finalPopulationWriter.close();
							dataWriter.close();
						} catch (IOException e1) {
							System.out.println("File output failed.");
							e1.printStackTrace();
						}
						
						long runEndTime = runEnd.getTime();
						System.out.println("Run completed. Run time elapsed: "+ghm.millisToTimestamp(runEndTime-runStartTime));
					}
				});
				double incrementedMutationChance = mutationChance;
				
				//Run GA
				int lastScoresIterator = 0;
				for(int generation= 0; generation < generationCount-1; generation++) {
					Date dateStart = new Date();
					long startTime = dateStart.getTime();
					ghm.multithreadScore(spongeArray, messages, messagesFlipped, popSize, messageCount);
					ghm.sortPopulationArray(spongeArray);
					for(int i = 0 ; i < popSize; i++) {
						spongeArrayReserve[i] = spongeArray[i];
					}
					lastScores[lastScoresIterator] = spongeArray[popSize-1].geneticScore;
					lastScoresIterator++;
					if(lastScoresIterator == aggressiveThreshold){
						lastScoresIterator = 0;
					}
						double scoreTotal = 0;
						for(double d:lastScores) {
							scoreTotal+=d;
						}
						if(lastScores[0]*aggressiveThreshold==scoreTotal) {
							aggressiveMode = true;
							System.out.println("Stagnant run detected, breaking");
							System.exit(1);
						}else {
							if(aggressiveMode) {
								aggressiveMode = false;
								System.out.println("!!!AGGRESSIVE GROWTH DISENGAGED!!!");
							}
						}
					
					Date dateEnd = new Date();
					long endTime = dateEnd.getTime();
					System.out.println("Generation	"+generation+"	completed.");
					System.out.println("Generation runtime: "+ghm.millisToTimestamp(endTime-startTime));
					System.out.println("Average individual runtime:	"+ghm.millisToTimestamp((long)((endTime-startTime)/popSize)));
					System.out.println("Best of gen:	"+spongeArray[popSize-1].geneticScore);
					System.out.println("Max bitchange(+/-):	"+(0.50-(1/spongeArray[popSize-1].geneticScore)));
					topScores[generation] = spongeArray[popSize-1].geneticScore;
					topBitchange[generation] = spongeArray[popSize-1].bitchangeScore;
					System.out.println("Projected remaining runtime: "+ghm.millisToTimestamp((long)(endTime-startTime)*(generationCount-generation)));
					if(aggressiveMode) {
						incrementedMutationChance+=0.01;
						if(incrementedMutationChance == 1) {
							incrementedMutationChance = mutationChance;
						}
						ghm.runGenerationOnSortedPopulation(spongeArray, populationDieOffPercent,incrementedMutationChance, preserveTopNIndividuals);
						
					}else {
						incrementedMutationChance = mutationChance;
						ghm.runGenerationOnSortedPopulation(spongeArray, populationDieOffPercent, mutationChance, preserveTopNIndividuals);
					}
					
					
					if(topBitchange[generation]<bitchangeUpperBoundAutostop&&topBitchange[generation]>bitchangeLowerBoundAutostop) {
						System.out.println("Target bitchange detected! Autostop!");
						break;
					}
					
				}
				//Score the last generation
				ghm.multithreadScore(spongeArray, messages, messagesFlipped, popSize, messageCount);
				ghm.sortPopulationArray(spongeArray);
				System.out.println("Generation	"+(generationCount-1)+"	completed.");
				System.out.println("Best of gen:	"+spongeArray[popSize-1].geneticScore);
				System.out.println("Max bitchange:	"+(spongeArray[popSize-1].bitchangeScore));
				topScores[generationCount-1] = spongeArray[popSize-1].geneticScore;
				topBitchange[generationCount-1] = spongeArray[popSize-1].bitchangeScore;
				System.out.println(topBitchange[generationCount-1]);
				for(int i = 0 ; i < popSize; i++) {
					spongeArrayReserve[i] = spongeArray[i];
				}
		
	}
}
