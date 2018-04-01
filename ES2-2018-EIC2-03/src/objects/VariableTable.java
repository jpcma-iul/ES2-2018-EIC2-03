package objects;

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
	
	public void clear() {
		while(!Variables.isEmpty())
			Variables.remove(0);
	}
	
	public void setVariableGroupName(String variableGroupName) {
		VariableGroupName = variableGroupName;
	}
	
	public String getVariableGroupName() {
		return VariableGroupName;
	}
	
}
