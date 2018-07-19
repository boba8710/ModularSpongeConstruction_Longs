package HashOperations;

public class LROT extends HashOperation {
	//Add a given constant to a chunk of the state
	public String id = "LRO";
	public String getId() {
		return id;
	}

	public long[] run(long[] state, String parameters) {
		String[] paramArray = parameters.split(",");
		state[Integer.parseInt(paramArray[0])] = Long.rotateLeft(state[Integer.parseInt(paramArray[0])],Integer.parseInt(paramArray[1]));

		return state;
	}

}
