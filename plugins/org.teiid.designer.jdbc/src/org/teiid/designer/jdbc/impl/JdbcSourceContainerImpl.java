/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.jdbc.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.teiid.designer.jdbc.JdbcPackage;
import org.teiid.designer.jdbc.JdbcSource;
import org.teiid.designer.jdbc.JdbcSourceContainer;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Source Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.teiid.designer.jdbc.impl.JdbcSourceContainerImpl#getJdbcSources <em>Jdbc Sources</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 *
 * @since 8.0
 */
public class JdbcSourceContainerImpl extends EObjectImpl implements JdbcSourceContainer {
    /**
     * The cached value of the '{@link #getJdbcSources() <em>Jdbc Sources</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getJdbcSources()
     * @generated
     * @ordered
     */
    protected EList jdbcSources = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected JdbcSourceContainerImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return JdbcPackage.eINSTANCE.getJdbcSourceContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
	public EList getJdbcSources() {
        if (jdbcSources == null) {
            jdbcSources = new EObjectContainmentWithInverseEList(JdbcSource.class, this, JdbcPackage.JDBC_SOURCE_CONTAINER__JDBC_SOURCES, JdbcPackage.JDBC_SOURCE__JDBC_SOURCE_CONTAINER);
        }
        return jdbcSources;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
        if (featureID >= 0) {
            switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
                case JdbcPackage.JDBC_SOURCE_CONTAINER__JDBC_SOURCES:
                    return ((InternalEList)getJdbcSources()).basicAdd(otherEnd, msgs);
                default:
                    return eDynamicInverseAdd(otherEnd, featureID, baseClass, msgs);
            }
        }
        if (eContainer != null)
            msgs = eBasicRemoveFromContainer(msgs);
        return eBasicSetContainer(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
        if (featureID >= 0) {
            switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
                case JdbcPackage.JDBC_SOURCE_CONTAINER__JDBC_SOURCES:
                    return ((InternalEList)getJdbcSources()).basicRemove(otherEnd, msgs);
                default:
                    return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
            }
        }
        return eBasicSetContainer(null, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(EStructuralFeature eFeature, boolean resolve) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
            case JdbcPackage.JDBC_SOURCE_CONTAINER__JDBC_SOURCES:
                return getJdbcSources();
        }
        return eDynamicGet(eFeature, resolve);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(EStructuralFeature eFeature, Object newValue) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
            case JdbcPackage.JDBC_SOURCE_CONTAINER__JDBC_SOURCES:
                getJdbcSources().clear();
                getJdbcSources().addAll((Collection)newValue);
                return;
        }
        eDynamicSet(eFeature, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(EStructuralFeature eFeature) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
            case JdbcPackage.JDBC_SOURCE_CONTAINER__JDBC_SOURCES:
                getJdbcSources().clear();
                return;
        }
        eDynamicUnset(eFeature);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(EStructuralFeature eFeature) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
            case JdbcPackage.JDBC_SOURCE_CONTAINER__JDBC_SOURCES:
                return jdbcSources != null && !jdbcSources.isEmpty();
        }
        return eDynamicIsSet(eFeature);
    }

} //JdbcSourceContainerImpl
