/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.metamodels.diagram.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.teiid.designer.metamodels.diagram.AbstractDiagramEntity;
import org.teiid.designer.metamodels.diagram.Diagram;
import org.teiid.designer.metamodels.diagram.DiagramContainer;
import org.teiid.designer.metamodels.diagram.DiagramEntity;
import org.teiid.designer.metamodels.diagram.DiagramLink;
import org.teiid.designer.metamodels.diagram.DiagramPackage;
import org.teiid.designer.metamodels.diagram.DiagramPosition;
import org.teiid.designer.metamodels.diagram.PresentationEntity;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.teiid.designer.metamodels.diagram.DiagramPackage
 * @generated
 *
 * @since 8.0
 */
public class DiagramAdapterFactory extends AdapterFactoryImpl {

    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static DiagramPackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DiagramAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = DiagramPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object.
     * <!-- begin-user-doc -->
     * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
     * <!-- end-user-doc -->
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject)object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch the delegates to the <code>createXXX</code> methods.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DiagramSwitch modelSwitch =
        new DiagramSwitch() {
            @Override
            public Object caseDiagramEntity(DiagramEntity object) {
                return createDiagramEntityAdapter();
            }
            @Override
            public Object casePresentationEntity(PresentationEntity object) {
                return createPresentationEntityAdapter();
            }
            @Override
            public Object caseDiagram(Diagram object) {
                return createDiagramAdapter();
            }
            @Override
            public Object caseDiagramContainer(DiagramContainer object) {
                return createDiagramContainerAdapter();
            }
            @Override
            public Object caseDiagramLink(DiagramLink object) {
                return createDiagramLinkAdapter();
            }
            @Override
            public Object caseAbstractDiagramEntity(AbstractDiagramEntity object) {
                return createAbstractDiagramEntityAdapter();
            }
            @Override
            public Object caseDiagramPosition(DiagramPosition object) {
                return createDiagramPositionAdapter();
            }
            @Override
            public Object defaultCase(EObject object) {
                return createEObjectAdapter();
            }
        };

    /**
     * Creates an adapter for the <code>target</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param target the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target) {
        return (Adapter)modelSwitch.doSwitch((EObject)target);
    }


    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.diagram.DiagramEntity <em>Entity</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.diagram.DiagramEntity
     * @generated
     */
    public Adapter createDiagramEntityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.diagram.PresentationEntity <em>Presentation Entity</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.diagram.PresentationEntity
     * @generated
     */
    public Adapter createPresentationEntityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.diagram.Diagram <em>Diagram</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.diagram.Diagram
     * @generated
     */
    public Adapter createDiagramAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.diagram.DiagramContainer <em>Container</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.diagram.DiagramContainer
     * @generated
     */
    public Adapter createDiagramContainerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.diagram.DiagramLink <em>Link</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.diagram.DiagramLink
     * @generated
     */
    public Adapter createDiagramLinkAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.diagram.AbstractDiagramEntity <em>Abstract Diagram Entity</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.diagram.AbstractDiagramEntity
     * @generated
     */
    public Adapter createAbstractDiagramEntityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.teiid.designer.metamodels.diagram.DiagramPosition <em>Position</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.teiid.designer.metamodels.diagram.DiagramPosition
     * @generated
     */
    public Adapter createDiagramPositionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case.
     * <!-- begin-user-doc -->
     * This default implementation returns null.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} //DiagramAdapterFactory
