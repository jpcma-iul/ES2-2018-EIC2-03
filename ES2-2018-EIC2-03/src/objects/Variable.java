package objects;

public class Variable {
	private String varName;
	private DataType type;
	private int highestBinaryLimit=1;
	private int lowestBinaryLimit=0;
	private double highestRealLimit = 1000000;
	private double lowestRealLimit = -1000000;
	private int lowestLimit = -1000000;
	private int highestLimit = 	1000000;
	private int binaryValue = 0;
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
		return intvalue;
	}
	
	public int getBinaryValue() {
		return binaryValue;
	}
	
	public double getRealValue() {
		return realvalue;
	}

	public void setBinaryValue(int binaryValue) {
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
	
	public double getHighestRealLimit() {
		return highestRealLimit;
	}
	
	public int getLowestLimit() {
		return lowestLimit;
	}
	
	public double getLowestRealLimit() {
		return lowestRealLimit;
	}

	public void setHighestLimit(int highestLimit) {
		this.highestLimit = highestLimit;
	}
	
	public void setHighestRealLimit(double highestRealLimit) {
		this.highestRealLimit = highestRealLimit;
	}

	public void setLowestLimit(int lowestLimit) {
		this.lowestLimit = lowestLimit;
	}
	
	public void setLowestRealLimit(double lowestRealLimit) {
		this.lowestRealLimit = lowestRealLimit;
	}
	
	public int getHighestBinaryLimit() {
		return highestBinaryLimit;
	}
	
	public int getLowestBinaryLimit() {
		return lowestBinaryLimit;
	}

}
