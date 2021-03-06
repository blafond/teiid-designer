/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.modelgenerator.xml.modelextension;

import org.teiid.designer.metamodels.relational.RelationalEntity;

/**
 * 
 * Adds namespace metadata extensions to the Table class.
 *
 *
 * @since 8.0
 */
public interface BaseXMLRelationalExtensionManager extends ExtensionManager {

	public void setNamespacePrefixesAttribute(RelationalEntity table, String prefixes);

}
