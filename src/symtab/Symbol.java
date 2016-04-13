package symtab;

import symtab.Type;

public abstract class Symbol {

	protected String name;
	protected Type type;
	private int definedLine = -1;
	private int definedColumn = -1;
	
	public Symbol(String name, Type type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public Type getType() {
		return type;
	}

	public int getDefinedLine() {
		return definedLine;
	}

	public void setDefinedLine(int definedLine) {
		this.definedLine = definedLine;
	}
	
	public int getDefinedColumn() {
		return definedColumn;
	}
	
	public void setDefinedColumn(int definedColumn) {
		this.definedColumn = definedColumn;
	}
}
