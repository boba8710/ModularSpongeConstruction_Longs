package main;
import java.util.ArrayList;

import main.CONSTANTS;

public class ModularSpongeConstruction_Longs {
	long[] state;
	int rateLongs;
	static int rate;
	static int stateSize;
	static int capacity;
	int capacityLongs;
	double geneticScore;
	double bitchangeScore;
	RoundFunction f;
	ModularSpongeConstruction_Longs(int rate, int capacity, int stateSize, RoundFunction f){
		this.rate = rate;
		assert rate % CONSTANTS.longSize == 0;
		this.rateLongs = rate/CONSTANTS.longSize;
		
		this.capacity = capacity;
		assert capacity % CONSTANTS.longSize == 0;
		this.capacityLongs = capacity/CONSTANTS.longSize;
		assert rate+capacity == stateSize;
		this.stateSize = stateSize;
		this.state = new long[stateSize/CONSTANTS.longSize];
		for(int i = 0 ; i < stateSize/CONSTANTS.longSize; i++) {
			state[i]=0;
		}
		this.f = f;
	}
	private void xorIntoState(long[] messageRateChunk) {
		for(int i = 0; i < rateLongs; i++) {
			state[i]=state[i]^messageRateChunk[i];
		}
	}
	public void spongeAbsorb(long[] message) {
		ArrayList<Long> messageArrayList = new ArrayList<Long>();
		for(long l : message) {
			messageArrayList.add(l);
		}
		long[] messageRateChunk = new long[rateLongs];
		while(!messageArrayList.isEmpty()) {
			 if(messageArrayList.size() > rateLongs) {
				 for(int i = 0; i < rateLongs; i++) {
					 messageRateChunk[i]=messageArrayList.get(i);
				 }
				 for(int i = 0; i < rateLongs; i++) {
					 messageArrayList.remove(0);
				 }
				 xorIntoState(messageRateChunk);
			 }else {
				 while(messageArrayList.size() < rateLongs) {
					messageArrayList.add(0L); 
				 }
				 xorIntoState(messageRateChunk);
				 messageArrayList.clear();
			 }
			 runRoundFunctionOnState();
		}
	}
	public String spongeSqueeze(int iterations) {
		String retString = "";
		for(int iteration = 0 ; iteration < iterations; iteration++) {
			for(int i = 0 ; i < rateLongs; i++) {
				String rateChunk=Long.toBinaryString(state[i]+(0x7ffffffffffffffL));
				while(rateChunk.length() < 64){
					rateChunk="0"+rateChunk;
				}
				retString+=rateChunk;
			}
			retString+="\n";
			runRoundFunctionOnState();
		}
		return retString;
	}
	private void runRoundFunctionOnState() {
		state = f.runFunction(state);
	}
	public void spongePurge(){
		for(int i = 0 ; i < stateSize/CONSTANTS.longSize; i++) {
			state[i]=0;
		}
	}
	private void printState() {
		System.out.print("\n{");
		for(int i = 0; i < state.length-1; i++) {
			System.out.print(state[i]+", ");
		}
		System.out.print(state[state.length-1]);
		System.out.println("}");
	}
}
