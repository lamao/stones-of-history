/* 
 * IXMLParser.java 9.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.xmlparser;

/**
 * Common interface for XML parsers.
 * @author lamao
 *
 */
public interface ISHXmlParser
{
	/**
	 * Parses given XML node.
	 * @param node - XML node
	 */
	public void parse(org.w3c.dom.Node node);

}
