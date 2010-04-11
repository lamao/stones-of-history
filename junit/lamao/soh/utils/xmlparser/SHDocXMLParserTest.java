/* 
 * SHDocXMLParserTest.java 9.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.xmlparser;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import static org.junit.Assert.*;

/**
 * @author lamao
 *
 */
public class SHDocXMLParserTest
{
	
	public class SHDummyParser implements ISHXmlParser
	{
		public int numChildren = 0;
		
		@Override
		public void parse(Node node)
		{
			for (int i = 0; i < node.getChildNodes().getLength(); i++)
			{
				if (node.getChildNodes().item(i).getNodeType() != Node.TEXT_NODE)
				{
					numChildren++;
				}
			}
		}
	}
	
	public Node getTestXML() throws SAXException, IOException, 
			ParserConfigurationException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setIgnoringElementContentWhitespace(true);
		Document doc = factory.newDocumentBuilder().parse(
				SHDocXMLParser.class.getResourceAsStream("/data/metadata/test.xml"));
		return doc.getDocumentElement();
	}

	@Test
	public void testParser() throws SAXException, IOException, 
			ParserConfigurationException
	{
		SHDocXMLParser docParser = new SHDocXMLParser();
		
		assertEquals(0, docParser.getParsers().size());
		
		SHDummyParser parser1 = new SHDummyParser();
		SHDummyParser parser2 = new SHDummyParser();
		docParser.addParser("a.a1.a2", parser1);
		docParser.addParser("a.b1", parser2);
		
		assertEquals(2, docParser.getParsers().size());
		docParser.parse(getTestXML());
		
		assertEquals(1, parser1.numChildren);
		assertEquals(2, parser2.numChildren);
		
		SHDummyParser rootParser = new SHDummyParser();
		docParser.addParser("a", rootParser);
		docParser.parse(getTestXML());
		
		assertEquals(1, parser1.numChildren);
		assertEquals(2, parser2.numChildren);
		assertEquals(2, rootParser.numChildren);
		
	}
}
