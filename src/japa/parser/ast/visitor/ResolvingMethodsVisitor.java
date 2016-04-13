package japa.parser.ast.visitor;

import japa.parser.ast.BlockComment;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.LineComment;
import japa.parser.ast.Node;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.TypeParameter;
import japa.parser.ast.body.AnnotationDeclaration;
import japa.parser.ast.body.AnnotationMemberDeclaration;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.EmptyMemberDeclaration;
import japa.parser.ast.body.EmptyTypeDeclaration;
import japa.parser.ast.body.EnumConstantDeclaration;
import japa.parser.ast.body.EnumDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.InitializerDeclaration;
import japa.parser.ast.body.JavadocComment;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.ArrayAccessExpr;
import japa.parser.ast.expr.ArrayCreationExpr;
import japa.parser.ast.expr.ArrayInitializerExpr;
import japa.parser.ast.expr.AssignExpr;
import japa.parser.ast.expr.BinaryExpr;
import japa.parser.ast.expr.BooleanLiteralExpr;
import japa.parser.ast.expr.CastExpr;
import japa.parser.ast.expr.CharLiteralExpr;
import japa.parser.ast.expr.ClassExpr;
import japa.parser.ast.expr.ConditionalExpr;
import japa.parser.ast.expr.DoubleLiteralExpr;
import japa.parser.ast.expr.EnclosedExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.InstanceOfExpr;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.IntegerLiteralMinValueExpr;
import japa.parser.ast.expr.LongLiteralExpr;
import japa.parser.ast.expr.LongLiteralMinValueExpr;
import japa.parser.ast.expr.MarkerAnnotationExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.expr.NullLiteralExpr;
import japa.parser.ast.expr.ObjectCreationExpr;
import japa.parser.ast.expr.QualifiedNameExpr;
import japa.parser.ast.expr.SingleMemberAnnotationExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.expr.SuperExpr;
import japa.parser.ast.expr.SuperMemberAccessExpr;
import japa.parser.ast.expr.ThisExpr;
import japa.parser.ast.expr.UnaryExpr;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.AssertStmt;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.BreakStmt;
import japa.parser.ast.stmt.CatchClause;
import japa.parser.ast.stmt.ContinueStmt;
import japa.parser.ast.stmt.DoStmt;
import japa.parser.ast.stmt.EmptyStmt;
import japa.parser.ast.stmt.ExplicitConstructorInvocationStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.ForStmt;
import japa.parser.ast.stmt.ForeachStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.stmt.LabeledStmt;
import japa.parser.ast.stmt.ReturnStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.stmt.SwitchEntryStmt;
import japa.parser.ast.stmt.SwitchStmt;
import japa.parser.ast.stmt.SynchronizedStmt;
import japa.parser.ast.stmt.ThrowStmt;
import japa.parser.ast.stmt.TryStmt;
import japa.parser.ast.stmt.TypeDeclarationStmt;
import japa.parser.ast.stmt.WhileStmt;
import japa.parser.ast.stmt.YieldStmt;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.type.ReferenceType;
import japa.parser.ast.type.VoidType;
import japa.parser.ast.type.WildcardType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import se701.A2SemanticsException;
import symtab.ClassSymbol;
import symtab.InterfaceSymbol;
import symtab.MethodSymbol;
import symtab.Scope;
import symtab.Symbol;
import symtab.Type;

public class ResolvingMethodsVisitor implements VoidVisitor<Object> {

	private Scope currentScope;

	@Override
	public void visit(Node n, Object arg) {
		throw new IllegalStateException(n.getClass().getName());
	}

	@Override
	public void visit(CompilationUnit n, Object arg) {
		currentScope = n.getEnclosingScope();

		if (n.getTypes() != null) {
			for (Iterator<TypeDeclaration> i = n.getTypes().iterator(); i
					.hasNext();) {
				i.next().accept(this, arg);
			}
		}
	}

	@Override
	public void visit(PackageDeclaration n, Object arg) {
	}

	@Override
	public void visit(ImportDeclaration n, Object arg) {
	}

	@Override
	public void visit(TypeParameter n, Object arg) {
	}

