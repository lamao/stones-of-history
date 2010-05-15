/* 
 * SHEntityFactory.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.math.Vector3f;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;

/**
 * Factory for creating stubs for entities
 * @author lamao
 *
 */
public class SHEntityCreator
{
	/** Creates default box brick dimension (1, 1, 1) located at (0, 0, 0) */ 
	public static SHBrick createDefaultBrick(String id)
	{
		Box box = new Box(id, new Vector3f(0, 0, 0), 2, 1, 1);
		box.setModelBound(new BoundingBox());
		box.updateModelBound();
		SHBrick brick = new SHBrick(box);
		brick.getRoot().updateGeometricState(0, true);
		brick.setType("brick");
		brick.setName(id);
		
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
	public static SHBall createDefaultBall(String type, String name)
	{
		Sphere model = new Sphere(name, 15, 15, 1);
		model.setModelBound(new BoundingSphere());
		model.updateModelBound();
		
		SHBall ball = new SHBall(model);
		ball.getRoot().updateGeometricState(0, true);
		ball.setType(type);
		ball.setName(name);
		
		return ball;
	}
	
	public static SHBall createDefaultBall()
	{
		return createDefaultBall("ball", "ball");
	}
	
	/** 
	 * Creates default paddle which have size (4, 2, 2) and located at
	 * (0, 0, 0)
	 */
	public static SHPaddle createDefaultPaddle()
	{
		Box box = new Box("paddle", new Vector3f(0, 0, 0), 2, 1, 1);
		box.setModelBound(new BoundingBox());
		box.updateModelBound();
		
		SHPaddle paddle = new SHPaddle(box);
		paddle.setType("paddle");
		paddle.setName("paddle");
		paddle.getRoot().updateGeometricState(0, true);
		return paddle;
	}
	
	@Test
	public void testDefaultBrick()
	{
		SHBrick brick = createDefaultBrick("brick");
		assertNotNull(brick.getRoot().getWorldBound());
		assertEquals(1, brick.getStrength());
		assertFalse(brick.isGlass());
		assertTrue(SHUtils.areEqual(Vector3f.ZERO, brick.getLocation(), 0.001f));
	}
	
	@Test
	public void testGlassBrick()
	{
		SHBrick brick = createGlassBrick("glass");
		assertEquals(1, brick.getStrength());
		assertTrue(brick.isGlass());
		assertTrue(SHUtils.areEqual(Vector3f.ZERO, brick.getLocation(), 0.001f));
	}
	
	@Test
	public void testSuperBrick()
	{
		SHBrick brick = createSuperBrick("super");
		assertEquals(Integer.MAX_VALUE, brick.getStrength());
		assertFalse(brick.isGlass());
		assertTrue(SHUtils.areEqual(Vector3f.ZERO, brick.getLocation(), 0.001f));
	}
	
	@Test
	public void testDefaultBall()
	{
		SHBall ball = createDefaultBall();
		assertEquals("ball", ball.getType());
		assertEquals("ball", ball.getName());
		assertNotNull(ball.getRoot().getWorldBound());
		assertNotNull(ball.getModel());
		assertNotNull(ball.getModel().getWorldBound());
		assertTrue(SHUtils.areEqual(Vector3f.UNIT_Y, ball.getVelocity(), 0.001f));
		assertTrue(SHUtils.areEqual(Vector3f.ZERO, ball.getLocation(), 0.001f));
	}
	
	@Test
	public void testDefaultPaddle()
	{
		SHPaddle paddle = createDefaultPaddle();
		assertEquals("paddle", paddle.getType());
		assertEquals("paddle", paddle.getName());
		assertNotNull(paddle.getRoot().getWorldBound());
		assertNotNull(paddle.getModel());
		assertNotNull(paddle.getModel().getWorldBound());
		assertTrue(paddle.getModel().getWorldBound() instanceof BoundingBox);
		BoundingBox box = (BoundingBox)paddle.getModel().getWorldBound();
		assertTrue(SHUtils.areEqual(new Vector3f(2, 1, 1), box.getExtent(null), 0.001f));
		assertTrue(SHUtils.areEqual(Vector3f.ZERO, paddle.getLocation(), 0.001f));
	}
	
}
