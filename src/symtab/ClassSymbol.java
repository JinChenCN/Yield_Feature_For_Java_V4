package symtab;

import java.util.List;


public class ClassSymbol extends ScopedSymbol implements Type {

	private List<InterfaceSymbol> interfaces;
	private ClassSymbol superclass;

	public ClassSymbol(String name) {
		super(name, null);
	}
	
	public ClassSymbol getSuperclass() {
		return superclass;
	}

	public void setSuperclass(ClassSymbol superclass) {
		this.superclass = superclass;
	}

	public List<InterfaceSymbol> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<InterfaceSymbol> interfaces) {
		this.interfaces = interfaces;
	}

	@Override
	public Symbol resolve(String name) {
		// if the symbol exists in the current scope, return it
		Symbol s = symbols.get(name);
		if (s != null)
			return s;

		if (superclass != null)
			return superclass.resolve(name);

		// otherwise look in the enclosing scope, if there is one
		if (enclosingScope != null)
			return enclosingScope.resolve(name);

		// otherwise it doesn't exist
		return null;
	}

	@Override
	public Type resolveType(String name) {
		// if the symbol exists in the current scope, return it
		Symbol s = symbols.get(name);
		if (s != null) {
			if (s instanceof Type) {
				return (Type) s;
			}
		}
		
		if (superclass != null)
			return superclass.resolveType(name);

		// otherwise look in the enclosing scope, if there is one
		if (enclosingScope != null)
			return enclosingScope.resolveType(name);

		// otherwise it doesn't exist
		return null;
	}
	
	public Symbol resolveMember(String string) {
	// if the symbol exists in the current scope, return it
		Symbol s = symbols.get(string);
		if (s != null)
			return s;	
		
		if (superclass != null)
			return superclass.resolveMember(name);

		// otherwise it doesn't exist
		return null;
	}
}
