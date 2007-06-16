package bddbddb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SymbolTable {
	
	private Map<String, Symbol> table = new TreeMap<String, Symbol>();
	
	public void add(String name, Symbol symbol) {
		table.put(name, symbol);
	}
	
	public String getUniqueName(String className, String methodName, String symbolName) {
		String name = className + "_" + methodName + "_" + symbolName;
		if (table.containsKey(name)) {
			return name;
		} else {
			name = className + "_" + symbolName;
			if (table.containsKey(name))
				return name;
			else if (table.containsKey(className))
				return getUniqueName(table.get(className).getType(), methodName, symbolName);
			else
				throw new RuntimeException("Symbol table does not contain \"" + symbolName + "\" in " + className + "." + methodName);
		}
	}
	
	public Symbol lookup(String className, String methodName, String symbolName) {
		return table.get(getUniqueName(className, methodName, symbolName));
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, Symbol> entry : table.entrySet())
			sb.append(entry.getKey() + " -> " + entry.getValue() + "\n");
		
		return sb.toString();
	}
}

abstract class Symbol {
	private final String type;
	
	public Symbol(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	public abstract String getValue(int index);
	
	public boolean isPrimitive() {
		return getType().equals("int") || getType().equals("boolean");
	}
	
	public String toString() {
		return "Type: " + type;
	}
}

class Variable extends Symbol {
	private final String value;
	
	public Variable(String type, String value) {
		super(type);
		this.value = value;
	}
	
	public String getValue(int index) {
		return value;
	}
	
	public String toString() {
		return "Variable { " + super.toString() + ", Value: " + value + " }";
	}
}

class Method extends Symbol {
	private final List<String> values = new ArrayList<String>();
	private String returnLabel = "";
	
	public Method(String type) {
		super(type);
	}
	
	public void addParameter(String parameter) {
		values.add(parameter);
	}
	
	public String getReturnLabel() {
		return returnLabel;
	}
	
	public void setReturnLabel(String returnLabel) {
		this.returnLabel = returnLabel;
	}
	
	public String getValue(int index) {
		return values.get(index);
	}
	
	public String toString() {
		return "Method { " + super.toString() + ", Values: " + values + ", Return Label: " + returnLabel + " }";
	}
}

class Class extends Symbol {

	public Class(String type) {
		super(type);
	}
	
	public String getValue(int index) {
		return getType();
	}
	
	public String toString() {
		return "Class { " + super.toString() + " }";
	}
}
