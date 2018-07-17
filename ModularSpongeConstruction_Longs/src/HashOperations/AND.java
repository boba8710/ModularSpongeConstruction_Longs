package HashOperations;


public class AND extends HashOperation {

	public String id = "AND";
	public String getId() {
		return id;
	}

	public long[] run(long[] state, String parameters) {
		String[] paramArray = parameters.split(",");
		state[Integer.parseInt(paramArray[0])] = state[Integer.parseInt(paramArray[0])]&state[Integer.parseInt(paramArray[1])];
		return state;
	}

}