	@Override
	public void visit(LineComment n, Object arg) {
	}

	@Override
	public void visit(BlockComment n, Object arg) {
	}

	@Override
	public void visit(ClassOrInterfaceDeclaration n, Object arg) {
		currentScope = n.getEnclosingScope();

		if (currentScope instanceof ClassSymbol) {
			ClassSymbol classSymbol = (ClassSymbol) currentScope;

			if (classSymbol.getInterfaces() != null) {

				for (InterfaceSymbol i : classSymbol.getInterfaces()) {
					
					if (i.getAllSymbols() != null) {
						for (Symbol s: i.getAllSymbols()) {
							if (s instanceof MethodSymbol) {
								MethodSymbol m = (MethodSymbol) s;

								Symbol methodSym = currentScope.resolve(m.getName());
								if(methodSym == null)
								{
									throw new A2SemanticsException(m.getName() + " must be implemented in class " + n.getName() 
									+ " on line " + n.getBeginLine() + ".");
								}
								
								if(!(methodSym instanceof MethodSymbol))
								{
									throw new A2SemanticsException(m.getName() + " is not a valid method " + n.getName() 
									+ " on line " + n.getBeginLine() + ".");
								}								
							}
						}
					}
				}
			}
		}

		if (n.getMembers() != null) {
			for (BodyDeclaration b : n.getMembers()) {
				b.accept(this, arg);
			}
		}

		currentScope = currentScope.getEnclosingScope();
	}

	@Override
	public void visit(EnumDeclaration n, Object arg) {
	}

	@Override
	public void visit(EmptyTypeDeclaration n, Object arg) {
	}

	@Override
	public void visit(EnumConstantDeclaration n, Object arg) {
	}

	@Override
	public void visit(AnnotationDeclaration n, Object arg) {
	}

	@Override
	public void visit(AnnotationMemberDeclaration n, Object arg) {
	}

	@Override
	public void visit(FieldDeclaration n, Object arg) {
	}

	@Override
	public void visit(VariableDeclarator n, Object arg) {
		if (n.getInit() instanceof MethodCallExpr) {
			n.getInit().accept(this, arg);
		}
	}
	
	@Override
	public void visit(YieldStmt n, Object arg) {

	}

	@Override
	public void visit(VariableDeclaratorId n, Object arg) {
	}

	@Override
	public void visit(ConstructorDeclaration n, Object arg) {
	}

