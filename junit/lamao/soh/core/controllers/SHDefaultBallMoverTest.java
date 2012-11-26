/* 
 * SHDefaultBallMoverTest.java 24.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.controllers;

import lamao.soh.core.SHEntityCreator;
import lamao.soh.core.SHUtils;
import lamao.soh.core.controllers.SHDefaultBallMover;
import lamao.soh.core.entities.SHBall;


import com.jme.math.Vector3f;

import org.testng.annotations.Test;
import static org.testng.Assert.*;
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
		assertTrue(SHUtils.areEqual(new Vector3f(0, 0, -1), ball.getLocation(), 
				0.001f));
		mover.update(0.123f);
		assertTrue(SHUtils.areEqual(new Vector3f(0, 0, -1.123f), ball.getLocation(), 
				0.001f));
		
		ball.setLocation(0, 0, 0);
		ball.setVelocity(new Vector3f(0.4f, 0, 2.4f));
		mover.update(0.25f);
		assertTrue(SHUtils.areEqual(new Vector3f(0.1f, 0, 0.6f), 
				ball.getLocation(), 0.001f));
		
	}

}
