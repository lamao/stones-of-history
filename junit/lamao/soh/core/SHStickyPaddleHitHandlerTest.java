/* 
 * SHStickyPaddleHitHandlerTest.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import lamao.soh.core.bonuses.SHDefaultMover;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author lamao
 *
 */
public class SHStickyPaddleHitHandlerTest
{

	@Test
	public void testHandler()
	{
		SHBall ball = SHCoreTestHelper.createDefaultBall();
		SHPaddle paddle = SHCoreTestHelper.createDefaultPaddle();
		SHStickyPaddleHitHandler handler = new SHStickyPaddleHitHandler();
		
		handler.execute(ball, paddle);
		assertEquals(0, ball.getModel().getControllerCount());
		
		ball.setVelocity(0, -1, 0);
		handler.execute(ball, paddle);
		assertEquals(1, ball.getModel().getControllerCount());
		assertTrue(ball.getModel().getController(0) instanceof SHPaddleSticker);
		
		handler.execute(ball, paddle);
		assertEquals(1, ball.getModel().getControllerCount());
		assertTrue(ball.getModel().getController(0) instanceof SHPaddleSticker);
	}
}
