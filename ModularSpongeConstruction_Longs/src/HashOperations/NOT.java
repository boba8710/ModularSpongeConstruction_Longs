package HashOperations;

public class NOT extends HashOperation {
	//Add a given constant to a chunk of the state
	public String id = "NOT";
	public String getId() {
		return id;
	}

	public long[] run(long[] state, String parameters) {
		String[] paramArray = parameters.split(",");
		state[Integer.parseInt(paramArray[0])] = ~state[Integer.parseInt(paramArray[0])];
		return state;
	}

}
