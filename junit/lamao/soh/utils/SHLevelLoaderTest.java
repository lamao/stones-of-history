/* 
 * SHLevelLoaderTest.java 5.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils;

import java.io.File;

import lamao.soh.core.SHLevel;
import lamao.soh.core.SHLevel.SHWallType;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.shape.Box;

/**
 * @author lamao
 *
 */
public class SHLevelLoaderTest
{
	
	private Node models = new Node();
	private String metadata = "/data/metadata/metadata.xml";
	
	@Before
	public void setUp()
	{
		models.attachChild(new Box("brick1", Vector3f.ZERO.clone(), 2, 1, 1));
		models.attachChild(new Box("brick2", Vector3f.UNIT_Y.clone(), 2, 1, 1));
		models.attachChild(new Box("brick3", Vector3f.UNIT_Y.clone(), 2, 1, 1));
		
		models.attachChild(new Box("left_wall", new Vector3f(-10, 0, 0), 2, 1, 1));
		models.attachChild(new Box("right_wall", new Vector3f(10, 0, 0), 2, 1, 1));
		models.attachChild(new Box("bottom_wall", new Vector3f(0, 10, 0), 2, 1, 1));
		models.attachChild(new Box("top_wall", new Vector3f(0, -10, 0), 2, 1, 1));
		
		models.attachChild(new Box("decoration1", new Vector3f(0, -10, 0), 2, 1, 1));
		models.attachChild(new Box("decoration2", new Vector3f(0, -10, 0), 2, 1, 1));
		
		models.attachChild(new Box("abs", new Vector3f(0, -10, 0), 2, 1, 1));
		
	}
	

	@Test
	public void testLoadingModels()
	{
		SHLevel level = new SHLevel();
		SHLevelLoader.loadModels(level, models);
		
		assertNotNull(level);
		assertEquals(3, level.getBricks().size());
		assertNotNull(level.getWall(SHWallType.LEFT));
		assertEquals("left_wall", level.getWall(SHWallType.LEFT).getName());
		assertNotNull(level.getWall(SHWallType.RIGHT));
		assertEquals("right_wall", level.getWall(SHWallType.RIGHT).getName());
		assertNotNull(level.getWall(SHWallType.TOP));
		assertEquals("top_wall", level.getWall(SHWallType.TOP).getName());
		assertNotNull(level.getWall(SHWallType.BOTTOM));
		assertEquals("bottom_wall", level.getWall(SHWallType.BOTTOM).getName());
		
		assertEquals(8, level.getRootNode().getChildren().size());
	}
	
	@Test
	public void testLoadingMetadata()
	{
		File file = new File(SHLevelLoaderTest.class.getResource(metadata).getPath());
		SHLevel level = SHLevelLoader.load(models, file);
		
		assertNotNull(level);
		assertEquals(2, level.getBonuses().size());
		assertEquals(Integer.MAX_VALUE, level.getBricks().get(0).getStrength());
		assertTrue(level.getBricks().get(0).isGlass());
		assertEquals(1, level.getBricks().get(1).getStrength());
		assertFalse(level.getBricks().get(1).isGlass());
		assertEquals(3, level.getBricks().get(2).getStrength());
		assertFalse(level.getBricks().get(2).isGlass());
		
	}
}