	@Override
	public void visit(MethodDeclaration n, Object arg) {
		currentScope = n.getEnclosingScope();
		boolean returnCorrect = false;
		MethodSymbol methodSym = ((MethodSymbol) currentScope.resolve(n.getName()));
		if (methodSym == null)
		{
			throw new A2SemanticsException("Method " + n.getName() +  " on line "
					+ n.getBeginLine() + " is not defined.");
		}
		if (methodSym.getReturnType() != null)
		{
			if (methodSym.getReturnType().getName() == "void") {
				returnCorrect = true;
			}
			String type = methodSym.getReturnType().getName();	

		if (n.getBody() != null) {
			BlockStmt body = n.getBody();
			if (body.getStmts() != null) {
				for (Statement statement : body.getStmts()) {
					if (statement instanceof ReturnStmt) {
						ReturnStmt returnStmt = (ReturnStmt) statement;
						Expression expr = returnStmt.getExpr();
						
						if (expr instanceof BinaryExpr) {
							expr = ((BinaryExpr) expr).getLeft();
						}

						if (expr instanceof NullLiteralExpr) {
							returnCorrect = true;						
							if (type == "byte" || type == "short" || type == "long" ||type == "int" || type == "double"
								|| type == "float" || type == "boolean" || type == "char") {
								throw new A2SemanticsException("Method " + methodSym.getName() +  " on line "
										+ n.getBeginLine() + " cannot return null.");
							}
						} else if (expr instanceof CharLiteralExpr) {
							returnCorrect = true;
							if (type != "char") {
								throw new A2SemanticsException(" Return type of Method "
										+ methodSym.getName() + " must be "
										+ methodSym.getReturnType().getName()
										+ " on line " + statement.getBeginLine() );
							}
						} else if (expr instanceof DoubleLiteralExpr) {
							returnCorrect = true;
							if (type != "double") {
								throw new A2SemanticsException(" Return type of Method "
										+ methodSym.getName() + " must be "
										+ methodSym.getReturnType().getName()
										+ " on line " + statement.getBeginLine() );
							}
						} else if (expr instanceof IntegerLiteralExpr) {
							returnCorrect = true;
							if (type != "int") {
								throw new A2SemanticsException(" Return type of Method "
										+ methodSym.getName() + " must be "
										+ methodSym.getReturnType().getName()
										+ " on line " + statement.getBeginLine() );
							}
						} else if (expr instanceof LongLiteralExpr) {
							returnCorrect = true;
							if (type != "long") {
								throw new A2SemanticsException(" Return type of Method "
										+ methodSym.getName() + " must be "
										+ methodSym.getReturnType().getName()
										+ " on line " + statement.getBeginLine() );
							}
						} else if (expr instanceof StringLiteralExpr) {
							returnCorrect = true;
							if (type != "String") {
								throw new A2SemanticsException(" Return type of Method "
										+ methodSym.getName() + " must be "
										+ methodSym.getReturnType().getName()
										+ " on line " + statement.getBeginLine() );
							}
						} else if (expr instanceof BooleanLiteralExpr) {
							returnCorrect = true;
							if (type != "boolean") {
								throw new A2SemanticsException(" Return type of Method "
										+ methodSym.getName() + " must be "
										+ methodSym.getReturnType().getName()
										+ " on line " + statement.getBeginLine() );
							}
						} else if (expr instanceof MethodCallExpr) {
							returnCorrect = true;
							Symbol methodCallSym = currentScope.resolve(((MethodCallExpr) expr).getName().toString());
							if (methodCallSym == null)
							{
								throw new A2SemanticsException("Method " + methodSym.getName() + " is not defined on line " + expr.getBeginLine());
							}
							if (methodCallSym instanceof MethodSymbol) {
								MethodSymbol m = (MethodSymbol) methodCallSym;
								String returnType = m.getReturnType().getName();
								if (type != returnType) {
									throw new A2SemanticsException( "Cannot convert " + returnType + " to " + type + " on line " + expr.getBeginLine());
								}
							} else {
								throw new A2SemanticsException( methodCallSym.getName() + " is not defined on line " + expr.getBeginLine());
							}
						} else if (expr instanceof ObjectCreationExpr) {
							returnCorrect = true;
							Symbol resolvedSymbol = currentScope.resolve(((ObjectCreationExpr) expr).getType().getName());
							if (resolvedSymbol == null)
							{
								throw new A2SemanticsException(((ObjectCreationExpr) expr).getType().getName() + " is not defined on line " + expr.getBeginLine());
							}
							
							if (resolvedSymbol != methodSym.getReturnType()) {
								throw new A2SemanticsException("Cannot convert " + resolvedSymbol.getName() + " to " + methodSym.getReturnType() + " on line " + expr.getBeginLine());
							}
						} else if (expr instanceof NameExpr) {
							returnCorrect = true;
							Symbol resolvedSymbol = currentScope.resolve(((NameExpr) expr).getName());
							if (resolvedSymbol == null)
							{
								throw new A2SemanticsException(((NameExpr) expr).getName() + " is not defined on line " + expr.getBeginLine());
							}
							
							if (resolvedSymbol.getType() != methodSym.getReturnType()) {
								throw new A2SemanticsException("Cannot convert " + resolvedSymbol.getType() + " to " + methodSym.getReturnType() + " on line " + expr.getBeginLine());
							}
						}
					}
				}
			}

			body.accept(this, arg);

			if (!returnCorrect) {
				throw new A2SemanticsException("Method " + n.getName() + " on line " + n.getBeginLine() 
				+ " doesn't have a return. Expected return " + methodSym.getReturnType().getName() + ".");
			}
		}
		}
	}

	@Override
	public void visit(Parameter n, Object arg) {
	}

	@Override
	public void visit(EmptyMemberDeclaration n, Object arg) {
	}

	@Override
	public void visit(InitializerDeclaration n, Object arg) {
	}

	@Override
	public void visit(JavadocComment n, Object arg) {
	}

