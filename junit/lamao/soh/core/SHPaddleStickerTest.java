/* 
 * SHPaddleStickerTest.java 26.03.2010
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
public class SHPaddleStickerTest
{
	@Test
	public void testContstructors()
	{
		SHPaddleSticker sticker = new SHPaddleSticker(
				SHCoreTestHelper.createDefaultBall(),
				SHCoreTestHelper.createDefaultPaddle().getModel());
		assertNotNull(sticker.getBall());
		assertNotNull(sticker.getTarget());
	}
	
	@Test
	public void testMoving()
	{
		SHBall ball = SHCoreTestHelper.createDefaultBall();
		SHPaddle paddle = SHCoreTestHelper.createDefaultPaddle();
		
		ball.setLocation(-1, 2, 0);
		ball.getModel().addController(new SHPaddleSticker(
				ball, paddle.getModel()));
		
		ball.getModel().updateGeometricState(1, true);
		assertTrue(SHUtils.areEqual(new Vector3f(-1, 2, 0), ball.getLocation(),
				0.001f));
		
		paddle.setLocation(2, 0, 0);
		ball.getModel().updateGeometricState(1, true);
		assertTrue(SHUtils.areEqual(new Vector3f(1, 2, 0), ball.getLocation(),
				0.001f));
		
		paddle.setLocation(-3, 0, 0);
		ball.getModel().updateGeometricState(0.5f, true);
		assertTrue(SHUtils.areEqual(new Vector3f(-4, 2, 0), ball.getLocation(),
				0.001f));
		
	}

}
