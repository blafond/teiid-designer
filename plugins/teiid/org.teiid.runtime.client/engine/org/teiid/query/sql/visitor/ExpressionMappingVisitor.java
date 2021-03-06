/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */

package org.teiid.query.sql.visitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.teiid.designer.runtime.version.spi.ITeiidServerVersion;
import org.teiid.designer.runtime.version.spi.TeiidServerVersion;
import org.teiid.query.parser.LanguageVisitor;
import org.teiid.query.parser.TeiidNodeFactory.ASTNodes;
import org.teiid.query.sql.lang.BetweenCriteria;
import org.teiid.query.sql.lang.CompareCriteria;
import org.teiid.query.sql.lang.DynamicCommand;
import org.teiid.query.sql.lang.ExpressionCriteria;
import org.teiid.query.sql.lang.GroupBy;
import org.teiid.query.sql.lang.Insert;
import org.teiid.query.sql.lang.IsNullCriteria;
import org.teiid.query.sql.lang.LanguageObject;
import org.teiid.query.sql.lang.Limit;
import org.teiid.query.sql.lang.MatchCriteria;
import org.teiid.query.sql.lang.ObjectColumn;
import org.teiid.query.sql.lang.ObjectTable;
import org.teiid.query.sql.lang.OrderByItem;
import org.teiid.query.sql.lang.SPParameter;
import org.teiid.query.sql.lang.Select;
import org.teiid.query.sql.lang.SetClause;
import org.teiid.query.sql.lang.SetCriteria;
import org.teiid.query.sql.lang.StoredProcedure;
import org.teiid.query.sql.lang.SubqueryCompareCriteria;
import org.teiid.query.sql.lang.SubquerySetCriteria;
import org.teiid.query.sql.lang.XMLColumn;
import org.teiid.query.sql.lang.XMLTable;
import org.teiid.query.sql.navigator.PreOrPostOrderNavigator;
import org.teiid.query.sql.proc.AssignmentStatement;
import org.teiid.query.sql.proc.ExceptionExpression;
import org.teiid.query.sql.proc.ReturnStatement;
import org.teiid.query.sql.symbol.AggregateSymbol;
import org.teiid.query.sql.symbol.AliasSymbol;
import org.teiid.query.sql.symbol.Array;
import org.teiid.query.sql.symbol.CaseExpression;
import org.teiid.query.sql.symbol.DerivedColumn;
import org.teiid.query.sql.symbol.ElementSymbol;
import org.teiid.query.sql.symbol.Expression;
import org.teiid.query.sql.symbol.ExpressionSymbol;
import org.teiid.query.sql.symbol.Function;
import org.teiid.query.sql.symbol.MultipleElementSymbol;
import org.teiid.query.sql.symbol.QueryString;
import org.teiid.query.sql.symbol.SearchedCaseExpression;
import org.teiid.query.sql.symbol.Symbol;
import org.teiid.query.sql.symbol.WindowSpecification;
import org.teiid.query.sql.symbol.XMLElement;
import org.teiid.query.sql.symbol.XMLParse;
import org.teiid.query.sql.symbol.XMLSerialize;


/**
 * It is important to use a Post Navigator with this class, 
 * otherwise a replacement containing itself will not work
 */
public class ExpressionMappingVisitor extends LanguageVisitor {

    private Map symbolMap;
    private boolean clone = true;
    private boolean elementSymbolsOnly;

    /**
     * Constructor for ExpressionMappingVisitor.
     * @param teiidVersion
     * @param symbolMap Map of ElementSymbol to Expression
     */
    public ExpressionMappingVisitor(ITeiidServerVersion teiidVersion, Map symbolMap) {
        super(teiidVersion);
        this.symbolMap = symbolMap;
    }

    /**
     * @param teiidVersion
     * @param symbolMap
     * @param clone
     */
    public ExpressionMappingVisitor(TeiidServerVersion teiidVersion, Map symbolMap, boolean clone) {
        super(teiidVersion);
        this.symbolMap = symbolMap;
        this.clone = clone;
    }
        
    protected boolean createAliases() {
    	return true;
    }
    
    @Override
    public void visit(Select obj) {
    	List<Expression> symbols = obj.getSymbols();
    	for (int i = 0; i < symbols.size(); i++) {
            Expression symbol = symbols.get(i);
            
            if (symbol instanceof MultipleElementSymbol) {
            	continue;
            }
            
            Expression replacmentSymbol = replaceSymbol(symbol, true);
            
            symbols.set(i, replacmentSymbol);
        }
    }
    
    /**
     * @return true if clone, false otherwise
     */
    public boolean isClone() {
		return clone;
	}
    
    /**
     * @param clone
     */
    public void setClone(boolean clone) {
		this.clone = clone;
	}
    
