/* 
 * SHSceneLoaderTest.java 03.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.deled;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import lamao.soh.core.SHGamePack;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;
import lamao.soh.core.entities.SHBrick;
import lamao.soh.utils.SHResourceManager;

import org.junit.Test;
import static org.junit.Assert.*;

import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.system.DisplaySystem;
import com.jme.system.dummy.DummySystemProvider;

/**
 * @author lamao
 *
 */
public class SHSceneLoaderTest
{

	static
	{
		Logger.getLogger("").setLevel(Level.OFF);
		DisplaySystem.setSystemProvider(new DummySystemProvider());
	}
	
	@Test
	public void testLoading()
	{
		SHGamePack.manager = new SHResourceManager();
		SHGamePack.manager.add(SHResourceManager.TYPE_MODEL, "double-ball", 
				new Node("ball"));
		SHScene scene = new SHScene();
		SHSceneLoader loader = new SHSceneLoader(scene);
		
		loader.load(new File("data/test/test-level.dps"));
		
		assertNull(scene.getEntities("decoration"));
		assertEquals(2, scene.getModels("decoration").size());
		assertEquals(3, scene.getEntities("wall").size());
		assertEquals(3, scene.getModels("wall").size());
		assertEquals(1, scene.getEntities("bottom-wall").size());
		assertEquals(15, scene.getEntities("brick").size());
		
		SHBrick brick = (SHBrick)scene.getEntity("brick", "brick-tank");
		assertEquals(Integer.MAX_VALUE, brick.getStrength());
		
		brick = (SHBrick)scene.getEntity("brick", "brick4");
		assertTrue(brick.isGlass());
		
		brick = (SHBrick)scene.getEntity("brick", "brick1");
		assertEquals(1, brick.getStrength());
	
		Map<String, List<Spatial>> models = scene.getModels();
		for (String key : models.keySet())
		{
			for (Spatial model : models.get(key))
			{
				assertFalse(model.getName() + " " + model.getLocalTranslation(), 
						SHUtils.areEqual(model.getLocalTranslation(), 
								new Vector3f(0, 0, 0), 0.001f));
			}
		}
	}
		
	
}
