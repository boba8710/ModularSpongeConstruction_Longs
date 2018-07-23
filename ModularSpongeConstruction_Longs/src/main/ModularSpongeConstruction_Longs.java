package main;
import java.util.ArrayList;

import main.CONSTANTS;

public class ModularSpongeConstruction_Longs {
	long[] state;
	static int rate;
	static int stateSize;
	static int capacity;
	static int hashLength;
	int rateLongs;
	int capacityLongs;
	int hashLenLongs;
	double geneticScore;
	double bitchangeScore;
	RoundFunction f;
	ModularSpongeConstruction_Longs(int rate, int capacity, int stateSize, RoundFunction f, int hashLength){
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
		this.hashLength = hashLength;
		this.hashLenLongs = hashLength/CONSTANTS.longSize;
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
				 for(int i = 0; i < rateLongs; i++) {
					 messageRateChunk[i]=messageArrayList.get(i);
				 }
				 xorIntoState(messageRateChunk);
				 messageArrayList.clear();
			 }
			 state = f.runFunction(state);
		}
	}
	public String spongeSqueeze(int iterations) {
		String retString = "";
		for(int iteration = 0 ; iteration < iterations; iteration++) {
			for(int i = 0 ; i < hashLenLongs; i++) {
				String hashChunk=Long.toBinaryString(state[i]+(0x7ffffffffffffffL));
				while(hashChunk.length() < 64){
					hashChunk="0"+hashChunk;
				}
				retString+=hashChunk;
			}
			retString+="\n";
			state = f.runFunction(state);
		}
		return retString;
	}
	public void spongePurge(){
		for(int i = 0 ; i < stateSize/CONSTANTS.longSize; i++) {
			state[i]=0;
		}
	}
	void printState() {
		System.out.print("\n{");
		for(int i = 0; i < state.length-1; i++) {
			System.out.print(state[i]+", ");
		}
		System.out.print(state[state.length-1]);
		System.out.println("}");
	}
}
