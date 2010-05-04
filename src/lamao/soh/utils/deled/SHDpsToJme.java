/* 
 * SHSceneLoader.java 22.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.deled;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.jme.app.AbstractGame.ConfigShowMode;
import com.jme.scene.Spatial;
import com.jme.scene.state.CullState;
import com.jme.scene.state.CullState.Face;

import lamao.soh.utils.xmlparser.SHDocXMLParser;


/**
 * Class for loading scene from DeleD *.dps files.
 * @author lamao
 *
 */
public class SHDpsToJme extends SHDpsLoader
{
	/** Root node for scene */
	private com.jme.scene.Node scene = new com.jme.scene.Node("scene-root");
	
	public Spatial getResult()
	{
		if (scene.getQuantity() == 0)
		{
			return null;
		}
		else if (scene.getQuantity() == 1)
		{
			return scene.getChild(0);
		}
		else
		{
			return scene;
		}
	}
	
	/**
	 * Resets all internal variables to default
	 */
	protected void resetLoader()
	{
		super.resetLoader();
		scene = new com.jme.scene.Node("scene-root");
	}

	/**
	 * Builds XML parser for scene.
	 * @return
	 */
	protected SHDocXMLParser buildSceneParser()
	{
		try
		{
			URL textureLocation = new URL("jar:file:" + 
					getFileName().replace("\\", "/") + "!/Textures/");
			
			SHDocXMLParser parser = new SHDocXMLParser();
			parser.addParser("scene.materials.category.material", 
					new SHMaterialParser(getMaterials(), textureLocation));
			parser.addParser("scene.primitives.primitive", 
					new SHPrimitiveNodeParser(getMaterials(), scene));
			return parser;
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args)
	{
		com.jme.app.SimpleGame game = new com.jme.app.SimpleGame()
		{
			@Override
			protected void simpleInitGame()
			{
//				rootNode.attachChild(SHModelLoader.load(new File("data/epochs/test_epoch/level1.obj")));
				SHDpsToJme loader = new SHDpsToJme();
				loader.load(new File(
//						"data/test/test-level.dps"));
						"data/epochs/test_epoch/level1.dps"));
				rootNode.attachChild(loader.getResult());
				
				
				CullState cs = display.getRenderer().createCullState();
				cs.setCullFace(Face.Back);
				rootNode.setRenderState(cs);
				rootNode.updateRenderState();
				
			}
		};
		game.setConfigShowMode(ConfigShowMode.AlwaysShow);
		game.start();
	}
	
}