	@Override
	public void visit(ClassOrInterfaceType n, Object arg) {
	}

	@Override
	public void visit(PrimitiveType n, Object arg) {
	}

	@Override
	public void visit(ReferenceType n, Object arg) {
	}

	@Override
	public void visit(VoidType n, Object arg) {
	}

	@Override
	public void visit(WildcardType n, Object arg) {
	}

	@Override
	public void visit(ArrayAccessExpr n, Object arg) {
	}

	@Override
	public void visit(ArrayCreationExpr n, Object arg) {
	}

	@Override
	public void visit(ArrayInitializerExpr n, Object arg) {
	}

	@Override
	public void visit(AssignExpr n, Object arg) {
		if (n.getValue() instanceof MethodCallExpr) {
			n.getValue().accept(this, arg);
		}
	}

	@Override
	public void visit(BinaryExpr n, Object arg) {
	}

	@Override
	public void visit(CastExpr n, Object arg) {
	}

	@Override
	public void visit(ClassExpr n, Object arg) {
	}

	@Override
	public void visit(ConditionalExpr n, Object arg) {
	}

	@Override
	public void visit(EnclosedExpr n, Object arg) {
	}

	@Override
	public void visit(FieldAccessExpr n, Object arg) {
	}

	@Override
	public void visit(InstanceOfExpr n, Object arg) {
	}

	@Override
	public void visit(StringLiteralExpr n, Object arg) {
	}

	@Override
	public void visit(IntegerLiteralExpr n, Object arg) {
	}

	@Override
	public void visit(LongLiteralExpr n, Object arg) {
	}

	@Override
	public void visit(IntegerLiteralMinValueExpr n, Object arg) {
	}

	@Override
	public void visit(LongLiteralMinValueExpr n, Object arg) {
	}

	@Override
	public void visit(CharLiteralExpr n, Object arg) {
	}

	@Override
	public void visit(DoubleLiteralExpr n, Object arg) {
	}

	@Override
	public void visit(BooleanLiteralExpr n, Object arg) {
	}

	@Override
	public void visit(NullLiteralExpr n, Object arg) {
	}

	@Override
	public void visit(MethodCallExpr n, Object arg) {
		currentScope = n.getEnclosingScope();

		Symbol methodSym = currentScope.resolve(n.getName());
		
		if(methodSym == null)
		{
			throw new A2SemanticsException("Method " + n.getName() + " on line " + n.getBeginLine() + " is not defined.");
		}

		if (methodSym instanceof MethodSymbol) {
			MethodSymbol m = (MethodSymbol) methodSym;
			
			List<List<Type>> methodParameters = m.getOverLoadParams();
			ArrayList<Integer> paramNum = new ArrayList<Integer>();
			for(List<Type> pList : methodParameters)
			{
				paramNum.add(pList.size());
			}
						
			List<Expression> actualParams = n.getArgs();
				
			if(paramNum != null && actualParams == null)
			{
				if(paramNum.size() == 0)
				{
					actualParams = Collections.<Expression> emptyList();
				} else
				{
					throw new A2SemanticsException(n.getName() + " on line " + n.getBeginLine() 
					+ " doesn't accept paramter null.");		
				}		
			}
			
			if(paramNum.size() != 0 && !(paramNum.contains(actualParams.size())) )
			{
				throw new A2SemanticsException(n.getName() + " on line " + n.getBeginLine() 
				+ " doesn't accept " + actualParams.size() + " paramters.");	
			}
			
			List<Type> actualType = new ArrayList<Type>();
			for(Expression e : actualParams)
			{
				Type t = getTypeOfExpression(e);
				actualType.add(t);
			}

			List<List<Type>> sameSizeList = new ArrayList<List<Type>>();
			for (int i=0; i < paramNum.size(); i++)
			{
				if (actualType.size() == paramNum.get(i) ) //&& actualType.equals(methodParameters.get(i))
				{
					sameSizeList.add(methodParameters.get(i));
				}				
			}
			
			if(sameSizeList.size() == 0 && paramNum.size() != 0)
			{
				throw new A2SemanticsException(n.getName() + " on line " + n.getBeginLine() + " have incorrect parameters.");
			}
			
			if (sameSizeList.size() != 0)
			{		
				boolean fundTheSame = false;
				for (int i = 0; i < sameSizeList.size(); i++)
				{
					List<Type> expectedParams = sameSizeList.get(i);
					List<String> expectTypeNames = new ArrayList<String>();
					List<String> actualTypeNames = new ArrayList<String>();
					for (Type t: expectedParams)
					{
						expectTypeNames.add(t.getName());
					}
					
					for (Type t: actualType)
					{
						actualTypeNames.add(t.getName());
					}
					if(actualTypeNames.equals(expectTypeNames))
					{
						fundTheSame = true;
						break;
					}						
				
			} 
				if (!fundTheSame)
				{
					throw new A2SemanticsException("Method call " + n.getName() + " on line " + n.getBeginLine() + " have incorrect parameters.");
				}	 
				
			}
//			}
		
		} else {
				throw new A2SemanticsException(n.getName() + " on line " + n.getBeginLine() + " is not a valid method.");
		
		}

		if (n.getYieldBlock() != null)
		{
			n.getYieldBlock().accept(this, arg);
		}
	}
	
