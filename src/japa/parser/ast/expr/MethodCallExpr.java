/*
 * Copyright (C) 2007 J�lio Vilmar Gesser.
 * 
 * This file is part of Java 1.5 parser and Abstract Syntax Tree.
 *
 * Java 1.5 parser and Abstract Syntax Tree is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Java 1.5 parser and Abstract Syntax Tree is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Java 1.5 parser and Abstract Syntax Tree.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * Created on 05/10/2006
 */
package japa.parser.ast.expr;

import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.type.Type;
import japa.parser.ast.visitor.GenericVisitor;
import japa.parser.ast.visitor.VoidVisitor;

import java.util.List;

/**
 * @author Julio Vilmar Gesser
 */
public final class MethodCallExpr extends Expression {

    private final Expression scope;

    private final List<Type> typeArgs;

    private final String name;

    private final List<Expression> args;
    
    private BlockStmt yieldBlock = null;
    
    private String originalName;

    public MethodCallExpr(int line, int column, Expression scope, List<Type> typeArgs, String name, List<Expression> args) {
        super(line, column);
        this.scope = scope;
        this.typeArgs = typeArgs;
        this.name = name;
        this.args = args;
    }
    
    public MethodCallExpr(int line, int column, Expression scope, List<Type> typeArgs, String originalName, String name, List<Expression> args, BlockStmt block) {
        super(line, column);
        this.scope = scope;
        this.typeArgs = typeArgs;
        this.originalName = originalName;
        this.name = name;
        this.args = args;
        this.yieldBlock = block;
    }
    
    public BlockStmt getYieldBlock() {
    	return yieldBlock;
    }

    public Expression getScope() {
        return scope;
    }

    public List<Type> getTypeArgs() {
        return typeArgs;
    }
    
    public String getOriginalName() {
        return originalName;
    }

    public String getName() {
        return name;
    }

    public List<Expression> getArgs() {
        return args;
    }

    @Override
    public <A> void accept(VoidVisitor<A> v, A arg) {
        v.visit(this, arg);
    }

    @Override
    public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
        return v.visit(this, arg);
    }

}
