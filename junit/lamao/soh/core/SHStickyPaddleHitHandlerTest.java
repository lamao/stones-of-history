/* 
 * SHStickyPaddleHitHandlerTest.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import lamao.soh.core.controllers.SHPaddleSticker;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHPaddle;

import org.testng.annotations.Test;
import static org.testng.Assert.*;
/**
 * @author lamao
 *
 */
public class SHStickyPaddleHitHandlerTest
{

	@Test
	public void testHandler()
	{
		SHBall ball = SHEntityCreator.createDefaultBall();
		SHPaddle paddle = SHEntityCreator.createDefaultPaddle();
		SHStickyPaddleHitHandler handler = new SHStickyPaddleHitHandler();
		
		handler.execute(ball, paddle);
		assertEquals(0, ball.getModel().getControllerCount());
		
		ball.setVelocity(0, 0, 1);
		handler.execute(ball, paddle);
		assertEquals(1, ball.getControllerCount());
		assertTrue(ball.getController(0) instanceof SHPaddleSticker);
		
		handler.execute(ball, paddle);
		assertEquals(1, ball.getControllerCount());
		assertTrue(ball.getController(0) instanceof SHPaddleSticker);
	}
}
