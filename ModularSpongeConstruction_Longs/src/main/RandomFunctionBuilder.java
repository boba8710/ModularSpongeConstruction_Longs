package main;

import java.util.ArrayList;
import java.util.Random;

import HashOperations.HashOperation;

public class RandomFunctionBuilder {
	int stateSize, funcCount;
	public RandomFunctionBuilder(int stateSize, int funcCount) {
		// TODO Auto-generated constructor stub
		this.stateSize = stateSize;
		this.funcCount = funcCount;
	}
	public RandomFunctionBuilder(int stateSize) {
		this.stateSize = stateSize;
	}
	public String genRandOperation(String operation) {
		int opIndex = Integer.parseInt(operation.substring(3,4));
		Random rand = new Random();
		HashOperation selected = CONSTANTS.opList[rand.nextInt(CONSTANTS.opList.length)];
		String paramString = "";
		if(selected.getId() == "NOP") {
			
		}else if(selected.getId() == "NOT" || selected.getId() == "LRO" || selected.getId() == "ADD" || selected.getId() == "XOC") {
			
			paramString+=selected.getId();
			paramString+=opIndex+",";
			if(selected.getId()=="LRO") {
				paramString+=rand.nextInt(CONSTANTS.longSize);
			}else if( selected.getId() != "NOT"){
				paramString+=rand.nextLong();
			}
		}else {
			paramString += selected.getId();
			int n = opIndex;
			int m = rand.nextInt(stateSize/CONSTANTS.longSize);
			while(n==m) {
				m = rand.nextInt(stateSize/CONSTANTS.longSize);
			}
			paramString+=n+","+m;
		}
		return paramString;
	}
	public String genFuncString() {
		Random rand = new Random();
		
		String retString = "";
		ArrayList<Integer> unusedIndexes = new ArrayList<Integer>();
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		for(int i = 0; i<stateSize/CONSTANTS.longSize;i++){
			indexList.add(i);
		}
		unusedIndexes.addAll(indexList);
		for(int function = 0; function < funcCount; function++) {
			Integer randIndex = unusedIndexes.get(rand.nextInt(unusedIndexes.size()));
			unusedIndexes.remove(randIndex);
			if(unusedIndexes.size() == 0){
				unusedIndexes.addAll(indexList);
			}
			HashOperation selected = CONSTANTS.opList[rand.nextInt(CONSTANTS.opList.length)];
			String paramString = "";
			if(selected.getId() == "NOP") {
				
			}else if(selected.getId() == "NOT" || selected.getId() == "LRO" || selected.getId() == "ADD" || selected.getId() == "XOC") {
				
				paramString+=selected.getId();
				paramString+=randIndex+",";
				if(selected.getId()=="LRO") {
					paramString+=rand.nextInt(CONSTANTS.longSize);
				}else if( selected.getId() != "NOT"){
					paramString+=rand.nextLong();
				}
			}else {
				paramString += selected.getId();
				int n = randIndex;
				int m = rand.nextInt(stateSize/CONSTANTS.longSize);
				while(n==m) {
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