    @Override
    public void visit(DerivedColumn obj) {
    	Expression original = obj.getExpression();
    	obj.setExpression(replaceExpression(original));
    	if (obj.isPropagateName() && obj.getAlias() == null && !(obj.getExpression() instanceof ElementSymbol) && original instanceof ElementSymbol) {
    		obj.setAlias(((ElementSymbol)original).getShortName());
    	}
    }
    
    @Override
    public void visit(XMLTable obj) {
    	for (XMLColumn col : obj.getColumns()) {
    		Expression exp = col.getDefaultExpression();
    		if (exp != null) {
    			col.setDefaultExpression(replaceExpression(exp));
    		}
		}
    }
    
    @Override
    public void visit(ObjectTable obj) {
    	for (ObjectColumn col : obj.getColumns()) {
    		Expression exp = col.getDefaultExpression();
    		if (exp != null) {
    			col.setDefaultExpression(replaceExpression(exp));
    		}
		}
    }
    
    @Override
    public void visit(XMLSerialize obj) {
    	obj.setExpression(replaceExpression(obj.getExpression()));
    }
    
    @Override
    public void visit(XMLParse obj) {
    	obj.setExpression(replaceExpression(obj.getExpression()));
    }
    
	private Expression replaceSymbol(Expression ses,
			boolean alias) {
		Expression expr = ses;
		String name = Symbol.getShortName(ses);
		if (ses instanceof ExpressionSymbol) {
		    expr = ((ExpressionSymbol)ses).getExpression();
		}
		
		Expression replacementSymbol = replaceExpression(expr);
		
		if (!(replacementSymbol instanceof Symbol)) {
		    replacementSymbol = getTeiidParser().createASTNode(ASTNodes.EXPRESSION_SYMBOL);
		    ((ExpressionSymbol) replacementSymbol).setName(name);
		    ((ExpressionSymbol) replacementSymbol).setExpression(replacementSymbol);
		} else if (alias && createAliases() && !Symbol.getShortName(replacementSymbol).equals(name)) {
		    AliasSymbol aliasSymbol = getTeiidParser().createASTNode(ASTNodes.ALIAS_SYMBOL);
		    aliasSymbol.setName(name);
            aliasSymbol.setSymbol(replacementSymbol);
            replacementSymbol = aliasSymbol;
		}
		return replacementSymbol;
	}
    
    /** 
     * @see LanguageVisitor#visit(org.teiid.query.sql.symbol.AliasSymbol)
     */
    @Override
    public void visit(AliasSymbol obj) {
        Expression replacement = replaceExpression(obj.getSymbol());
        obj.setSymbol(replacement);
    }
    
    @Override
    public void visit(ExpressionSymbol expr) {
        expr.setExpression(replaceExpression(expr.getExpression()));
    }
    
    /**
     * @see LanguageVisitor#visit(BetweenCriteria)
     */
    @Override
    public void visit(BetweenCriteria obj) {
        obj.setExpression( replaceExpression(obj.getExpression()) );
        obj.setLowerExpression( replaceExpression(obj.getLowerExpression()) );
        obj.setUpperExpression( replaceExpression(obj.getUpperExpression()) );
    }
    
    @Override
    public void visit(CaseExpression obj) {
        obj.setExpression(replaceExpression(obj.getExpression()));
        final int whenCount = obj.getWhenCount();
        ArrayList whens = new ArrayList(whenCount);
        ArrayList thens = new ArrayList(whenCount);
        for (int i = 0; i < whenCount; i++) {
            whens.add(replaceExpression(obj.getWhenExpression(i)));
            thens.add(replaceExpression(obj.getThenExpression(i)));
        }
        obj.setWhen(whens, thens);
        if (obj.getElseExpression() != null) {
            obj.setElseExpression(replaceExpression(obj.getElseExpression()));
        }
    }

    /**
     * @see LanguageVisitor#visit(CompareCriteria)
     */
    @Override
    public void visit(CompareCriteria obj) {
        obj.setLeftExpression( replaceExpression(obj.getLeftExpression()) );
        obj.setRightExpression( replaceExpression(obj.getRightExpression()) );
    }

    /**
     * @see LanguageVisitor#visit(Function)
     */
    @Override
    public void visit(Function obj) {
        Expression[] args = obj.getArgs();
        if(args != null && args.length > 0) {
            for(int i=0; i<args.length; i++) {
                args[i] = replaceExpression(args[i]);
            }
        }
    }

    /**
     * @see LanguageVisitor#visit(IsNullCriteria)
     */
    @Override
    public void visit(IsNullCriteria obj) {
        obj.setExpression( replaceExpression(obj.getExpression()) );
    }

