/* 
 * SHDefaultHitHandler.java 24.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import org.testng.annotations.Test;
import static org.testng.Assert.*;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHPaddle;



import com.jme3.math.Vector3f;

/**
 * @author lamao
 *
 */
public class SHDefaultPaddleHitHandlerTest
{
	@Test
	public void testConstructors()
	{
		SHDefaultPaddleHitHandler handler = new SHDefaultPaddleHitHandler();
		assertNotNull(handler);
	}
	
	@Test
	public void testOnHitWithUnitySpeed()
	{
		SHPaddle paddle = SHEntityCreator.createDefaultPaddle();
		SHBall ball = SHEntityCreator.createDefaultBall();
		
		// result velocity = -90 degrees
		ball.setLocation(-2, 0, -2);
		ball.setVelocity(0, 0, 1);
		paddle.onHit(ball);
		assertTrue(SHUtils.areEqual(new Vector3f(-1, 0, 0), ball.getVelocity(), 0.001f));
		
		// result velocity = -45 degrees
		ball.setLocation(-(float)Math.sqrt(2), 0, -2);
		ball.setVelocity(0, 0, 1);
		paddle.onHit(ball);
		assertTrue(ball.getVelocity().x < 0);
		assertTrue(ball.getVelocity().z < 0);
		assertTrue(Math.abs(-ball.getVelocity().x + ball.getVelocity().z) < 0.001f);
		
		// center of the paddle
		ball.setLocation(0, 0, -2);	
		ball.setVelocity(0, 0, 1);
		paddle.onHit(ball);
		assertTrue(SHUtils.areEqual(new Vector3f(0, 0, -1), ball.getVelocity(), 0.001f));
		
		// result velocity = 45 degrees
		ball.setLocation((float)Math.sqrt(2), 0, -2);
		ball.setVelocity(0, 0, 1);
		paddle.onHit(ball);
		assertTrue(ball.getVelocity().x > 0);
		assertTrue(ball.getVelocity().z < 0);
		assertTrue(Math.abs(ball.getVelocity().x + ball.getVelocity().z) < 0.001f);
		
		// result velocity = 90 degrees
		ball.setLocation(2, 0, -2);
		ball.setVelocity(0, 0, 1);
		paddle.onHit(ball);
		assertTrue(SHUtils.areEqual(new Vector3f(1, 0, 0), ball.getVelocity(), 0.001f));
	}
	
	@Test
	public void testOnHitWithCustomSpeed()
	{
		SHPaddle paddle = SHEntityCreator.createDefaultPaddle();
		SHBall ball = SHEntityCreator.createDefaultBall();
		
		// center of paddle
		ball.setLocation(0, 0, -2);
		ball.setVelocity((float)Math.sqrt(2),0,  (float)Math.sqrt(2));
		paddle.onHit(ball);
		assertTrue(SHUtils.areEqual(new Vector3f(0, 0, -2), ball.getVelocity(), 
				0.001f));
		
		// other location
		ball.setLocation(-0.23435f, 0, -2);
		ball.setVelocity((float)Math.sqrt(2), 0, (float)Math.sqrt(2));
		paddle.onHit(ball);
		assertTrue(Math.abs(ball.getVelocity().length() - 2) < 0.001f);
	}
	
	@Test
	public void testCornerHits()
	{
		SHBall ball = SHEntityCreator.createDefaultBall();
		SHPaddle paddle = SHEntityCreator.createDefaultPaddle();
		
		// left corner
		ball.setLocation(-3, 0, -2);
		ball.setVelocity(1, 0, 1);
		paddle.onHit(ball);
		assertTrue(SHUtils.areEqual(new Vector3f(-1, 0, -1), ball.getVelocity(), 
				0.001f));
		
		// left upper side
		ball.setLocation(-3, 0, -1);
		ball.setVelocity(3, 0, 4);
		paddle.onHit(ball);
		assertTrue(SHUtils.areEqual(new Vector3f(-3, 0, -4), ball.getVelocity(), 
				0.001f));
		
		// left bottom side
		ball.setLocation(-3, 0, 1);
		ball.setVelocity(1, 0, 1);
		paddle.onHit(ball);
		assertTrue(SHUtils.areEqual(new Vector3f(1, 0, 1), ball.getVelocity(), 
				0.001f));
		
		// right corner 
		ball.setLocation(3, 0, -2);
		ball.setVelocity(-1, 0, 1);
		paddle.onHit(ball);
		assertTrue(SHUtils.areEqual(new Vector3f(1, 0, -1), ball.getVelocity(), 
				0.001f));
		
		// right upper side 
		ball.setLocation(3, 0, -1);
		ball.setVelocity(-3, 0, 2);
		paddle.onHit(ball);
		assertTrue(SHUtils.areEqual(new Vector3f(3, 0, -2), ball.getVelocity(), 
				0.001f));
		
		// right bottom side 
		ball.setLocation(3, 0, 1);
		ball.setVelocity(-1, 0, 1);
		paddle.onHit(ball);
		assertTrue(SHUtils.areEqual(new Vector3f(-1, 0, 1), ball.getVelocity(), 
				0.001f));
		
	}
	
	@Test
	public void testOnHitStressTest()
	{
		SHPaddle paddle = SHEntityCreator.createDefaultPaddle();
		SHBall ball = SHEntityCreator.createDefaultBall();
		
		ball.setLocation(0.12355f, 2, 0);
		ball.setVelocity(0, -2, 0);
		for (int i = 0; i < 1000; i++)
		{
			paddle.onHit(ball);
		}
		
		assertTrue(Math.abs(ball.getVelocity().length() - 2) < 0.001f);
	}
	
	@Test
	public void testBallWithUpVelocity()
	{
		SHPaddle paddle = SHEntityCreator.createDefaultPaddle();
		SHBall ball = SHEntityCreator.createDefaultBall();
		
		ball.setLocation(0, 2, 0);
		ball.setVelocity(1, 1, 0);
		paddle.onHit(ball);
		assertTrue(SHUtils.areEqual(new Vector3f(1, 1, 0), ball.getVelocity(), 
				0.001f));
	}

}
