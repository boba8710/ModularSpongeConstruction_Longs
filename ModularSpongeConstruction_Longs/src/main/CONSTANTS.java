package main;

import HashOperations.HashOperation;

public class CONSTANTS {
	public static final int 	longSize = 64;
	public static final int 	rounds = 5;
	public static final HashOperation[] opList = {new HashOperations.ADD(), new HashOperations.AND(), new HashOperations.LROT(), /*new HashOperations.NOP(),*/ /*new HashOperations.NOT(), new HashOperations.OR(), new HashOperations.SWAP(), new HashOperations.SWAP0(), new HashOperations.SWAP1(), new HashOperations.SWAP2(),*/ new HashOperations.XOR()/*, new HashOperations.XORCONSTANT()*/};
}
