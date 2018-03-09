package optimization;

public class Variable {
	private String varName;
	private DataType type;
	private int integer;
	private boolean binary;
	private double real;
	
	public Variable(String varName, DataType type) {
		this.varName=varName;
		this.type=type;
		System.out.println(varName+": "+type);
	}
	
	public String getVarName() {
		return varName;
	}
	
	public DataType getType() {
		return type;
	}
}
