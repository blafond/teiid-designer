/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.core.metamodel.util;

import org.eclipse.emf.ecore.EObject;
import org.teiid.designer.core.metamodel.aspect.AspectManager;
import org.teiid.designer.core.metamodel.aspect.sql.SqlAspect;
import org.teiid.designer.core.metamodel.aspect.sql.SqlModelAspect;
import org.teiid.designer.core.metamodel.aspect.sql.SqlProcedureAspect;
import org.teiid.designer.core.metamodel.aspect.sql.SqlProcedureParameterAspect;


/**
 * @since 8.0
 */
public class ProcedureParameterNameFinder extends AbstractNameFinder {

    public ProcedureParameterNameFinder( final String groupName,
                                         final boolean isPartialname ) {
        super(groupName, isPartialname);
    }

    /**
     * @see org.teiid.designer.core.util.ModelVisitor#visit(org.eclipse.emf.ecore.EObject)
     */
    @Override
    public boolean visit( final EObject eObject ) {
        if (!super.visit(eObject)) {
            return false;
        }
        final SqlAspect sqlAspect = AspectManager.getSqlAspect(eObject);
        if (sqlAspect != null) {
            final String fullName = sqlAspect.getFullName(eObject);
            if (sqlAspect instanceof SqlModelAspect || sqlAspect instanceof SqlProcedureAspect) {
                // If the fullname of the parentAspect is not in the parent
                // path of the entity fullname then do not check the contents
                // of the parent
                if (fullName != null && !isParent(fullName.toUpperCase())) {
                    return false;
                }
            } else if (sqlAspect instanceof SqlProcedureParameterAspect) {
                // Check the ProcedureAspect against the group fullname to match
                if (fullName != null && foundMatch(fullName.toUpperCase(), eObject)) {
                    return false;
                }
            }
        }
        return true;
    }
}
