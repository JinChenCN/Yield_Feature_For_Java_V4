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
import japa.parser.ast.expr.AssignExpr.Operator;
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

import java.util.Iterator;

import se701.A2SemanticsException;
import symtab.ClassSymbol;
import symtab.MethodSymbol;
import symtab.Scope;
import symtab.Symbol;
import symtab.VariableSymbol;


public class ResolvingVariablesVisitor implements VoidVisitor<Object> {

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
		Symbol symOfVariable = currentScope.resolve(n.getId().getName());

		if (symOfVariable == null) {
			throw new A2SemanticsException("Variable " + n.getId().toString() + " on line " + n.getId().getBeginLine() 
					+ " is not defined.");
		}

//		if (n.getId().getBeginLine() < symOfVariable.getDefinedLine()) {
//			throw new A2SemanticsException("Variable " + symOfVariable.getName() + " on line " + n.getId().getBeginLine()
//					+ " cannot be used before its definition.");
//		}
//		
//		if (n.getId().getBeginLine() == symOfVariable.getDefinedLine()) {
//			if(n.getBeginColumn() < symOfVariable.getDefinedColumn())
//			throw new A2SemanticsException("Variable " + symOfVariable.getName() + " on line " + n.getId().getBeginLine() + " at column " + n.getBeginColumn()
//					+ " cannot be used before its definition." );
//		}
		
		Expression expr = n.getInit();
		String typeOfLeft = symOfVariable.getType().getName();

