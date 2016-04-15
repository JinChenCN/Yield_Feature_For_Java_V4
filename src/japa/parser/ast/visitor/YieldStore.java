package japa.parser.ast.visitor;

import java.util.HashMap;
import java.util.List;

import japa.parser.ast.TypeParameter;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.stmt.BlockStmt;

public class YieldStore {
	public static HashMap<String, HashMap<String,BlockStmt>> yieldBlocks = new HashMap<String, HashMap<String,BlockStmt>>();
	public static HashMap<String, List<Parameter>> methodParams = new HashMap<String, List<Parameter>>();
	public static HashMap<String, List<Expression>> yieldParams = new HashMap<String, List<Expression>>();
	public static HashMap<String, List<MethodDeclaration>> yieldMethods = new HashMap<String, List<MethodDeclaration>>();
	
	public static void putBlock(String methodName, HashMap<String,BlockStmt> blockMap) {
		yieldBlocks.put(methodName, blockMap);
	}
	
	public static HashMap<String,BlockStmt> getBlocks(String methodName) {
		return yieldBlocks.get(methodName);
	}
	
	public static void putParams(String methodName, List<Parameter> parameters) {
		methodParams.put(methodName, parameters);
	}
	
	public static List<Parameter> getParams(String methodName) {
		return methodParams.get(methodName);
	}
	
	public static void putYieldParams(String methodName, List<Expression> parameters) {
		yieldParams.put(methodName, parameters);
	}
	
	public static List<Expression> getYieldParams(String methodName) {
		return yieldParams.get(methodName);
	}
	
	public static void putYieldMethods(String methodName, List<MethodDeclaration> methods) {
		yieldMethods.put(methodName, methods);
	}
	
	public static List<MethodDeclaration> getYieldMethods(String methodName) {
		return yieldMethods.get(methodName);
	}
}
