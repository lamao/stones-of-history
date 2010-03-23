/* 
 * SHBallTest.java 23.10.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import org.junit.Before;
import org.junit.Test;

import com.jme.bounding.BoundingBox;
import com.jme.math.Vector3f;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;

import static org.junit.Assert.*;

/**
 * @author lamao
 *
 */
public class SHBallTest
{
	private SHBall sharedBall; 
	private SHBrick sharedBrick;
	
	/** Creates default box brick dimension (1, 1, 1) located at (0, 0, 0) */ 
	SHBrick createDefaultBrick()
	{
		SHBrick brick = new SHBrick(new Box("brick", new Vector3f(0, 0, 0), 
				2, 1, 1));
		brick.getModel().setModelBound(new BoundingBox());
		brick.getModel().updateModelBound();
		return brick;
	}
	
	/** Creates default ball with radius 1 located at (0, 0, 0)*/
	SHBall createDefaultBall()
	{
		return new SHBall(new Sphere("ball", 15, 15, 1));
	}
	
	@Before
	public void setUp()
	{
		sharedBall = createDefaultBall();
		sharedBrick = createDefaultBrick();
	}
	
	@Test
	public void testConstructors()
	{
		SHBall ball = new SHBall();
		assertNotNull(ball);
		assertEquals(Vector3f.UNIT_Y, ball.getVelocity());
	}
	
	@Test
	public void testMoving()
	{
		sharedBall.setLocation(new Vector3f(0, 2, 0));
		assertEquals(new Vector3f(0, 2, 0), sharedBall.getLocation());
		assertEquals(new Vector3f(0, 2, 0), sharedBall.getModel()
				.getLocalTranslation());
	}
	
	@Test
	public void testBottomHit()
	{
		SHBall ball = createDefaultBall();
		ball.setLocation(new Vector3f(0, -2, 0));
		ball.onHit(sharedBrick);
		assertTrue(SHUtils.areEqual(new Vector3f(0, -1, 0), 
									ball.getVelocity(), 
									0.001f));
		
		ball.setVelocity(new Vector3f(2, 1, 0));
		ball.setLocation(new Vector3f(0.5f, -2, 0));
		ball.onHit(sharedBrick);
		assertTrue(SHUtils.areEqual(new Vector3f(2, -1, 0), 
									ball.getVelocity(), 
									0.001f));
	}
	
	@Test
	public void testUpHit()
	{
		SHBall ball = createDefaultBall();
		ball.setLocation(new Vector3f(0, 2, 0));
		ball.setVelocity(new Vector3f(0, -1, 0));
		ball.onHit(sharedBrick);
		assertTrue(SHUtils.areEqual(Vector3f.UNIT_Y, 
									ball.getVelocity(), 
									0.001f));
		
		ball.setVelocity(new Vector3f(-1, -2, 0));
		ball.setLocation(new Vector3f(0.5f, 2, 0));
		ball.onHit(sharedBrick);
		assertTrue(SHUtils.areEqual(new Vector3f(-1, 2, 0), 
									ball.getVelocity(), 
									0.001f));
	}
	
	@Test
	public void testLeftHit()
	{
		SHBall ball = createDefaultBall();
		ball.setLocation(new Vector3f(-3, 0, 0));
		ball.setVelocity(new Vector3f(1, 0, 0));
		ball.onHit(sharedBrick);
		assertTrue(SHUtils.areEqual(new Vector3f(-1, 0, 0), 
									ball.getVelocity(), 
									0.001f));
		
		ball.setVelocity(new Vector3f(1, 1, 0));
		ball.setLocation(new Vector3f(-3, -0.5f, 0));
		ball.onHit(sharedBrick);
		assertTrue(SHUtils.areEqual(new Vector3f(-1, 1, 0), 
									ball.getVelocity(), 
									0.001f));
	}
	
	@Test
	public void testRightHit()
	{
		SHBall ball = createDefaultBall();
		ball.setLocation(new Vector3f(3, 0, 0));
		ball.setVelocity(new Vector3f(-1, 0, 0));
		ball.onHit(sharedBrick);
		assertTrue(SHUtils.areEqual(new Vector3f(1, 0, 0), 
									ball.getVelocity(), 
									0.001f));
		
		ball.setVelocity(new Vector3f(-1, -1, 0));
		ball.setLocation(new Vector3f(3, -0.7f, 0));
		ball.onHit(sharedBrick);
		assertTrue(SHUtils.areEqual(new Vector3f(1, -1, 0), 
									ball.getVelocity(), 
									0.001f));
		
	}
	
	@Test
	public void testCornerHits()
	{
		SHBall ball = createDefaultBall();
		// upper left
		ball.setLocation(-3, 2, 0);
		ball.setVelocity(1, -1, 0);
		ball.onHit(sharedBrick);
		assertTrue(SHUtils.areEqual(new Vector3f(-1, 1, 0), 
									ball.getVelocity(), 
									0.001f));
		// upper right
		ball.setLocation(3, 2, 0);
		ball.setVelocity(-1, -1, 0);
		ball.onHit(sharedBrick);
		assertTrue(SHUtils.areEqual(new Vector3f(1, 1, 0), 
									ball.getVelocity(), 
									0.001f));
		
		// bottom left
		ball.setLocation(-3, -2, 0);
		ball.setVelocity(1, 1, 0);
		ball.onHit(sharedBrick);
		assertTrue(SHUtils.areEqual(new Vector3f(-1, -1, 0), 
									ball.getVelocity(), 
									0.001f));
		
		// bottom right
		ball.setLocation(-3, 2, 0);
		ball.setVelocity(-1, 1, 0);
		ball.onHit(sharedBrick);
		assertTrue(SHUtils.areEqual(new Vector3f(1, -1, 0), 
									ball.getVelocity(), 
									0.001f));
		
	}

}
