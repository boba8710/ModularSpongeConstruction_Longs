package main;

import java.util.Random;

import HashOperations.HashOperation;

public class RandomFunctionBuilder {
	int stateSize, funcCount;
	public RandomFunctionBuilder(int stateSize, int funcCount) {
		// TODO Auto-generated constructor stub
		this.stateSize = stateSize;
		this.funcCount = funcCount;
	}
	public String genRandOperation() {
		Random rand = new Random();
		HashOperation selected = CONSTANTS.opList[rand.nextInt(CONSTANTS.opList.length)];
		String paramString = "";
		if(selected.getId() == "NOP") {
			
		}else if(selected.getId() == "NOT" || selected.getId() == "LRO" || selected.getId() == "ADD" || selected.getId() == "XOC") {
			int randIndex = rand.nextInt(stateSize/CONSTANTS.longSize);
			paramString+=selected.getId();
			paramString+=randIndex+",";
			if(selected.getId()=="LRO") {
				paramString+=rand.nextInt(CONSTANTS.longSize);
			}else {
				paramString+=rand.nextLong();
			}
		}else if( selected.getId() != "NOT"){
			paramString += selected.getId();
			int n = rand.nextInt(stateSize/CONSTANTS.longSize);
			int m = rand.nextInt(stateSize/CONSTANTS.longSize);
			while(n!=m) {
				n = rand.nextInt(stateSize/CONSTANTS.longSize);
				m = rand.nextInt(stateSize/CONSTANTS.longSize);
			}
			paramString+=n+","+m;
		}
		return paramString;
	}
	public String genFuncString() {
		Random rand = new Random();
		
		String retString = "";
		
		for(int function = 0; function < funcCount; function++) {
			HashOperation selected = CONSTANTS.opList[rand.nextInt(CONSTANTS.opList.length)];
			String paramString = "";
			if(selected.getId() == "NOP") {
				
			}else if(selected.getId() == "NOT" || selected.getId() == "LRO" || selected.getId() == "ADD" || selected.getId() == "XOC") {
				int randIndex = rand.nextInt(stateSize/CONSTANTS.longSize);
				paramString+=selected.getId();
				paramString+=randIndex+",";
				if(selected.getId()=="LRO") {
					paramString+=rand.nextInt(CONSTANTS.longSize);
				}else if( selected.getId() != "NOT"){
					paramString+=rand.nextLong();
				}
			}else {
				paramString += selected.getId();
				int n = rand.nextInt(stateSize/CONSTANTS.longSize);
				int m = rand.nextInt(stateSize/CONSTANTS.longSize);
				while(n==m) {
					n = rand.nextInt(stateSize/CONSTANTS.longSize);
					m = rand.nextInt(stateSize/CONSTANTS.longSize);
				}
				paramString+=n+","+m;
			}
			paramString+="#";
			retString+=paramString;
		}
		return retString;
	}
}
