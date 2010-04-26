/* 
 * SHSceneLoader.java 22.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.deled;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
public class SHDpsToJme
{
	
	
	private static Logger logger = Logger.getLogger(SHDpsToJme.class.getName());
	
	/** Root node for scene */
	private com.jme.scene.Node scene = new com.jme.scene.Node("scene-root");

	/** Storage for materials, loaded from file */
	private Map<String, SHMaterialGroup> _materials = 
		new HashMap<String, SHMaterialGroup>();
	
	/** Name of file from which scene is loaded. Used for accessing to 
	 * textures
	 */
	private String _fileName = null;
	
	
	/**
	 * Loads scene from *.dps file
	 * @param file
	 * @return Loaded scene. Null if loading failed.
	 */
	public Spatial load(File file)
	{
		try
		{
			_fileName = file.getAbsolutePath();
			ZipFile zip = new ZipFile(file);
			ZipEntry entry = zip.getEntry("Map Files/scene.dxs");
			
			return loadScene(zip.getInputStream(entry));
		}
		catch (IOException e) 
		{
			logger.severe("Can't open file: " + file.getName());
			return null;
		}
	}
	
	/**
	 * Load scene from input stream (unzipped *.dxs file)
	 * @param is
	 * @return Loaded scene. Null if loading failed.
	 */
	private Spatial loadScene(InputStream is)
	{
		resetLoader();
		SHDocXMLParser parser = buildSceneParser();
		
		Node docRoot = getDocumentRoot(is);
		parser.removeWhitespaces(docRoot);
		parser.parse(docRoot);
		
		if (scene.getChildren().size() == 1)
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
	private void resetLoader()
	{
		scene = new com.jme.scene.Node("scene-root");
		_materials.clear();
	}

	/**
	 * Builds XML parser for scene.
	 * @return
	 */
	private SHDocXMLParser buildSceneParser()
	{
		try
		{
			URL textureLocation = new URL("jar:file:" + 
					_fileName.replace("\\", "/") + "!/Textures/");
			
			SHDocXMLParser parser = new SHDocXMLParser();
			parser.addParser("scene.materials.category.material", 
					new SHMaterialParser(_materials, textureLocation));
			parser.addParser("scene.primitives.primitive", 
					new SHPrimitiveNodeParser(_materials, scene));
			return parser;
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Parses XML and returns root element of the document.
	 * @param is
	 * @return
	 */
	private org.w3c.dom.Node getDocumentRoot(InputStream is)
	{
		org.w3c.dom.Node result = null;
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(false);
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);
			Document doc = factory.newDocumentBuilder().parse(is);
			result = doc.getDocumentElement();
		}
		catch (SAXException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
		}
		return result;
		
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
				rootNode.attachChild(loader.load(new File(
//						"data/test/test-level.dps")));
						"data/epochs/test_epoch/level1.dps")));
				
				
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
