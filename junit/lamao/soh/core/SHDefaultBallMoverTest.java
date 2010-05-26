/* 
 * SHDefaultBallMoverTest.java 24.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import org.junit.Test;

import com.jme.math.Vector3f;

import static org.junit.Assert.*;

/**
 * @author lamao
 *
 */
public class SHDefaultBallMoverTest
{
	@Test
	public void testConstructors()
	{
		SHDefaultBallMover mover = new SHDefaultBallMover();
		assertNull(mover.getBall());
		
		mover = new SHDefaultBallMover(SHEntityCreator.createDefaultBall());
		assertNotNull(mover.getBall());
	}
	
	@Test
	public void testMoving()
	{
		SHBall ball = SHEntityCreator.createDefaultBall();
		SHDefaultBallMover mover = new SHDefaultBallMover(ball);
		mover.update(1);
		assertTrue(SHUtils.areEqual(new Vector3f(0, 1, 0), ball.getLocation(), 
				0.001f));
		mover.update(0.123f);
		assertTrue(SHUtils.areEqual(new Vector3f(0, 1.123f, 0), ball.getLocation(), 
				0.001f));
		
		ball.setLocation(0, 0, 0);
		ball.setVelocity(new Vector3f(0.4f, -2.4f, 0));
		mover.update(0.25f);
		assertTrue(SHUtils.areEqual(new Vector3f(0.1f, -0.6f, 0), 
				ball.getLocation(), 0.001f));
		
	}

}
