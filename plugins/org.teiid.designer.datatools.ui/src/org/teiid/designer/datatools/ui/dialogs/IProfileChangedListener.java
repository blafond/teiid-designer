/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.datatools.ui.dialogs;

import org.eclipse.datatools.connectivity.IConnectionProfile;

/**
 * 
 *
 * @since 8.0
 */
public interface IProfileChangedListener {

	void profileChanged(IConnectionProfile profile);
}
