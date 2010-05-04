/* 
 * SHSceneLoader.java 03.05.2010
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

import com.jme.app.SimpleGame;
import com.jme.app.AbstractGame.ConfigShowMode;
import com.jme.bounding.BoundingBox;
import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Spatial;
import com.jme.scene.shape.Box;

import lamao.soh.core.SHBall;
import lamao.soh.core.SHBreakoutEntityFactory;
import lamao.soh.core.SHEntity;
import lamao.soh.core.SHGamePack;
import lamao.soh.core.SHPaddle;
import lamao.soh.core.SHPaddleSticker;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;
import lamao.soh.utils.SHResourceManager;
import lamao.soh.utils.xmlparser.SHDocXMLParser;

/**
 * @author lamao
 *
 */
public class SHSceneLoader
{
private static Logger logger = Logger.getLogger(SHDpsToJme.class.getName());
	
	/** Scene  */
	private SHScene _scene = null;

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
	public void load(File file, SHScene scene)
	{
		try
		{
			_scene = scene;
			_fileName = file.getAbsolutePath();
			ZipFile zip = new ZipFile(file);
			ZipEntry entry = zip.getEntry("Map Files/scene.dxs");
			
			loadScene(zip.getInputStream(entry));
		}
		catch (IOException e) 
		{
			logger.severe("Can't open file: " + file.getName());
		}
	}
	
	/**
	 * Load scene from input stream (unzipped *.dxs file)
	 * @param is
	 * @return Loaded scene. Null if loading failed.
	 */
	private void loadScene(InputStream is)
	{
		resetLoader();
		SHDocXMLParser parser = buildSceneParser();
		
		Node docRoot = getDocumentRoot(is);
		parser.removeWhitespaces(docRoot);
		parser.parse(docRoot);
		
		setupSharedEntities();
		
	}
	
	/**
	 * Resets all internal variables to default
	 */
	private void resetLoader()
	{
		_scene.reset();
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
					new SHPrimitiveEntityParser(_materials, _scene, 
					new SHBreakoutEntityFactory()));
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
	
	private void setupSharedEntities()
	{
		SHResourceManager theme = SHResourceManager.getInstance();
		SHBall ball = new SHBall();
		ball.setType("ball");
		ball.setName("ball" + ball);
		Spatial model = (Spatial)theme.get(SHResourceManager.TYPE_MODEL, "ball");
		model = SHUtils.createSharedModel("ball" + ball, model);
		ball.setModel(model);
		
		SHPaddle paddle = new SHPaddle(model);
		paddle.setType("paddle");
		paddle.setName("paddle");
		model = (Spatial)theme.get(SHResourceManager.TYPE_MODEL, "paddle");
		paddle.setModel(model);
		paddle.setLocation(0, -7, 0);
		_scene.addEntity(paddle);
		
		
		ball.setLocation(-0, -6.3f, 0);
		ball.setVelocity(-3 ,3 ,0);
		ball.getRoot().addController(new SHPaddleSticker(ball, paddle.getRoot()));
		_scene.addEntity(ball);
	}

	
	public static void main(String[] args)
	{
		SimpleGame game = new SimpleGame() 
		{
			@Override
			protected void simpleInitGame()
			{
				SHResourceManager.getInstance().loadAll(new File(
						"data/epochs/test_epoch/appearence.txt"));
				SHScene scene = new SHScene();
				new SHSceneLoader().load(new File("data/test/test-level.dps"),
						scene);
				
				rootNode.attachChild(scene.getRootNode());
				rootNode.updateRenderState();
				
			}
		};
		game.setConfigShowMode(ConfigShowMode.AlwaysShow);
		game.start();
	}
}
