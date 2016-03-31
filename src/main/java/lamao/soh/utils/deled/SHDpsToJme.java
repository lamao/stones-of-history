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

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial;

import lamao.soh.utils.xmlparser.SHDocXMLParser;


/**
 * Class for loading scene from DeleD *.dps files.
 * @author lamao
 *
 */
public class SHDpsToJme extends SHDpsLoader
{
	/** Root node for scene */
	private com.jme3.scene.Node scene = new com.jme3.scene.Node("scene-root");
	
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
		scene = new com.jme3.scene.Node("scene-root");
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
		SimpleApplication game = new SimpleApplication()
		{
			@Override
            public void simpleInitApp() {
//				rootNode.attachChild(SHModelLoader.load(new File("data/epochs/test_epoch/level1.obj")));
				SHDpsToJme loader = new SHDpsToJme();
				loader.load(new File(
//						"data/test/test-level.dps"));
						"data/epochs/test_epoch/level1.dps"));
				rootNode.attachChild(loader.getResult());

                PointLight light = new PointLight();
                light.setColor(ColorRGBA.White.clone());
			}
        };
		game.start();
	}
	
}