		if (expr != null)
		{
			checkTypeOfExp(expr, typeOfLeft, n.getId().getBeginLine(), n.getId().getBeginColumn(),symOfVariable);
		}
		
	}
	
	private void checkTypeOfExp(Expression expr, String typeOfTarget, int line, int column, Symbol symOfVariable) {
		if (expr instanceof BinaryExpr) {
			expr = ((BinaryExpr) expr).getLeft();
		}

		if (expr instanceof BooleanLiteralExpr) {
			if (typeOfTarget != "boolean") {
				throw new A2SemanticsException("Cannot convert from boolean to " + typeOfTarget + " on line " + line + ".");
			}
		} else if (expr instanceof NullLiteralExpr) {
			if (typeOfTarget == "int" || typeOfTarget == "byte" || typeOfTarget == "short" || typeOfTarget == "long"
					|| typeOfTarget == "double" || typeOfTarget == "float" || typeOfTarget == "boolean"
					|| typeOfTarget == "char") {
				throw new A2SemanticsException("Variable " + symOfVariable.getName() + " cannot be null on line " + line + ".");
			}
		} else if (expr instanceof CharLiteralExpr) {
			if (typeOfTarget != "char") {
				throw new A2SemanticsException("Cannot convert from char to " + typeOfTarget + " on line " + line + ".");
			}
		} else if (expr instanceof DoubleLiteralExpr) {
			if (typeOfTarget != "double") {
				throw new A2SemanticsException("Cannot convert from double to " + typeOfTarget + " on line " + line + ".");
			}
		} else if (expr instanceof IntegerLiteralExpr) {
			if (typeOfTarget != "int") {
				throw new A2SemanticsException("Cannot convert from int to " + typeOfTarget + " on line " + line + ".");
			}
		} else if (expr instanceof LongLiteralExpr) {
			if (typeOfTarget != "long") {
				throw new A2SemanticsException("Cannot convert from int to " + typeOfTarget + " on line " + line + ".");
			}
		} else if (expr instanceof StringLiteralExpr) {
			if (typeOfTarget != "String") {
				throw new A2SemanticsException("Cannot convert from String to " + typeOfTarget + " on line " + line + ".");
			}
		} else if (expr instanceof MethodCallExpr) {
			Symbol resolvedSymbol = currentScope
					.resolve(((MethodCallExpr) expr).getName().toString());

			if (resolvedSymbol == null) {
				throw new A2SemanticsException("Method " 
						+ ((MethodCallExpr) expr).getName().toString() + " on line " + line + " is not defined.");
			}

			if (resolvedSymbol instanceof MethodSymbol) {
				MethodSymbol m = (MethodSymbol) resolvedSymbol;
				String returnType = m.getReturnType().getName();
				if (typeOfTarget != returnType) {
					throw new A2SemanticsException("Cannot convert from " + returnType + " to " + typeOfTarget + " on line " + line + ".");
				}
			} else {
				throw new A2SemanticsException(((MethodCallExpr) expr).getName().toString() + " on line " + line + " is not a valid method.");
			}
		} else if (expr instanceof ObjectCreationExpr) {
			Symbol resolvedSymbol = currentScope.resolve(((ObjectCreationExpr) expr).getType().getName());

			if (resolvedSymbol == null)
			{
				throw new A2SemanticsException(((ObjectCreationExpr) expr).getType().getName() + " on line " + line + " is not defined.");
			}
			
			if (resolvedSymbol != symOfVariable.getType()) {
				throw new A2SemanticsException("Cannot convert from " + resolvedSymbol.getName() + " to " + typeOfTarget + " on line " + line + ".");
			}
		} else if (expr instanceof NameExpr) {
			Symbol resolvedSymbol = currentScope.resolve(((NameExpr) expr).getName());

			if(resolvedSymbol == null)
			{
				throw new A2SemanticsException(((NameExpr) expr).getName() + " on line " + line + " is not defined.");
			}
			
			if (line < resolvedSymbol.getDefinedLine()) {
				throw new A2SemanticsException("Variable " + ((NameExpr) expr).getName() + " on line " + line
						+ " cannot be used before its definition.");
			}
			
			if (line == resolvedSymbol.getDefinedLine()) {
				if(column < resolvedSymbol.getDefinedColumn())
				throw new A2SemanticsException("Variable " + resolvedSymbol.getName() + " on line " + line + " at column " + column
						+ " cannot be used before its definition." );
			}
			
			if (resolvedSymbol.getType() != symOfVariable.getType()) {
				throw new A2SemanticsException("Cannot convert from " + resolvedSymbol.getName() + " to " + typeOfTarget + " on line " + line + ".");
			}
		}
	
	}

	@Override
	public void visit(VariableDeclaratorId n, Object arg) {
	}

	@Override
	public void visit(ConstructorDeclaration n, Object arg) {
		currentScope = n.getEnclosingScope();

		if (n.getBlock() != null) {
			n.getBlock().accept(this, arg);
		}

		currentScope = n.getEnclosingScope();
	}

	@Override
	public void visit(MethodDeclaration n, Object arg) {
		currentScope = n.getEnclosingScope();

		if (n.getBody() != null) {
			n.getBody().accept(this, arg);
		}

		currentScope = currentScope.getEnclosingScope();
	}

	@Override
	public void visit(Parameter n, Object arg) {
	}

	@Override
	public void visit(EmptyMemberDeclaration n, Object arg) {
	}

	@Override
	public void visit(InitializerDeclaration n, Object arg) {
		currentScope = n.getEnclosingScope();

		if (n.getBlock() != null) {
			n.getBlock().accept(this, arg);
		}

		currentScope = currentScope.getEnclosingScope();
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
		currentScope = n.getEnclosingScope();
		Symbol target = null;
		if (n.getTarget().toString().contains(".")) {
			String[] parts = n.getTarget().toString().split("\\.");
			if(parts[0].equals("this")){
				MethodSymbol classSym = (MethodSymbol)(currentScope.getEnclosingScope());
				target = classSym.resolveMember(parts[1]);
				if(target == null)
				{
					throw new A2SemanticsException("Variable " + n.getTarget().toString() + " on line " + n.getTarget().getBeginLine() + " is not defined.");
				}
			} else {
				Symbol targetClassSym = currentScope.resolve(parts[0]);
				if(targetClassSym == null)
				{
					throw new A2SemanticsException("Variable " + parts[0] + " on line " + n.getTarget().getBeginLine() + " is not defined.");
				}
				symtab.Type type = targetClassSym.getType();
				ClassSymbol targetClass = (ClassSymbol)type;
				target = targetClass.resolveMember(parts[1]);
				if(target == null)
				{
					throw new A2SemanticsException("Variable " + n.getTarget().toString() + " on line " + n.getTarget().getBeginLine() + " is not defined.");
				}
			}
		} else {
			target = currentScope.resolve(n.getTarget().toString());
			if (target == null) {
				throw new A2SemanticsException("Variable " + n.getTarget().toString() + " on line " + n.getTarget().getBeginLine() + " is not defined.");
			}
		}	
		String typeOfTarget = target.getType().getName();
		
		Operator operator = n.getOperator();


		switch(operator) {
		case assign: // =
			Expression expr = n.getValue();
			if (expr != null)
			{
				checkTypeOfExp(expr, typeOfTarget, n.getTarget().getBeginLine(), n.getTarget().getBeginColumn(), target);
			}
			break;
		case plus: // +=
			break;
		case minus: // -=
			break;
		case star: // *=
			break;
		case slash: // /=
			break;
		case and: // &=
			break;
		case or: // |=
			break;
		case xor: // ^=
			break;
		case rem: // %=
			break;
		case lShift: // <<=
			break;
		case rSignedShift: // >>=
			break;
		case rUnsignedShift: // >>>=
			break;
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

		if (n.getYieldBlock() != null) {
			n.getYieldBlock().accept(this, arg);
		}

		currentScope = currentScope.getEnclosingScope();
	}

	@Override
	public void visit(NameExpr n, Object arg) {
	}

	@Override
	public void visit(ObjectCreationExpr n, Object arg) {
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
	}

	@Override
	public void visit(VariableDeclarationExpr n, Object arg) {
		n.getType().accept(this, arg);

		for (Iterator<VariableDeclarator> i = n.getVars().iterator(); i
				.hasNext();) {
			VariableDeclarator v = i.next();
			v.accept(this, arg);
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
	}

	@Override
	public void visit(ExplicitConstructorInvocationStmt n, Object arg) {
	}

	@Override
	public void visit(TypeDeclarationStmt n, Object arg) {
	}

	@Override
	public void visit(AssertStmt n, Object arg) {
	}

	@Override
	public void visit(BlockStmt n, Object arg) {
		currentScope = n.getEnclosingScope();

		if (n.getStmts() != null) {
			for (Statement s : n.getStmts()) {
				s.accept(this, arg);
			}
		}

		currentScope = currentScope.getEnclosingScope();
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
		currentScope = n.getEnclosingScope();

		n.getExpr().accept(this, arg);
	}

	@Override
	public void visit(IfStmt n, Object arg) {
		currentScope = n.getEnclosingScope();

		n.getThenStmt().accept(this, arg);

		if (n.getElseStmt() != null) {
			n.getElseStmt().accept(this, arg);
		}
	}

	@Override
	public void visit(WhileStmt n, Object arg) {
		currentScope = n.getEnclosingScope();

		n.getBody().accept(this, arg);
	}

	@Override
	public void visit(ContinueStmt n, Object arg) {
	}

	@Override
	public void visit(DoStmt n, Object arg) {
	}

	@Override
	public void visit(ForeachStmt n, Object arg) {
		currentScope = n.getEnclosingScope();

		n.getBody().accept(this, arg);
	}

	@Override
	public void visit(ForStmt n, Object arg) {
		currentScope = n.getEnclosingScope();

		n.getBody().accept(this, arg);
	}

	@Override
	public void visit(ThrowStmt n, Object arg) {
	}

	@Override
	public void visit(SynchronizedStmt n, Object arg) {
	}

	@Override
	public void visit(TryStmt n, Object arg) {
		currentScope = n.getEnclosingScope();

		n.getTryBlock().accept(this, arg);

		if (n.getCatchs() != null) {
			for (CatchClause c : n.getCatchs()) {
				c.accept(this, arg);
			}
		}

		if (n.getFinallyBlock() != null) {
			n.getFinallyBlock().accept(this, arg);
		}
	}

	@Override
	public void visit(CatchClause n, Object arg) {
		currentScope = n.getEnclosingScope();

		n.getCatchBlock().accept(this, arg);
	}

	@Override
	public void visit(YieldStmt n, Object arg) {
		currentScope = n.getEnclosingScope();
	}
}
