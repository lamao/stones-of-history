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
	
	/** Creates default box brick dimension (1, 1, 1) located at (0, 0, 0) */ 
	SHBrick createDefaultBrick()
	{
		SHBrick brick = new SHBrick(new Box("brick", new Vector3f(0, 0, 0), 
				new Vector3f(1, 1, 1)));
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
	}
	
	@Test
	public void testConstructors()
	{
		SHBall ball = new SHBall();
		assertNotNull(ball);
		assertEquals(new Vector3f(0, 1, 0), ball.getDirection());
		assertTrue(ball.getSpeed() - 1.0f < 0.01f);
	}
	
	@Test
	public void testMoving()
	{
		sharedBall.setLocation(new Vector3f(0, 2, 0));
		assertEquals(new Vector3f(0, 2, 0), sharedBall.getLocation());
		assertEquals(new Vector3f(0, 2, 0), sharedBall.getModel().getLocalTranslation());
	}
	
	@Test
	public void testDefaultHit()
	{
		SHBall ball = createDefaultBall();
		ball.setLocation(new Vector3f(0, 2, 0));
		ball.onHit(createDefaultBrick());
		assertTrue(SHUtils.areEqual(new Vector3f(0, -1, 0), ball.getDirection(), 0.0001f));
	}

}
