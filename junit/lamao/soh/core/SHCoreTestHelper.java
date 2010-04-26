/* 
 * SHCoreTestHelper.java 24.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import lamao.soh.core.SHLevel.SHWallType;
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.core.bonuses.SHIncPaddleWidthBonus;

import org.junit.Test;

import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.math.Vector3f;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;

import static org.junit.Assert.*;

/**
 * Factory class for tests in core package. Creates different game entities for
 * testing purposes.
 * @author lamao
 *
 */
public class SHCoreTestHelper
{
	/** Creates default box brick dimension (1, 1, 1) located at (0, 0, 0) */ 
	public static SHBrick createDefaultBrick(String id)
	{
		SHBrick brick = new SHBrick(new Box(id, new Vector3f(0, 0, 0), 
				2, 1, 1));
		brick.getModel().setModelBound(new BoundingBox());
		brick.getModel().updateModelBound();
		return brick;
	}
	
	/** Creates same box as in {@link #createDefaultBrick()} but it is glass */
	public static SHBrick createGlassBrick(String id)
	{
		SHBrick brick = createDefaultBrick(id);
		brick.setGlass(true);
		return brick;
	}
	
	/** 
	 * Creates same box as in {@link #createDefaultBrick()} but it is 
	 * superbrick 
	 */
	public static SHBrick createSuperBrick(String id)
	{
		SHBrick brick = createDefaultBrick(id);
		brick.setStrength(Integer.MAX_VALUE);
		return brick;
	}
	
	/** Creates default ball with radius 1 located at (0, 0, 0)*/
	public static SHBall createDefaultBall(String id)
	{
		Sphere model = new Sphere(id, 15, 15, 1);
		model.setModelBound(new BoundingSphere());
		model.updateModelBound();
		
		return new SHBall(model);
	}
	
	public static SHBall createDefaultBall()
	{
		return createDefaultBall("ball");
	}
	
	/** 
	 * Creates default paddle which have size (4, 2, 2) and located at
	 * (0, 0, 0)
	 */
	public static SHPaddle createDefaultPaddle()
	{
		SHPaddle paddle = new SHPaddle(new Box("paddle", 
				new Vector3f(0, 0, 0), 2, 1, 1));
		paddle.getModel().setModelBound(new BoundingBox());
		paddle.getModel().updateModelBound();
		return paddle;
	}
	
	/**
	 * Creates level with default walls, default ball, default paddle, and 
	 * three bricks: default, super, glass. <br>
	 * Ball location is (0, 2, 0), paddle location is (0, 0, 0),
	 * default brick location (-5, 0, 0), glass brick - (0, 0, 0),
	 * super brick (5, 0, 0) 
	 */
	public static SHLevel createDefaultLevel()
	{
		SHLevel level = new SHLevel();
		
		SHBrick defaultBrick = createDefaultBrick("brick");
		defaultBrick.setLocation(-5, 0, 0);
		level.addBrick(defaultBrick);
		
		SHBrick glassBrick = createGlassBrick("glass");
		glassBrick.setLocation(0, 0, 0);
		level.addBrick(glassBrick);
		
		SHBrick superBrick = createSuperBrick("super");
		superBrick.setLocation(5, 0, 0);
		level.addBrick(superBrick);
		
		SHBall ball = createDefaultBall("ball");
		ball.setLocation(0, -5, 0);
		level.addBall(ball);
		
		SHPaddle paddle = createDefaultPaddle();
		paddle.setLocation(0, -7, 0);
		level.setPaddle(paddle);
		
		createDefaultWalls(level);
		
		level.updateDeletebleBricks();
		return level;
	}
	
	/**
	 *  Creates default level with <code>SHIncPaddleWidthBonus</code> attached
	 * to left (default) brick. 
	 */
	public static SHLevel createLevelWithBonus()
	{
		SHLevel level = createDefaultLevel();
		
		SHBrick brick = level.getBricks().get(0);
		SHBonus bonus = SHCoreTestHelper.createDefaultBonus();
		level.getBonuses().put(brick, bonus);
		
		return level;
	}
	
	/**
	 * Create walls for the given level. Walls are boxes.<br>
	 * @param level
	 */
	private static void createDefaultWalls(SHLevel level)
	{
		Box leftWall = new Box("left wall", new Vector3f(-10, 0, 0), 1, 10, 1);
		leftWall.setModelBound(new BoundingBox());
		leftWall.updateModelBound();
		level.setWall(leftWall, SHWallType.LEFT);
		
		Box rightWall = new Box("right wall", new Vector3f(10, 0, 0), 1, 10, 1);
		rightWall.setModelBound(new BoundingBox());
		rightWall.updateModelBound();
		level.setWall(rightWall, SHWallType.RIGHT);
		
		Box topWall = new Box("top wall", new Vector3f(0, 10, 0), 10, 1, 1);
		topWall.setModelBound(new BoundingBox());
		topWall.updateModelBound();
		level.setWall(topWall, SHWallType.TOP);
		
		Box bottomWall = new Box("bottom wall", new Vector3f(0, -10, 0), 10, 1, 1);
		bottomWall.setModelBound(new BoundingBox());
		bottomWall.updateModelBound();
		level.setWall(bottomWall, SHWallType.BOTTOM);
		level.setBottomWallActive(true);
	}
	
