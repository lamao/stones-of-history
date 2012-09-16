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
import lamao.soh.ngutils.AbstractJmeTest;
import lamao.soh.utils.SHResourceManager;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;

/**
 * @author lamao
 *
 */
public class SHSceneLoaderTest extends AbstractJmeTest
{
	
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
		assertEquals(2, scene.get("decoration").size());
		assertEquals(3, scene.getEntities("wall").size());
		assertEquals(3, scene.get("wall").size());
		assertEquals(1, scene.getEntities("bottom-wall").size());
		assertEquals(15, scene.getEntities("brick").size());
		
		SHBrick brick = (SHBrick)scene.getEntity("brick", "brick-tank");
		assertEquals(Integer.MAX_VALUE, brick.getStrength());
		
		brick = (SHBrick)scene.getEntity("brick", "brick4");
		assertTrue(brick.isGlass());
		
		brick = (SHBrick)scene.getEntity("brick", "brick1");
		assertEquals(1, brick.getStrength());
	
		Map<String, List<Spatial>> models = scene.getAll();
		for (String key : models.keySet())
		{
			for (Spatial model : models.get(key))
			{
				assertFalse( 
						SHUtils.areEqual(model.getLocalTranslation(), 
								new Vector3f(0, 0, 0), 0.001f), 
						model.getName() + " " + model.getLocalTranslation());
			}
		}
	}
		
	
}
