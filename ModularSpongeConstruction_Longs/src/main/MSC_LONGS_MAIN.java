package main;

import java.util.Random;

public class MSC_LONGS_MAIN {
	public static void main(String[] args) {
		RandomFunctionBuilder rfb = new RandomFunctionBuilder(1600, 20);
		System.out.println(rfb.genFuncString());
	}
}
