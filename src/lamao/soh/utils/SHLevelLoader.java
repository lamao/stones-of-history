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

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import lamao.soh.SHConstants;
import lamao.soh.core.SHBall;
import lamao.soh.core.SHBrick;
import lamao.soh.core.SHGamePack;
import lamao.soh.core.SHLevel;
import lamao.soh.core.SHMouseBallLauncher;
import lamao.soh.core.SHPaddle;
import lamao.soh.core.SHPaddleInputHandler;
import lamao.soh.core.SHPaddleSticker;
import lamao.soh.core.SHUtils;
import lamao.soh.core.SHLevel.SHWallType;
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.utils.xmlparser.ISHXmlParser;
import lamao.soh.utils.xmlparser.SHDocXMLParser;

import com.jme.input.InputHandler;
import com.jme.scene.Node;
import com.jme.scene.Spatial;

/**
 * Builds level from models and metadata (bonuses associations, parameters 
 * of brick etc).
 * @author lamao
 *
 */
public class SHLevelLoader implements SHConstants
{
	private Map<String, org.w3c.dom.Element> _bricks = new HashMap<String, Element>();
	
	/**
	 * Creates level and fills new content based on <code>models</code> and
	 * set bonuses and parameters of brick based on <code>metadata</code>
	 * and sets paddle and ball from resource manager.
	 * @param theme - shared resources for current epoch (models, sounds)
	 * @param models - models for level (bricks, decorations)
	 * @param metadata - bonuses, bricks parameters.
	 */
	public SHLevel load(SHResourceManager theme, Node models, File metadata)
	{
		SHLevel level = new SHLevel();
		
		loadMetadata(metadata);
		loadModels(level, models);
		setupSharedEntities(level, theme);
		
		level.getRootNode().updateRenderState();
		
		
		return level;
	}
	
	/** Adds ball and paddle */
	private void setupSharedEntities(SHLevel level, SHResourceManager theme)
	{
		SHBall ball = new SHBall();
		Spatial model = (Spatial)theme.get(SHResourceManager.TYPE_MODEL, BALL);
		model = SHUtils.createSharedModel("ball" + ball, model);
		ball.setModel(model);
		
		SHPaddle paddle = new SHPaddle(model);
		model = (Spatial)theme.get(SHResourceManager.TYPE_MODEL, PADDLE);
		paddle.setModel(model);
		level.setPaddle(paddle);
		
		paddle.setLocation(0, -7, 0);
		
		ball.setLocation(-0, -6.3f, 0);
		ball.setVelocity(-3 ,3 ,0);
		ball.getModel().addController(new SHPaddleSticker(ball, paddle.getModel()));
		level.addBall(ball);
		
		InputHandler input = new SHPaddleInputHandler(level.getPaddle());
		level.setInputHandler(input);
		level.getInputHandler().addAction(new SHMouseBallLauncher(level));
	}
	
	/**
	 * Loads models from given node into given level.
	 * @param level
	 * @param models
	 */
	public void loadModels(SHLevel level, Node models)
	{
		Spatial spatial = null;
		while (models.getChildren().size() > 0)
		{
			spatial = models.getChild(0);
			if (spatial.getName().startsWith(BRICK))
			{
				SHBrick brick = new SHBrick(spatial);
				setUpBrick(level, brick);
				level.addBrick(brick);
			}
			else if (spatial.getName().startsWith(LEFT_WALL))
			{
				level.setWall(spatial, SHWallType.LEFT);
			}
			else if (spatial.getName().startsWith(RIGHT_WALL))
			{
				level.setWall(spatial, SHWallType.RIGHT);
			}
			else if (spatial.getName().startsWith(TOP_WALL))
			{
				level.setWall(spatial, SHWallType.TOP);
			}
			else if (spatial.getName().startsWith(BOTTOM_WALL))
			{
				level.setWall(spatial, SHWallType.BOTTOM);
			}
			else if (spatial.getName().startsWith(DECORATION))
			{
				level.getRootNode().attachChild(spatial);
			}
			else
			{
				models.detachChild(spatial);
			}
		}
	}
	
	private void setUpBrick(SHLevel level, SHBrick brick)
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
			
			value = metadata.getAttribute("bonus");
			if (value.length() > 0)
			{
				SHBonus bonus = createBonus(value);
				level.getBonuses().put(brick, bonus);
			}
		}
	}
	
	/**
	 * Creates bonus from its name like 'inc-paddle-width' = 
	 * SHIncPaddleWidthBonus
	 * @param name
	 * @return
	 */
	private SHBonus createBonus(String name)
	{
		SHBonus bonus = null;
		try
		{
			String className = SHUtils.getClassName("lamao.soh.core.bonuses.SH", 
					name, "Bonus");
			Class<?> klass = Class.forName(className);
			bonus = (SHBonus)klass.newInstance();
			Spatial model = (Spatial)SHGamePack.manager
					.get(SHResourceManager.TYPE_MODEL, name);
			bonus.setModel(SHUtils.createSharedModel(bonus + "bonus", model));
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		
		return bonus;
	}
	
	
	/**
	 * Parses metadata and apply it to level (e.g. setup bonuses or brick
	 * types)
	 * @param level
	 * @param metadata
	 */
	public void loadMetadata(File metadata)
	{
		Document doc = null;
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
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
		
		_bricks.clear();
		SHDocXMLParser parser = new SHDocXMLParser();
		parser.addParser("metadata.bricks", new SHBricksXmlParser());
		
		parser.removeWhitespaces(doc.getDocumentElement());
		parser.parse(doc.getDocumentElement());

	}
	
	public void printDomTree(org.w3c.dom.Node node) 
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
	
	
	
	private class SHBricksXmlParser implements ISHXmlParser
	{
		/* (non-Javadoc)
		 * @see lamao.soh.utils.xmlparser.ISHXmlParser#parse(org.w3c.dom.Node)
		 */
		@Override
		public void parse(org.w3c.dom.Node node)
		{
			NodeList bricks = node.getChildNodes();
			org.w3c.dom.Element brick = null;
			for (int i = 0; i < bricks.getLength(); i++)
			{
				brick = (Element)(bricks.item(i));
				if (brick.getNodeType() != org.w3c.dom.Node.TEXT_NODE)
				{
					_bricks.put(brick.getAttribute("name"), brick);
				}
			}
		}
	}
}
