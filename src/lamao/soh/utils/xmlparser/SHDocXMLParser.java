/* 
 * DocXMLParser.java 9.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.xmlparser;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * Parses part of XML document. Uses preset <code>IXMLParser</code> for 
 * document subtrees (e.g. separate parser for <code>metadata.bricks</code>
 * subtree. 
 * @author lamao
 *
 */
public class SHDocXMLParser implements ISHXmlParser
{
	/** Parsers for subtrees of document */
	private Map<String, ISHXmlParser> _parsers = new HashMap<String, ISHXmlParser>();
	
	/* (non-Javadoc)
	 * @see lamao.soh.utils.xmlparser.IXMLParser#parser(org.w3c.dom.Node)
	 */
	@Override
	public void parse(Node node)
	{
		if (_parsers.get(node.getNodeName()) == null)
		{
			parse(node.getNodeName(), node);
		}
		else
		{
			_parsers.get(node.getNodeName()).parse(node);
		}
	}
	
	/** 
	 * Parses given node that has given path in whole document.
	 * @param subtree - path in document to this node. Used for choosing
	 * appropriate subtree parser.
	 * @param node
	 */
	private void parse(String subtree, Node node)
	{
		NodeList children = node.getChildNodes();
		
		Node current = null;
		ISHXmlParser parser = null;
		String newSubtree = null;
		for (int i = 0; i < children.getLength(); i++)
		{
			current = children.item(i);
			if (current.getNodeType() != Node.TEXT_NODE)
			{
				newSubtree = subtree + "." + current.getNodeName();
				parser = _parsers.get(newSubtree);
				if (parser != null)
				{
					parser.parse(current);
				}
				else
				{
					parse(subtree + "." + current.getNodeName(), current);
				}
			}
		}
	}

	public void addParser(String subtree, ISHXmlParser parser)
	{
		_parsers.put(subtree, parser);
	}
	
	public ISHXmlParser getParser(String subtree)
	{
		return _parsers.get(subtree);
	}
	
	public Map<String, ISHXmlParser> getParsers()
	{
		return _parsers;
	}

	public void setParsers(Map<String, ISHXmlParser> parsers)
	{
		_parsers = parsers;
	}
	
	/**
	 * Removes all unused characters (spaces, empty lines) from XML node.
	 * @param node
	 */
	public void removeWhitespaces(org.w3c.dom.Node node)
	{
		NodeList children = node.getChildNodes();
		if (children.getLength() > 0)
		{
			for (int i = children.getLength() - 1; i >= 0; i--)
			{
				org.w3c.dom.Node child = children.item(i);
				if (child instanceof Text && ((Text) child).getData().trim().length() == 0)
				{
					node.removeChild(child);
				}
				else if (child instanceof Element)
				{
					removeWhitespaces((Element) child);
				}
			}
		}
	} 
}
