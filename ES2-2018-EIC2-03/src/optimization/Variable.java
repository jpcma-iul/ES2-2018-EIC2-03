package optimization;

public class Variable {
	private String varName;
	private DataType type;
	private int inteiro;
	private boolean binário;
	private double decimal;
	public Variable(String varName, DataType type) {
		this.varName=varName;
		this.type=type;
	}
}