    /**
     * @see LanguageVisitor#visit(MatchCriteria)
     */
    @Override
    public void visit(MatchCriteria obj) {
        obj.setLeftExpression( replaceExpression(obj.getLeftExpression()) );
        obj.setRightExpression( replaceExpression(obj.getRightExpression()) );
    }

    @Override
    public void visit(SearchedCaseExpression obj) {
        int whenCount = obj.getWhenCount();
        ArrayList<Expression> thens = new ArrayList<Expression>(whenCount);
        for (int i = 0; i < whenCount; i++) {
            thens.add(replaceExpression(obj.getThenExpression(i)));
        }
        obj.setWhen(obj.getWhen(), thens);
        if (obj.getElseExpression() != null) {
            obj.setElseExpression(replaceExpression(obj.getElseExpression()));
        }
    }

    /**
     * @see LanguageVisitor#visit(SetCriteria)
     */
    @Override
    public void visit(SetCriteria obj) {
        obj.setExpression( replaceExpression(obj.getExpression()) );
        
        if (obj.isAllConstants()) {
        	return;
        }
        
        Collection newValues = new ArrayList(obj.getValues().size());        
        Iterator valueIter = obj.getValues().iterator();
        while(valueIter.hasNext()) {
            newValues.add( replaceExpression( (Expression) valueIter.next() ) );
        }
        
        obj.setValues(newValues);                    
    }

    /**
     * @see LanguageVisitor#visit(org.teiid.query.sql.lang.SubqueryCompareCriteria)
     */
    @Override
    public void visit(SubqueryCompareCriteria obj) {
        obj.setLeftExpression( replaceExpression(obj.getLeftExpression()) );
    }
    
    /**
     * @see LanguageVisitor#visit(org.teiid.query.sql.lang.SubquerySetCriteria)
     */
    @Override
    public void visit(SubquerySetCriteria obj) {
        obj.setExpression( replaceExpression(obj.getExpression()) );
    }    
    
    /**
     * Find an expression in the symbol map that could replace
     * the given expression
     *
     * @param element
     * @return new expression
     */
    public Expression replaceExpression(Expression element) {
    	if (elementSymbolsOnly && !(element instanceof ElementSymbol)) {
    		return element;
    	}
        Expression mapped = (Expression) this.symbolMap.get(element);
        if(mapped != null) {
        	if (clone) {
        		return mapped.clone();
        	}
        	return mapped;
        }
        return element;    
    }
    
    @Override
    public void visit(StoredProcedure obj) {
    	for (Iterator<SPParameter> paramIter = obj.getInputParameters().iterator(); paramIter.hasNext();) {
			SPParameter param = paramIter.next();
            Expression expr = param.getExpression();
            param.setExpression(replaceExpression(expr));
        }
    }
    
    @Override
    public void visit(AggregateSymbol obj) {
    	visit((Function)obj);
    	if (obj.getCondition() != null) { 
    		obj.setCondition(replaceExpression(obj.getCondition()));
    	}
    }
    
    /**
     * Swap each ElementSymbol in GroupBy (other symbols are ignored).
     * @param obj Object to remap
     */
    @Override
    public void visit(GroupBy obj) {        
    	List<Expression> symbols = obj.getSymbols();
		for (int i = 0; i < symbols.size(); i++) {
            Expression symbol = symbols.get(i);
            symbols.set(i, replaceExpression(symbol));
        }
    }
    
    @Override
    public void visit(OrderByItem obj) {
    	obj.setSymbol(replaceSymbol(obj.getSymbol(), obj.getExpressionPosition() != -1));
    }
    
    @Override
    public void visit(Limit obj) {
        if (obj.getOffset() != null) {
            obj.setOffset(replaceExpression(obj.getOffset()));
        }
        obj.setRowLimit(replaceExpression(obj.getRowLimit()));
    }
       
    @Override
    public void visit(DynamicCommand obj) {
        obj.setSql(replaceExpression(obj.getSql()));
        if (obj.getUsing() != null) {
	        for (SetClause clause : obj.getUsing().getClauses()) {
				visit(clause);
			}
        }
    }
    
    @Override
    public void visit(SetClause obj) {
    	obj.setValue(replaceExpression(obj.getValue()));
    }
    
    @Override
    public void visit(QueryString obj) {
    	obj.setPath(replaceExpression(obj.getPath()));
    }
    
    @Override
    public void visit(ExpressionCriteria obj) {
    	obj.setExpression(replaceExpression(obj.getExpression()));
    }
    
    /**
     * The object is modified in place, so is not returned.
     * @param obj Language object
     * @param exprMap Expression map, Expression to Expression
     */
    public static void mapExpressions(LanguageObject obj, Map<? extends Expression, ? extends Expression> exprMap) {
        mapExpressions(obj, exprMap, false);
    }
    
