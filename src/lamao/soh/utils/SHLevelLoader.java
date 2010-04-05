/* 
 * SHLevelLoader.java 5.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import lamao.soh.core.SHBrick;
import lamao.soh.core.SHLevel;
import lamao.soh.core.SHLevel.SHWallType;
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.core.bonuses.SHIncPaddleWidthBonus;

import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.shape.Box;

/**
 * Builds level from models and metadata (bonuses associations, parameters 
 * of brick etc).
 * @author lamao
 *
 */
public class SHLevelLoader
{
	public final static String NAME_BRICK = "brick";
	public final static String NAME_LEFT_WALL = "left_wall";
	public final static String NAME_RIGHT_WALL = "right_wall";
	public final static String NAME_TOP_WALL = "top_wall";
	public final static String NAME_BOTTOM_WALL = "bottom_wall";
	public final static String NAME_DECORATION = "decoration";
	
	
	private static Map<String, org.w3c.dom.Element> _bricks = new HashMap<String, Element>();
	private static Map<String, org.w3c.dom.Element> _bonuses = new HashMap<String, Element>();
	
	
	/**
	 * Creates level and fills new content based on <code>models</code> and
	 * set bonuses and parameters of brick based on <code>metadata</code>
	 * and sets paddle and ball from resource manager.
	 * @param models - models for level (bricks, decorations)
	 * @param metadata - bonuses, bricks parameters.
	 */
	public static SHLevel load(Node models, File metadata)
	{
		SHLevel level = new SHLevel();
		
		loadMetadata(metadata);
		loadModels(level, models);
		
		return level;
	}
	
	/**
	 * Loads models from given node into given level.
	 * @param level
	 * @param models
	 */
	public static void loadModels(SHLevel level, Node models)
	{
		Spatial spatial = null;
		while (models.getChildren().size() > 0)
		{
			spatial = models.getChild(0);
			if (spatial.getName().startsWith(NAME_BRICK))
			{
				SHBrick brick = new SHBrick(spatial);
				setUpBrick(brick);
				checkBonus(level, brick);
				level.addBrick(brick);
			}
			else if (spatial.getName().startsWith(NAME_LEFT_WALL))
			{
				level.setWall(spatial, SHWallType.LEFT);
			}
			else if (spatial.getName().startsWith(NAME_RIGHT_WALL))
			{
				level.setWall(spatial, SHWallType.RIGHT);
			}
			else if (spatial.getName().startsWith(NAME_TOP_WALL))
			{
				level.setWall(spatial, SHWallType.TOP);
			}
			else if (spatial.getName().startsWith(NAME_BOTTOM_WALL))
			{
				level.setWall(spatial, SHWallType.BOTTOM);
			}
			else if (spatial.getName().startsWith(NAME_DECORATION))
			{
				level.getRootNode().attachChild(spatial);
			}
			else
			{
				models.detachChild(spatial);
			}
		}
	}
	
	private static void setUpBrick(SHBrick brick)
	{
		String value = null;
		Element metadata = _bricks.get(brick.getModel().getName());
		if (metadata != null)
		{
			value = metadata.getAttribute("super");
			if (value.length() > 0 && Boolean.parseBoolean(value))
			{
				brick.setStrength(Integer.MAX_VALUE);
			}
			value = metadata.getAttribute("glass");
			if (value.length() > 0)
			{
				brick.setGlass(Boolean.parseBoolean(value));
			}
			value = metadata.getAttribute("strength");
			if (value.length() > 0)
			{
				brick.setStrength(Integer.parseInt(value));
			}
		}
	}
	
	private static void checkBonus(SHLevel level, SHBrick brick)
	{
		String value = null;
		Element metadata = _bonuses.get(brick.getModel().getName());
		if (metadata != null)
		{
			value = metadata.getAttribute("type");
			
			// TODO: Implement this
			SHBonus bonus = new SHIncPaddleWidthBonus(new Box("bonus box", 
					new Vector3f(0, 0, 0), 0.25f, 0.25f, 0.25f));
			level.getBonuses().put(brick, bonus);
		}
	}
	