    private symtab.Type getTypeOfExpression(Expression expr) {
    	symtab.Type type = null;
    	if(expr != null){
    		Symbol sym = null; 
    		if (expr.getClass() == BinaryExpr.class) {
				expr = ((BinaryExpr) expr).getLeft();
			}
    		
    		if(expr.getClass() == NameExpr.class){
    			Symbol nameSym = currentScope.resolve(expr.toString());
    			 if(nameSym == null){
 	            	throw new A2SemanticsException(expr.toString() + " on line " + expr.getBeginLine() + " is undefined.");
 	            }
    			sym = (Symbol) currentScope.resolve(expr.toString()).getType();
    			 if(sym == null){
    	            	throw new A2SemanticsException(sym.getName() + " on line " + expr.getBeginLine() + " is undefined.");
    	            }
    		} 
    		else if(expr.getClass() == IntegerLiteralExpr.class){
    			sym = currentScope.resolve("int");
    		}else if (expr.getClass() == StringLiteralExpr.class){
    			sym = currentScope.resolve("String");
    		}else if(expr.getClass() == BinaryExpr.class){
    			sym = currentScope.resolve("byte");
    		}else if (expr.getClass() == BooleanLiteralExpr.class){
    			sym = currentScope.resolve("boolean");
    		}    		
    		else if(expr.getClass() == CharLiteralExpr.class){
    			sym = currentScope.resolve("char");
    		}else if (expr.getClass() == DoubleLiteralExpr.class){
    			sym = currentScope.resolve("double");
    		}else if(expr.getClass() == LongLiteralExpr.class){
    			sym = currentScope.resolve("long");
    		}else if (expr.getClass() == BooleanLiteralExpr.class){
    			sym = currentScope.resolve("boolean");
    		}else if (expr.getClass() == MethodCallExpr.class) {
    			Symbol symbol = currentScope.resolve(((MethodCallExpr) expr).getName().toString());
				if (symbol instanceof MethodSymbol) {
					MethodSymbol method = (MethodSymbol) symbol;
					//TODO : need to check if  
					//(symtab.Type) sym 
					//returns the correct type.
					sym = method;
				} else {
					throw new A2SemanticsException(symbol.getName() + " in class "
							+ symbol.getClass().getSimpleName()
							+" is not a valid method.");
				}
			} else if (expr instanceof ObjectCreationExpr) {
				sym = currentScope.resolve(((ObjectCreationExpr) expr).getType().getName());
			}
    		type = (symtab.Type) sym;
    	}
		return type;
	}

	@Override
	public void visit(NameExpr n, Object arg) {

	}

	@Override
	public void visit(ObjectCreationExpr n, Object arg) {
		if(n.getAnonymousClassBody() != null)
		{
			for (BodyDeclaration b : n.getAnonymousClassBody())
			{
				b.accept(this, arg);
			}		
		}
		
		if(n.getArgs() != null)
		{
			for (Expression e : n.getArgs())
			{
				e.accept(this, arg);
			}
		}
		
		if(n.getTypeArgs() != null)
		{
			for (japa.parser.ast.type.Type t : n.getTypeArgs())
			{
				t.accept(this, arg);
			}			
		}
	}

