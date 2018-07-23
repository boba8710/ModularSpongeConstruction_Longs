package main;

public class GlassFunction extends RoundFunction {

	GlassFunction(int stateSize) {
		super(stateSize);
		// TODO Auto-generated constructor stub
	}

	@Override
	long[] runFunction(long[] state) {
		assert state.length == stateSize;
		state[3] ^= state[1];
		state[0] = Long.rotateLeft(state[0], 37);
		state[0] = Long.rotateLeft(state[0], 37);
		state[3] += 4266968414311524662L;
		state[6] &= state[24];
		state[1] = Long.rotateLeft(state[1], 35);
		state[20]^=state[23];
		state[12]^=state[15];
		state[3] = Long.rotateLeft(state[3], 25);
		state[0]+=2548774367665783085L;
		state[4]^=state[2];
		state[11]&=state[5];
		state[0]^=state[4];
		state[2]^=state[3];
		state[1]^=state[0];
		state[18]&=state[22];
		state[17]^=state[2];
		state[16]&=state[2];
		state[2] = Long.rotateLeft(state[2], 16);
		state[1] = Long.rotateLeft(state[1], 17);
		state[15]&=state[4];
		state[1]^=state[2];
		state[2]^=state[0];
		state[0] = Long.rotateLeft(state[0], 47);
		state[16] = Long.rotateLeft(state[16], 14);
		state[1] += 3004493702170732358L;
		state[9]= Long.rotateLeft(state[9], 9);
		state[19]+=3869881997244444665L;
		state[0]^=state[1];
		return state;
	}

	@Override
	public String getFunc() {
		// TODO Auto-generated method stub
		return null;
	}

}
