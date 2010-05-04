/* 
 * SHSceneLoader.java 03.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.deled;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.jme.app.SimpleGame;
import com.jme.app.AbstractGame.ConfigShowMode;

import lamao.soh.core.SHBreakoutEntityFactory;
import lamao.soh.core.SHGamePack;
import lamao.soh.core.SHScene;
import lamao.soh.utils.xmlparser.SHDocXMLParser;

/**
 * @author lamao
 *
 */
public class SHSceneLoader extends SHDpsLoader
{
	/** Scene where load data from file */
	private SHScene _scene = null;

	public SHSceneLoader(SHScene scene)
	{
		_scene = scene;
	}
	
	/**
	 * Resets all internal variables to default
	 */
	protected void resetLoader()
	{
		super.resetLoader();
		_scene.reset();
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
					new SHPrimitiveEntityParser(getMaterials(), _scene, 
					new SHBreakoutEntityFactory()));
			return parser;
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
//	private void setupSharedEntities()
//	{
//		SHResourceManager theme = SHGamePack.manager;
//		SHBall ball = new SHBall();
//		ball.setType("ball");
//		ball.setName("ball" + ball);
//		Spatial model = (Spatial)theme.get(SHResourceManager.TYPE_MODEL, "ball");
//		model = SHUtils.createSharedModel("ball" + ball, model);
//		ball.setModel(model);
//		
//		SHPaddle paddle = new SHPaddle(model);
//		paddle.setType("paddle");
//		paddle.setName("paddle");
//		model = (Spatial)theme.get(SHResourceManager.TYPE_MODEL, "paddle");
//		paddle.setModel(model);
//		paddle.setLocation(0, -7, 0);
//		_scene.addEntity(paddle);
//		
//		
//		ball.setLocation(-0, -6.3f, 0);
//		ball.setVelocity(-3 ,3 ,0);
//		ball.getRoot().addController(new SHPaddleSticker(ball, paddle.getRoot()));
//		_scene.addEntity(ball);
//	}

	
	public static void main(String[] args)
	{
		SimpleGame game = new SimpleGame() 
		{
			@Override
			protected void simpleInitGame()
			{
				SHGamePack.initDefaults();
				SHGamePack.manager.loadAll(new File(
						"data/epochs/test_epoch/appearence.txt"));
				SHScene scene = new SHScene();
				new SHSceneLoader(scene).load(new File("data/test/test-level.dps"));
				
				rootNode.attachChild(scene.getRootNode());
				rootNode.updateRenderState();
				
			}
		};
		game.setConfigShowMode(ConfigShowMode.AlwaysShow);
		game.start();
	}
}