	@Override
	public void visit(QualifiedNameExpr n, Object arg) {

	}

	@Override
	public void visit(SuperMemberAccessExpr n, Object arg) {
		
	}

	@Override
	public void visit(ThisExpr n, Object arg) {

	}

	@Override
	public void visit(SuperExpr n, Object arg) {

	}

	@Override
	public void visit(UnaryExpr n, Object arg) {
		if(n.getExpr() != null)
		{
			n.getExpr().accept(this, arg);
		}
	}

	@Override
	public void visit(VariableDeclarationExpr n, Object arg) {
		if (n.getVars() != null) {
			for (VariableDeclarator v : n.getVars()) {
				v.accept(this, arg);
			}
		}
	}

	@Override
	public void visit(MarkerAnnotationExpr n, Object arg) {

	}

	@Override
	public void visit(SingleMemberAnnotationExpr n, Object arg) {
	}

	@Override
	public void visit(NormalAnnotationExpr n, Object arg) {
	}

	@Override
	public void visit(MemberValuePair n, Object arg) {
		if(n.getValue() != null)
		{
			n.getValue().accept(this, arg);
		}
	}

	@Override
	public void visit(ExplicitConstructorInvocationStmt n, Object arg) {
	}

	@Override
	public void visit(TypeDeclarationStmt n, Object arg) {
	}

	@Override
	public void visit(AssertStmt n, Object arg) {
		if(n.getCheck() != null)
		{
			n.getCheck().accept(this, arg);
		}
	}

	@Override
	public void visit(BlockStmt n, Object arg) {
		if (n.getStmts() != null) {
			for (Statement s : n.getStmts()) {
				s.accept(this, arg);
			}
		}
	}

	@Override
	public void visit(LabeledStmt n, Object arg) {
	}

	@Override
	public void visit(EmptyStmt n, Object arg) {
	}

	@Override
	public void visit(ExpressionStmt n, Object arg) {
		n.getExpression().accept(this, arg);
	}

	@Override
	public void visit(SwitchStmt n, Object arg) {
	}

	@Override
	public void visit(SwitchEntryStmt n, Object arg) {
	}

	@Override
	public void visit(BreakStmt n, Object arg) {
	}

	@Override
	public void visit(ReturnStmt n, Object arg) {
		if (n.getExpr() != null)
		{
			n.getExpr().accept(this, arg);
		}
	}

	@Override
	public void visit(IfStmt n, Object arg) {
		if (n.getCondition() != null)
		{
			n.getCondition().accept(this, arg);
		}
		
		if (n.getElseStmt() != null)
		{
			n.getElseStmt().accept(this, arg);
		}
		
		if (n.getThenStmt() != null)
		{
			n.getThenStmt().accept(this, arg);
		}
	}

	@Override
	public void visit(WhileStmt n, Object arg) {
		if (n.getBody() != null)
		{
			n.getBody().accept(this, arg);
		}
	}

	@Override
	public void visit(ContinueStmt n, Object arg) {
	}

	@Override
	public void visit(DoStmt n, Object arg) {
		if (n.getBody() != null)
		{
			n.getBody().accept(this, arg);
		}
	}

	@Override
	public void visit(ForeachStmt n, Object arg) {
		if (n.getBody() != null)
		{
			n.getBody().accept(this, arg);
		}
	}

	@Override
	public void visit(ForStmt n, Object arg) {
		if (n.getBody() != null)
		{
			n.getBody().accept(this, arg);
		}
	}

	@Override
	public void visit(ThrowStmt n, Object arg) {
	}

	@Override
	public void visit(SynchronizedStmt n, Object arg) {
	}

	@Override
	public void visit(TryStmt n, Object arg) {
		if (n.getTryBlock() != null)
		{
			n.getTryBlock().accept(this, arg);
		}
		
		if (n.getFinallyBlock() != null)
		{
			n.getFinallyBlock().accept(this, arg);
		}
	}

	@Override
	public void visit(CatchClause n, Object arg) {
	}
}