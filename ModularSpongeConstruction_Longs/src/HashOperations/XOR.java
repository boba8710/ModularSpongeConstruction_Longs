package HashOperations;

public class XOR extends HashOperation {

	public String id = "XOR";
	public String getId() {
		return id;
	}

	public long[] run(long[] state, String parameters) {
		String[] paramArray = parameters.split(",");
		state[Integer.parseInt(paramArray[0])] = state[Integer.parseInt(paramArray[0])]^state[Integer.parseInt(paramArray[1])];
		return state;
	}

}