	/**
	 * Creates <code>SHIncPaddleWidthBonus</code> with default duration and
	 * box model (0.5, 0.5, 0.5) dimensions
	 */
	public static SHBonus createDefaultBonus(String name)
	{
		Box box = new Box(name, Vector3f.ZERO.clone(), 0.25f, 0.25f, 0.25f);
		box.setModelBound(new BoundingBox());
		box.updateModelBound();
		SHBonus bonus = new SHIncPaddleWidthBonus(box);
		return bonus;
	}
	
	public static SHBonus createDefaultBonus()
	{
		return createDefaultBonus("bonus model");
	}
	
	
	
	@Test
	public void testDefaultBrick()
	{
		SHBrick brick = createDefaultBrick("brick");
		assertEquals(1, brick.getStrength());
		assertFalse(brick.isGlass());
		assertTrue(brick.getModel() instanceof Box);
		assertTrue(brick.getModel().getWorldBound() instanceof BoundingBox);
		assertTrue(SHUtils.areEqual(Vector3f.ZERO, 
									brick.getModel().getLocalTranslation(), 
									0.001f));
		BoundingBox box = (BoundingBox)brick.getModel().getWorldBound();
		assertTrue(SHUtils.areEqual(new Vector3f(2, 1, 1), 
									box.getExtent(null), 
									0.001f));
	}
	
	@Test
	public void testGlassBrick()
	{
		SHBrick brick = createGlassBrick("glass");
		assertEquals(1, brick.getStrength());
		assertTrue(brick.isGlass());
		assertTrue(brick.getModel() instanceof Box);
		assertTrue(brick.getModel().getWorldBound() instanceof BoundingBox);
		assertTrue(SHUtils.areEqual(Vector3f.ZERO, 
									brick.getModel().getLocalTranslation(), 
									0.001f));
		BoundingBox box = (BoundingBox)brick.getModel().getWorldBound();
		assertTrue(SHUtils.areEqual(new Vector3f(2, 1, 1), 
									box.getExtent(null), 
									0.001f));
	}
	
	@Test
	public void testSuperBrick()
	{
		SHBrick brick = createSuperBrick("super");
		assertEquals(Integer.MAX_VALUE, brick.getStrength());
		assertFalse(brick.isGlass());
		assertTrue(brick.getModel() instanceof Box);
		assertTrue(brick.getModel().getWorldBound() instanceof BoundingBox);
		assertTrue(SHUtils.areEqual(Vector3f.ZERO, 
									brick.getModel().getLocalTranslation(), 
									0.001f));
		BoundingBox box = (BoundingBox)brick.getModel().getWorldBound();
		assertTrue(SHUtils.areEqual(new Vector3f(2, 1, 1), 
									box.getExtent(null), 
									0.001f));
	}
	
	@Test
	public void testDefaultBall()
	{
		SHBall ball = createDefaultBall();
		assertNotNull(ball.getModel());
		assertNotNull(ball.getModel().getWorldBound());
		assertTrue(SHUtils.areEqual(Vector3f.UNIT_Y, ball.getVelocity(), 0.001f));
		assertTrue(SHUtils.areEqual(Vector3f.ZERO, ball.getLocation(), 0.001f));
	}
	
	@Test
	public void testDefaultPaddle()
	{
		SHPaddle paddle = createDefaultPaddle();
		assertTrue(paddle.getModel().getWorldBound() instanceof BoundingBox);
		BoundingBox box = (BoundingBox)paddle.getModel().getWorldBound();
		assertTrue(SHUtils.areEqual(new Vector3f(2, 1, 1), 
									box.getExtent(null), 0.001f));
		assertTrue(SHUtils.areEqual(Vector3f.ZERO, paddle.getModel()
				.getLocalTranslation(), 0.001f));
	}
	
	@Test
	public void testDefaultLevel()
	{
		SHLevel level = createDefaultLevel();
		assertEquals(3, level.getBricks().size());
		assertEquals(1, level.getBalls().size());
		assertNotNull(level.getPaddle());
		assertTrue(level.isBottomWallActive());
		assertEquals(9, level.getRootNode().getChildren().size());
		for (SHWallType type : SHLevel.SHWallType.values())
		{
			assertNotNull(level.getWall(type));
		}
		assertEquals(0, level.getBonuses().size());
	}
	
	@Test
	public void testDefaultBonus()
	{
		SHBonus bonus = createDefaultBonus();
		assertNotNull(bonus.getModel());
		assertTrue(SHUtils.areEqual(Vector3f.ZERO, bonus.getModel()
				.getLocalTranslation(), 0.001f));
		BoundingBox bound = (BoundingBox)bonus.getModel().getWorldBound();
		assertTrue(SHUtils.areEqual(new Vector3f(0.25f, 0.25f, 0.25f), 
				bound.getExtent(null), 0.001f));
	}
	
	@Test
	public void testLevelWithBonus()
	{
		SHLevel level = createLevelWithBonus();
		assertEquals(1, level.getBonuses().size());
		
		SHBrick brick = level.getBricks().get(0);
		assertNotNull(level.getBonuses().get(brick));
	}
	
}
