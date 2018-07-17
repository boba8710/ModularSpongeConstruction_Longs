package HashOperations;

public class OR extends HashOperation {

	public String id = "ORR";
	public String getId() {
		return id;
	}

	public long[] run(long[] state, String parameters) {
		String[] paramArray = parameters.split(",");
		state[Integer.parseInt(paramArray[0])] = state[Integer.parseInt(paramArray[0])]|state[Integer.parseInt(paramArray[1])];
		return state;
	}

}

