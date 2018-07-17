package main;

public class RoundFunction_Test extends RoundFunction {

	RoundFunction_Test(int stateSize) {
		super(stateSize);
	}

	@Override
	long[] runFunction(long[] state) {
		long[][] threeDState = new long[5][5];
		long[] tempState = new long[state.length];
		for(int i = 0; i < state.length; i++) {
			tempState[i] = state[i];
		}
		int iterator = 0;
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				threeDState[i][j] = state[iterator];
				iterator++;
			}
		}
		iterator = 0;
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				threeDState[i][j]=threeDState[(i+1)%5][(j+1)%5]^threeDState[i][j];
				tempState[iterator] = threeDState[i][j];
				tempState[iterator] = (long) (tempState[iterator]+(Math.pow(i*j,j))%(Math.pow(2, CONSTANTS.longSize)-1));
				tempState[iterator] = tempState[iterator]<<i*j;
				iterator++;
			}
		}
		return tempState;
	}
	
	@Override
	public String getFunc() {
		// TODO Auto-generated method stub
		return "TESTING FUNCTION";
	}

}
