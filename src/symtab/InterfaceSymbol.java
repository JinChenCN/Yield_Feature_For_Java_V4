package symtab;


public class InterfaceSymbol extends ScopedSymbol implements Type {

	private InterfaceSymbol superclass = null;

	public InterfaceSymbol(String name) {
		super(name, null);
	}

	public InterfaceSymbol getSuperclass() {
		return superclass;
	}

	public void setSuperinterface(InterfaceSymbol superinterface) {
		this.superclass = superinterface;
	}

	@Override
	public Symbol resolve(String name) {
		// if the symbol exists in the current scope, return it
		Symbol s = symbols.get(name);
		if (s != null)
			return s;

		// otherwise look in the enclosing scope, if there is one
		if (enclosingScope != null)
			return enclosingScope.resolve(name);

		// otherwise look in the superclass, if there is one
		if (superclass != null)
			return superclass.resolve(name);

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

		// otherwise look in the enclosing scope, if there is one
		if (enclosingScope != null)
			return enclosingScope.resolveType(name);

		// otherwise look in the superclass, if there is one
		if (superclass != null)
			return superclass.resolveType(name);

		// otherwise it doesn't exist
		return null;
	}

}
