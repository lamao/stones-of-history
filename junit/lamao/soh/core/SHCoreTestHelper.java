/* 
 * SHCoreTestHelper.java 24 бер. 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import org.junit.Test;

import com.jme.bounding.BoundingBox;
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
	static SHBrick createDefaultBrick()
	{
		SHBrick brick = new SHBrick(new Box("brick", new Vector3f(0, 0, 0), 
				2, 1, 1));
		brick.getModel().setModelBound(new BoundingBox());
		brick.getModel().updateModelBound();
		return brick;
	}
	
	/** Creates default ball with radius 1 located at (0, 0, 0)*/
	static SHBall createDefaultBall()
	{
		return new SHBall(new Sphere("ball", 15, 15, 1));
	}
	
	/** 
	 * Creates default paddle which have size (4, 2, 2) and located at
	 * (0, 0, 0)
	 */
	static SHPaddle createDefaultPaddle()
	{
		SHPaddle paddle = new SHPaddle(new Box("paddle", 
				new Vector3f(0, 0, 0), 2, 1, 1));
		paddle.getModel().setModelBound(new BoundingBox());
		paddle.getModel().updateModelBound();
		return paddle;
	}
	
	@Test
	public void testDefaultBrick()
	{
		SHBrick brick = createDefaultBrick();
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
	public void testDefaultBall()
	{
		SHBall ball = createDefaultBall();
		assertNotNull(ball.getModel());
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

}
