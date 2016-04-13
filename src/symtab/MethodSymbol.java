package symtab;

import java.util.ArrayList;
import java.util.List;

import se701.A2SemanticsException;

public class MethodSymbol extends ScopedSymbol {
	private Type returnType;
	private List<Type> parameters;
	private List<List<Type>> overLoadParams = new ArrayList<List<Type>>();

	public MethodSymbol(String name) {
		super(name, null);
	}

	public Type getReturnType() {
		return returnType;
	}

	public void setReturnType(Type returnType) {
		this.returnType = returnType;
	}

	public List<Type> getParameters() {
		return parameters;
	}

	public void setParameters(List<Type> parameters) {
		this.parameters = parameters;
	}
	
	public void putOverloadParams(List<Type> parameters) {
		overLoadParams.add(parameters);
	}
	
	public List<List<Type>> getOverLoadParams() {
		return overLoadParams;
	}
	
	public Symbol resolveMember(String string) {
		Symbol s = null;
	// if the symbol exists in the current scope, return it
		
//		s = symbols.get(string);
//		if (s != null)
//			return s;

		// otherwise look in the enclosing scope, if there is one
		if ((enclosingScope != null) && (enclosingScope instanceof ClassSymbol))
		{
			s = ((ClassSymbol)enclosingScope).resolveMember(string);
			if (s != null)
				return s;
		}

		// otherwise it doesn't exist
		return null;
	}

}
