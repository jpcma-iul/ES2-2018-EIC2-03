package objects;

public class Variable {
	private String varName;
	private DataType type;
	private double highestRealLimit = Double.MAX_VALUE;
	private double lowestRealLimit = Double.MIN_VALUE;
	private int lowestLimit = Integer.MIN_VALUE;
	private int highestLimit = 	Integer.MAX_VALUE;
	private String binaryValue = "0";
	private double realvalue = 0.0;
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
	public int getIntegerValue() {
		if (type == DataType.BINARY) {
			return (int) Long.parseLong(binaryValue, 2);
		}
		return intvalue;
	}
	
	public double getRealValue() {
		return realvalue;
	}

	public void setValue(String binaryValue) {
		this.binaryValue = binaryValue;
	}

	public void setValue(int value) {
		this.intvalue = value;
	}

	public void setValue(double value) {
		this.realvalue = value;
	}
	
	public int getHighestLimit() {
		return highestLimit;
	}
	
	public int getLowestLimit() {
		return lowestLimit;
	}

	public void setHighestLimit(int highestLimit) {
		this.highestLimit = highestLimit;
	}

	public void setLowestLimit(int lowestLimit) {
		this.lowestLimit = lowestLimit;
	}

}
