package objects;

public class Variable {
	private String varName;
	private DataType type;
	private int lowestLimit;
	private int highestLimit;
	private String binaryValue = "";
	private double doublevalue = 0.0;
	private int intvalue = 0;

	public Variable(String varName, DataType type) {
		this.varName = varName;
		this.type = type;
	}

	public String getVarName() {
		return varName;
	}

	public DataType getType() {
		return type;
	}

	/**
	 * We didn't make any double getValue() because this method is only called for a
	 * JSlider and the only values it accepts are Integers. Therefore making the
	 * double*10 was our approach. The BINARY DataType is considered as a String of
	 * 2's complement (signed) binary value. If the String is longer than 32 "bits",
	 * the latter are considered for the calculation.
	 * 
	 */
	public int getValue() {
		if (type == DataType.BINARY) {
			return (int) Long.parseLong(binaryValue, 2);
		}
		if (type == DataType.REAL)
			return (int) (doublevalue * 10);
		return intvalue;
	}

	public void setValue(String binaryValue) {
		this.binaryValue = binaryValue;
	}

	public void setValue(int value) {
		this.intvalue = value;
	}

	public void setValue(double value) {
		this.doublevalue = value;
	}

	public void setHighestLimit(int highestLimit) {
		this.highestLimit = highestLimit;
	}

	public void setLowestLimit(int lowestLimit) {
		this.lowestLimit = lowestLimit;
	}

}
