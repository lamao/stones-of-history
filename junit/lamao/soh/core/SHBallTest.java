/* 
 * SHBallTest.java 23.10.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import org.junit.Before;
import org.junit.Test;

import com.jme.math.Vector3f;

import static org.junit.Assert.*;

/**
 * @author lamao
 *
 */
public class SHBallTest
{
	private SHBrick sharedBrick;
	
	@Before
	public void setUp()
	{
		sharedBrick = SHCoreTestHelper.createDefaultBrick("brick");
	}
	
	@Test
	public void testConstructors()
	{
		SHBall ball = new SHBall();
		assertNotNull(ball);
		assertEquals(Vector3f.UNIT_Y, ball.getVelocity());
	}
	
	@Test
	public void testBottomHit()
	{
		SHBall ball = SHCoreTestHelper.createDefaultBall();
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
		SHBall ball = SHCoreTestHelper.createDefaultBall();
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
		SHBall ball = SHCoreTestHelper.createDefaultBall();
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
		SHBall ball = SHCoreTestHelper.createDefaultBall();
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
		SHBall ball = SHCoreTestHelper.createDefaultBall();
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
	
	@Test
	public void testGlassHit()
	{
		SHBall ball = SHCoreTestHelper.createDefaultBall();
		SHBrick brick = SHCoreTestHelper.createDefaultBrick("brick");
		
		ball.setLocation(0, 2, 0);
		brick.setGlass(true);
		ball.onHit(brick);
		assertTrue(SHUtils.areEqual(Vector3f.UNIT_Y, ball.getVelocity(), 0.001f));
	}
	
	@Test
	public void testStrengthDecreasing()
	{
		sharedBrick.setStrength(10);
		SHBall ball = SHCoreTestHelper.createDefaultBall();
		
		ball.setLocation(0, 2, 0);
		ball.onHit(sharedBrick);
		ball.onHit(sharedBrick);
		assertEquals(8, sharedBrick.getStrength());
		
		sharedBrick.setGlass(true);
		ball.onHit(sharedBrick);
		assertEquals(7, sharedBrick.getStrength());
		
		sharedBrick.setStrength(Integer.MAX_VALUE);
		ball.onHit(sharedBrick);
		assertEquals(Integer.MAX_VALUE, sharedBrick.getStrength());
	}

}
