/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.teiid.designer.query.metadata;

import java.util.List;
import org.teiid.designer.query.sql.lang.ISPParameter;

/**
* This class encapsulates everything needed to pass between runtime metadata
* and the QueryResolver via the facades
*/

public interface IStoredProcedureInfo<P extends ISPParameter, Q extends IQueryNode> {

    String getProcedureCallableName();
    
    void setProcedureCallableName(String callableName);
    
    Object getModelID();
    
    void setModelID(Object modelID);
    
    Object getProcedureID();
    
    void setProcedureID(Object procedureID);
    
    List<P> getParameters();
    
    void setParameters(List<P> parameters);
    
    void addParameter(P parameter);

    boolean returnsResultSet();

    boolean returnsResultParameter();
    
    int getUpdateCount();

    void setUpdateCount(int updateCount);

    Q getQueryPlan();

    void setQueryPlan(Q queryNode);
    
}