/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.mapping.ui.recursion.actions;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.teiid.designer.mapping.ui.UiConstants;
import org.teiid.designer.mapping.ui.UiPlugin;
import org.teiid.designer.mapping.ui.recursion.RecursionPanel;
import org.teiid.designer.ui.common.actions.AbstractAction;


/**
 * The <code>LaunchCriteriaBuilder</code> class launches the Criteria Builder
 * @since 8.0
 */
public class LaunchCriteriaBuilder extends AbstractAction {

    // =================================================================
    // FIELDS
    // =================================================================
    private RecursionPanel pnlRecursionPanel;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ///////////////////////////////////////////////////////////////////////////////////////////////
    
    public LaunchCriteriaBuilder( RecursionPanel pnlRecursionPanel ) {
        super( UiPlugin.getDefault() );
        setImageDescriptor(UiPlugin.getDefault().getImageDescriptor(UiConstants.Images.CRITERIA_BUILDER));
        this.pnlRecursionPanel = pnlRecursionPanel;
        setEnabled( false );            
    }
    
    // =================================================================
    // METHODS
    // =================================================================
    
    @Override
    protected void doRun() {
        // Tell ChoicePanel to show the Criteria builder
//        System.out.println("[LaunchCriteriaBuilder.doRun]"); //$NON-NLS-1$
        pnlRecursionPanel.launchCriteriaBuilder();
    }
    

    /**
     * @see org.eclipse.ui.ISelectionListener#selectionChanged(IWorkbenchPart, ISelection)
     * @since 4.0
     */
    public void selectionChanged() {
        if ( pnlRecursionPanel.canLaunchCriteriaBuilder() ) {         
            setEnabled( true );            
        } else {
            setEnabled( false );
        }
    }
}
