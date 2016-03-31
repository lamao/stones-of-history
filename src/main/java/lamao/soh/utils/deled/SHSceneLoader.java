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

import lamao.soh.core.ISHEntityFactory;
import lamao.soh.core.SHScene;
import lamao.soh.utils.xmlparser.SHDocXMLParser;

/**
 * @author lamao
 *
 */
public class SHSceneLoader extends SHDpsLoader
{
	/** Scene where load data from file */
	private SHScene scene = null;
	
	private ISHEntityFactory entityFactory;

	public SHSceneLoader(SHScene scene, ISHEntityFactory entityFactory)
	{
		this.scene = scene;
		this.entityFactory = entityFactory;
	}
	
	/**
	 * Resets all internal variables to default
	 */
	protected void resetLoader()
	{
		super.resetLoader();
		scene.reset();
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
					new SHPrimitiveEntityParser(getMaterials(), scene, 
					entityFactory));
			return parser;
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public SHScene getScene()
	{
		return scene;
	}

	public void setScene(SHScene scene)
	{
		this.scene = scene;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void load(File file)
	{
		scene.resetAll();
		super.load(file);
	}

}
