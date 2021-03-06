/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.metamodels.function.aspects.validation.rules;

import org.teiid.designer.core.validation.rules.StringNameRule;
import org.teiid.designer.metamodels.function.FunctionPackage;


/**
 * ScalarFunctionNameRule
 *
 * @since 8.0
 */
public class FunctionEntityNameRule extends StringNameRule {

    /**
     * Construct an instance of ScalarFunctionNameRule.
     * 
     */
    public FunctionEntityNameRule() {
        super(FunctionPackage.FUNCTION__NAME);
    }
    
    /**
     * @see org.teiid.designer.core.validation.rules.StringNameRule#validateUniqueness()
     */
    @Override
    protected boolean validateUniqueness() {
        return false;
    }

    


}
