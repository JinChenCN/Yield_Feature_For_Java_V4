package symtab;

import java.util.List;

public interface Scope {
	public String getScopeName();
	public Scope getEnclosingScope();
	public void define(Symbol symbol);
	public Symbol resolve(String name);
	public void setEnclosingScope(Scope scope);
	public Type resolveType(String name);
	public List<Symbol> getAllSymbols();
	public void defineMethod(MethodSymbol methodSymbol, List<Type> params);
}
