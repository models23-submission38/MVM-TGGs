/**
 */
package de.mdelab.migmm.history.modisco.java.adapted.java_adapted.impl;

import de.mdelab.migmm.history.impl.ContainmentEdgeWithHistoryImpl;
import de.mdelab.migmm.history.modisco.java.adapted.java_adapted.Java_adaptedPackage;
import de.mdelab.migmm.history.modisco.java.adapted.java_adapted.Modifier_adapted;
import de.mdelab.migmm.history.modisco.java.adapted.java_adapted.VariableDeclarationExpression_adapted_modifierItem;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Variable Declaration Expression adapted modifier Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.mdelab.migmm.history.modisco.java.adapted.java_adapted.impl.VariableDeclarationExpression_adapted_modifierItemImpl#getModifier <em>Modifier</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VariableDeclarationExpression_adapted_modifierItemImpl extends ContainmentEdgeWithHistoryImpl implements VariableDeclarationExpression_adapted_modifierItem {
	/**
	 * The cached value of the '{@link #getModifier() <em>Modifier</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModifier()
	 * @generated
	 * @ordered
	 */
	protected Modifier_adapted modifier;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VariableDeclarationExpression_adapted_modifierItemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return Java_adaptedPackage.eINSTANCE.getVariableDeclarationExpression_adapted_modifierItem();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Modifier_adapted getModifier() {
		if (modifier != null && modifier.eIsProxy()) {
			InternalEObject oldModifier = (InternalEObject)modifier;
			modifier = (Modifier_adapted)eResolveProxy(oldModifier);
			if (modifier != oldModifier) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, Java_adaptedPackage.VARIABLE_DECLARATION_EXPRESSION_ADAPTED_MODIFIER_ITEM__MODIFIER, oldModifier, modifier));
			}
		}
		return modifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Modifier_adapted basicGetModifier() {
		return modifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setModifier(Modifier_adapted newModifier) {
		Modifier_adapted oldModifier = modifier;
		modifier = newModifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, Java_adaptedPackage.VARIABLE_DECLARATION_EXPRESSION_ADAPTED_MODIFIER_ITEM__MODIFIER, oldModifier, modifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case Java_adaptedPackage.VARIABLE_DECLARATION_EXPRESSION_ADAPTED_MODIFIER_ITEM__MODIFIER:
				if (resolve) return getModifier();
				return basicGetModifier();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case Java_adaptedPackage.VARIABLE_DECLARATION_EXPRESSION_ADAPTED_MODIFIER_ITEM__MODIFIER:
				setModifier((Modifier_adapted)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case Java_adaptedPackage.VARIABLE_DECLARATION_EXPRESSION_ADAPTED_MODIFIER_ITEM__MODIFIER:
				setModifier((Modifier_adapted)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case Java_adaptedPackage.VARIABLE_DECLARATION_EXPRESSION_ADAPTED_MODIFIER_ITEM__MODIFIER:
				return modifier != null;
		}
		return super.eIsSet(featureID);
	}

} //VariableDeclarationExpression_adapted_modifierItemImpl
