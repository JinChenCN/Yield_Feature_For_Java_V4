package symtab;

import java.util.ArrayList;
import java.util.List;

public class GlobalScope extends BaseScope {
	
	public GlobalScope() {
		super();
		
		define(new BuiltInTypeSymbol("char"));
		define(new BuiltInTypeSymbol("String"));
		define(new BuiltInTypeSymbol("float"));
		define(new BuiltInTypeSymbol("byte"));
		define(new BuiltInTypeSymbol("int"));
		define(new BuiltInTypeSymbol("boolean"));
		define(new BuiltInTypeSymbol("double"));
		define(new BuiltInTypeSymbol("short"));
		
		define(new BuiltInTypeSymbol("long"));
		define(new BuiltInTypeSymbol("void"));
		define(new BuiltInTypeSymbol("null"));

		define(new VariableSymbol("true", resolveType("boolean")));
		define(new VariableSymbol("false", resolveType("boolean")));
		
		define(new ClassSymbol("Object"));
		define(new ClassSymbol("FileInputStream"));

		MethodSymbol println = new MethodSymbol("println");
		List<Type> paramString = new ArrayList<Type>();
		paramString.add(new BuiltInTypeSymbol("String"));
		println.putOverloadParams(paramString);
		
		List<Type> paramsInt = new ArrayList<Type>();
		paramsInt.add(new BuiltInTypeSymbol("int"));
		println.putOverloadParams(paramsInt);
		
		List<Type> paramsChar = new ArrayList<Type>();
		paramsChar.add(new BuiltInTypeSymbol("char"));
		println.putOverloadParams(paramsChar);
		
		List<Type> paramsBool = new ArrayList<Type>();
		paramsBool.add(new BuiltInTypeSymbol("boolean"));
		println.putOverloadParams(paramsBool);
		
		List<Type> paramsDouble = new ArrayList<Type>();
		paramsDouble.add(new BuiltInTypeSymbol("double"));
		println.putOverloadParams(paramsDouble);
		
		List<Type> paramsFloat = new ArrayList<Type>();
		paramsFloat.add(new BuiltInTypeSymbol("float"));
		println.putOverloadParams(paramsFloat);
		
		List<Type> paramsLong = new ArrayList<Type>();
		paramsLong.add(new BuiltInTypeSymbol("long"));
		println.putOverloadParams(paramsLong);		
		
		List<Type> paramsObject = new ArrayList<Type>();
		paramsObject.add(new ClassSymbol("Object"));
		println.putOverloadParams(paramsObject);	
		
		List<Type> paramsVoid = new ArrayList<Type>();
		println.putOverloadParams(paramsVoid);
		define(println);
		
		MethodSymbol print = new MethodSymbol("print");
		print.putOverloadParams(paramString);
		print.putOverloadParams(paramsInt);
		print.putOverloadParams(paramsChar);
		print.putOverloadParams(paramsBool);
		print.putOverloadParams(paramsDouble);
		print.putOverloadParams(paramsFloat);
		print.putOverloadParams(paramsLong);		
		print.putOverloadParams(paramsObject);
		print.putOverloadParams(paramsVoid);
		define(print);
		
		//TODO : need to define more Scopes
	}
	
	@Override
	public String getScopeName() {
		return "Global Scope";
	}

	@Override
	public Scope getEnclosingScope() {
		return null;
	}

	@Override
	public void setEnclosingScope(Scope scope) {
		throw new IllegalArgumentException("Cannot set enclosing scope for GlobalScope.");
	}
}
