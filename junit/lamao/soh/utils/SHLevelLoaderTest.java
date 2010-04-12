/* 
 * SHLevelLoaderTest.java 5.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import lamao.soh.SHConstants;
import lamao.soh.core.SHLevel;
import lamao.soh.core.SHPaddleSticker;
import lamao.soh.core.SHLevel.SHWallType;
import lamao.soh.core.bonuses.SHDoubleBallBonus;
import lamao.soh.core.bonuses.SHIncPaddleWidthBonus;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.jme.input.MouseInput;
import com.jme.input.dummy.DummyMouseInput;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;
import com.jme.system.DisplaySystem;
import com.jme.system.dummy.DummySystemProvider;

/**
 * @author lamao
 *
 */
public class SHLevelLoaderTest implements SHConstants
{
	
	private Node models = new Node();
	private String metadata = "/data/metadata/metadata.xml";
	
	static 
	{
		DisplaySystem.setSystemProvider(new DummySystemProvider());
		MouseInput.setProvider(DummyMouseInput.class);
		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.OFF);
	}
	
	@Before
	public void setUp()
	{
		models.attachChild(new Box(BRICK + "1", Vector3f.ZERO.clone(), 2, 1, 1));
		models.attachChild(new Box(BRICK + "2", Vector3f.UNIT_Y.clone(), 2, 1, 1));
		models.attachChild(new Box(BRICK + "3", Vector3f.UNIT_Y.clone(), 2, 1, 1));
		
		models.attachChild(new Box(LEFT_WALL, new Vector3f(-10, 0, 0), 2, 1, 1));
		models.attachChild(new Box(RIGHT_WALL, new Vector3f(10, 0, 0), 2, 1, 1));
		models.attachChild(new Box(TOP_WALL, new Vector3f(0, 10, 0), 2, 1, 1));
		models.attachChild(new Box(BOTTOM_WALL, new Vector3f(0, -10, 0), 2, 1, 1));
		
		models.attachChild(new Box(DECORATION + "1", new Vector3f(0, -10, 0), 2, 1, 1));
		models.attachChild(new Box(DECORATION+ "2", new Vector3f(0, -10, 0), 2, 1, 1));
		
		models.attachChild(new Box("abs", new Vector3f(0, -10, 0), 2, 1, 1));
		
	}
	

	@Test
	public void testLoadingModels()
	{
		SHLevel level = new SHLevel();
		new SHLevelLoader().loadModels(level, models);
		
		assertNotNull(level);
		assertEquals(3, level.getBricks().size());
		assertNotNull(level.getWall(SHWallType.LEFT));
		assertEquals(LEFT_WALL, level.getWall(SHWallType.LEFT).getName());
		assertNotNull(level.getWall(SHWallType.RIGHT));
		assertEquals(RIGHT_WALL, level.getWall(SHWallType.RIGHT).getName());
		assertNotNull(level.getWall(SHWallType.TOP));
		assertEquals(TOP_WALL, level.getWall(SHWallType.TOP).getName());
		assertNotNull(level.getWall(SHWallType.BOTTOM));
		assertEquals(BOTTOM_WALL, level.getWall(SHWallType.BOTTOM).getName());
		
		assertEquals(9, level.getRootNode().getChildren().size());
	}
	
	@Test
	public void testLoadingAll()
	{
		SHResourceManager manager = SHResourceManager.getInstance();
		manager.add(SHResourceManager.TYPE_MODEL, BALL, new Sphere(BALL, 
				15, 15, 1));
		manager.add(SHResourceManager.TYPE_MODEL, PADDLE, new Box(BALL, 
				Vector3f.ZERO.clone(), 1, 1, 1));
		manager.add(SHResourceManager.TYPE_MODEL, "inc-paddle-width", 
				new Box("inc-paddle-width", Vector3f.ZERO.clone(), 1, 1, 1));
		manager.add(SHResourceManager.TYPE_MODEL, "double-ball", 
				new Box("double-ball", Vector3f.ZERO.clone(), 1, 1, 1));
		
		
		File file = new File(SHLevelLoaderTest.class.getResource(metadata).getPath());
		SHLevel level = new SHLevelLoader().load(manager, models, file);
		
		assertNotNull(level);
		assertEquals(2, level.getBonuses().size());
		assertNotNull(level.getPaddle());
		assertEquals(1, level.getBalls().size());
		
		assertNotNull(level.getInputHandler());
		assertTrue(level.getBalls().get(0).getModel().getControllers().get(0) 
				instanceof SHPaddleSticker);
		
		assertEquals(Integer.MAX_VALUE, level.getBricks().get(0).getStrength());
		assertTrue(level.getBricks().get(0).isGlass());
		assertEquals(1, level.getBricks().get(1).getStrength());
		assertFalse(level.getBricks().get(1).isGlass());
		assertEquals(3, level.getBricks().get(2).getStrength());
		assertFalse(level.getBricks().get(2).isGlass());
		
		assertEquals(SHIncPaddleWidthBonus.class, 
				level.getBonuses().get(level.getBricks().get(1)).getClass());
		assertEquals(SHDoubleBallBonus.class, 
				level.getBonuses().get(level.getBricks().get(2)).getClass());
		
	}
}