    /**
     * The object is modified in place, so is not returned.
     * @param obj Language object
     * @param exprMap Expression map, Expression to Expression
     */
    public static void mapExpressions(LanguageObject obj, Map<? extends Expression, ? extends Expression> exprMap, boolean deep) {
        if(obj == null || exprMap == null || exprMap.isEmpty()) { 
            return;
        }
        ITeiidServerVersion teiidVersion = obj.getTeiidVersion();
        final ExpressionMappingVisitor visitor = new ExpressionMappingVisitor(teiidVersion, exprMap);
        visitor.elementSymbolsOnly = true;
        boolean preOrder = true;
        boolean useReverseMapping = true;
        for (Map.Entry<? extends Expression, ? extends Expression> entry : exprMap.entrySet()) {
        	if (!(entry.getKey() instanceof ElementSymbol)) {
        		visitor.elementSymbolsOnly = false;
        		break;
        	}
		}
        if (!visitor.elementSymbolsOnly) {
        	for (Map.Entry<? extends Expression, ? extends Expression> entry : exprMap.entrySet()) {
            	if (!(entry.getValue() instanceof ElementSymbol)) {
            		useReverseMapping = !Collections.disjoint(GroupsUsedByElementsVisitor.getGroups(exprMap.keySet()),
                    		GroupsUsedByElementsVisitor.getGroups(exprMap.values()));
            		break;
            	}
    		}
        } else {
        	preOrder = false;
        	useReverseMapping = false;
        }
        
        if (useReverseMapping) {
	        final Set<Expression> reverseSet = new HashSet<Expression>(exprMap.values());
	        PreOrPostOrderNavigator pon = new PreOrPostOrderNavigator(visitor, PreOrPostOrderNavigator.PRE_ORDER, deep) {
	        	@Override
	        	protected void visitNode(LanguageObject obj) {
	        		if (!(obj instanceof Expression) || !reverseSet.contains(obj)) {
	            		super.visitNode(obj);
	        		}
	        	}
	        };
	        obj.acceptVisitor(pon);
        } else {
        	PreOrPostOrderNavigator.doVisit(obj, visitor, preOrder, deep);
        }
    }
    
    protected void setVariableValues(Map variableValues) {
        this.symbolMap = variableValues;
    }

    protected Map getVariableValues() {
        return symbolMap;
    }    
    
    /** 
     * @see LanguageVisitor#visit(org.teiid.query.sql.proc.AssignmentStatement)
     * @since 5.0
     */
    @Override
    public void visit(AssignmentStatement obj) {
        obj.setExpression(replaceExpression(obj.getExpression()));
    }
    
    /** 
     * @see LanguageVisitor#visit(org.teiid.query.sql.lang.Insert)
     * @since 5.0
     */
    @Override
    public void visit(Insert obj) {
        for (int i = 0; i < obj.getValues().size(); i++) {
            obj.getValues().set(i, replaceExpression(obj.getValues().get(i)));
        }
    }
    
    @Override
    public void visit(XMLElement obj) {
    	for (int i = 0; i < obj.getContent().size(); i++) {
    		obj.getContent().set(i, replaceExpression(obj.getContent().get(i)));
    	}
    }
    
    @Override
    public void visit(WindowSpecification windowSpecification) {
    	if (windowSpecification.getPartition() == null) {
    		return;
    	}
    	List<Expression> partition = windowSpecification.getPartition();
		for (int i = 0; i < partition.size(); i++) {
    		partition.set(i, replaceExpression(partition.get(i)));
    	}
    }

    @Override
    public void visit(Array array) {
        List<Expression> exprs = array.getExpressions();
        for (int i = 0; i < exprs.size(); i++) {
            exprs.set(i, replaceExpression(exprs.get(i)));
        }
    }

    @Override
    public void visit(ExceptionExpression exceptionExpression) {
    	if (exceptionExpression.getMessage() != null) {
    		exceptionExpression.setMessage(replaceExpression(exceptionExpression.getMessage()));
    	}
    	if (exceptionExpression.getSqlState() != null) {
    		exceptionExpression.setSqlState(replaceExpression(exceptionExpression.getSqlState()));
    	}
    	if (exceptionExpression.getErrorCode() != null) {
    		exceptionExpression.setErrorCode(replaceExpression(exceptionExpression.getErrorCode()));
    	}
    	if (exceptionExpression.getParent() != null) {
    		exceptionExpression.setParent(replaceExpression(exceptionExpression.getParent()));
    	}
    }
    
    @Override
    public void visit(ReturnStatement obj) {
    	if (obj.getExpression() != null) {
    		obj.setExpression(replaceExpression(obj.getExpression()));
    	}
    }
    
}
