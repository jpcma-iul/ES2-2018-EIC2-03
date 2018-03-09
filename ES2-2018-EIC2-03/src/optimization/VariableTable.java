package optimization;

import java.util.ArrayList;

public class VariableTable {
	private ArrayList<Variable> Variables = new ArrayList<>();
	private String VariableGroupName;

	public VariableTable(String name) {
		VariableGroupName = name;
	}
	
	public void addVariable(Variable v){
		Variables.add(v);
	}
	
	public ArrayList<Variable> getVariables() {
		return Variables;
	}
	
	
}
