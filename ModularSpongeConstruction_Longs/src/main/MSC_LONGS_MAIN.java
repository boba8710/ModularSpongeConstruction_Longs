package main;

import java.util.Random;

public class MSC_LONGS_MAIN {
	public static void main(String[] args) {
		Random rand = new Random();
		RandomFunctionBuilder rfb = new RandomFunctionBuilder(1600,50);
		String parameterString = rfb.genFuncString();
		RoundFunction f = new ModularRoundFunction(1600, parameterString);
		ModularSpongeConstruction_Longs msc = new ModularSpongeConstruction_Longs(265, 1600-265, 1600, f);
		long[] message = new long[25];
		for(int i = 0; i < 25; i++) {
			message[i] = rand.nextLong();
		}
		msc.spongeAbsorb(message);
		System.out.println(msc.spongeSqueeze(1));
		System.out.println(f.getFunc());
		
	}
}
