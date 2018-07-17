package HashOperations;

public abstract class HashOperation {
	public String id;
	abstract public String getId();
	abstract public long[] run(long[] state, String parameters);
}
