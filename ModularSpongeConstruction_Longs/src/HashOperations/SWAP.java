package HashOperations;


public class SWAP extends HashOperation {

	public String id = "SWA";
	public String getId() {
		return id;
	}

	public long[] run(long[] state, String parameters) {
		String[] paramArray = parameters.split(",");
		long temp = state[Integer.parseInt(paramArray[0])];
		state[Integer.parseInt(paramArray[0])] = state[Integer.parseInt(paramArray[1])];
		state[Integer.parseInt(paramArray[1])] = temp;
		return state;
	}
	
	//Credit to GeeksForGeeks.com (https://www.geeksforgeeks.org/reverse-actual-bits-given-number/)
	//and ayushjauhari14 for this implementation
	private static int reverseBits(int n)
    {
        int rev = 0;
        while (n > 0) 
        {
            rev <<= 1;
            if ((int)(n & 1) == 1)
                rev ^= 1;
            n >>= 1;
        }
        return rev;
    }

}
