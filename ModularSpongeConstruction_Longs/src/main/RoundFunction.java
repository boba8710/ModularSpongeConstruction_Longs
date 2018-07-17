package main;

public abstract class RoundFunction {
	int stateSize;
	/**
	 * 
	 * @param stateSize
	 * 
	 * Parent class of all encoded round functions. Currently, the statesize is locked to 1600 bits.
	 */
	static int rounds = CONSTANTS.rounds;
	RoundFunction(int stateSize){
		this.stateSize = stateSize;
		assert stateSize == 1600;
	}
	abstract long[] runFunction(long[] state);
	abstract public String getFunc();
}