	/**
	 * Parses metadata and apply it to level (e.g. setup bonuses or brick
	 * types)
	 * @param level
	 * @param metadata
	 */
	public static void loadMetadata(File metadata)
	{
		Document doc = null;
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(true);
			factory.setIgnoringElementContentWhitespace(true);
			factory.setNamespaceAware(true);
			final String JAXP_SCHEMA_LANGUAGE =
	             "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	        final String W3C_XML_SCHEMA =
	             "http://www.w3.org/2001/XMLSchema"; 
	        factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);

			doc = factory.newDocumentBuilder().parse(metadata);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
		
		removeWhitespaces(doc.getDocumentElement());
		
		_bonuses.clear();
		_bricks.clear();
		org.w3c.dom.Node root = doc.getDocumentElement();
		NodeList items = root.getChildNodes();
		for (int i = 0; i < items.getLength(); i++)
		{
			org.w3c.dom.Node child = items.item(i);
			if (child.getNodeName().equals("bonuses") && child.hasChildNodes())
			{
				parseBonuses(child.getChildNodes());
			}
			else if (child.getNodeName().equals("bricks") && child.hasChildNodes())
			{	
				parseBricks(child.getChildNodes());
				
			}
		}

	}
	
	private static void parseBonuses(NodeList bonuses)
	{
		org.w3c.dom.Element bonus = null;
		for (int i = 0; i < bonuses.getLength(); i++)
		{
			bonus = (Element)(bonuses.item(i));
			_bonuses.put(bonus.getAttribute("brick"), bonus);
		}
	}
	
	private static void parseBricks(NodeList bricks)
	{
		org.w3c.dom.Element brick = null;
		for (int i = 0; i < bricks.getLength(); i++)
		{
			brick = (Element)(bricks.item(i));
			_bricks.put(brick.getAttribute("name"), brick);
		}
	}
	
	public static void printDomTree(org.w3c.dom.Node node) 
	{
	  int type = node.getNodeType();
	  switch (type)
	  {
	    // print the document element
	    case org.w3c.dom.Node.DOCUMENT_NODE: 
	    {
	      System.out.println("<?xml version=\"1.0\" ?>");
	       printDomTree (((Document)node).getDocumentElement());
	      break;
	    }
	    case org.w3c.dom.Node.ELEMENT_NODE: 
	    {
	      System.out.print("<");
	      System.out.print(node.getNodeName());

	      NamedNodeMap attrs = node.getAttributes();
	      for (int i = 0; i < attrs.getLength(); i++)
	         printDomTree (attrs.item(i));

	      System.out.print(">");

	      if (node.hasChildNodes())
	      {
	        NodeList children = node.getChildNodes();
	        for (int i = 0; i < children.getLength(); i++)
	           printDomTree (children.item(i));
	      }

	      System.out.print("</");
	      System.out.print(node.getNodeName());
	      System.out.print('>');

	      break;
	    }
	    case org.w3c.dom.Node.ATTRIBUTE_NODE:
	    {
	      System.out.print(" " + node.getNodeName() + "=\"" +
	                       ((Attr)node).getValue() + "\"");
	      break;
	    }

	    case org.w3c.dom.Node.TEXT_NODE: 
	    {
	      System.out.print(node.getNodeValue());
	      break;
	    }
	  }
	}
	
	/**
	 * Removes all unused characters (spaces, empty lines) from XML node.
	 * @param node
	 */
	private static void removeWhitespaces(org.w3c.dom.Node node)
	{
		NodeList children = node.getChildNodes();
		if (children.getLength() > 0)
		{
			int count = 0;
			for (int i = children.getLength() - 1; i >= 0; i--)
			{
				org.w3c.dom.Node child = children.item(i);
				if (child instanceof Text && ((Text) child).getData().trim().length() == 0)
				{
					node.removeChild(child);
					count++;
				}
				else if (child instanceof Element)
				{
					removeWhitespaces((Element) child);
				}
			}
		}
	} 
}
