/* 
 * SHPaddleStickerTest.java 26.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.controllers;

import lamao.soh.core.SHEntityCreator;
import lamao.soh.core.SHUtils;
import lamao.soh.core.controllers.SHPaddleSticker;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHPaddle;


import com.jme3.math.Vector3f;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

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
				SHEntityCreator.createDefaultBall(),
				SHEntityCreator.createDefaultPaddle());
		assertNotNull(sticker.getBall());
		assertNotNull(sticker.getTarget());
	}
	
	@Test
	public void testMoving()
	{
		SHBall ball = SHEntityCreator.createDefaultBall();
		SHPaddle paddle = SHEntityCreator.createDefaultPaddle();
		
		ball.setLocation(-1, 2, 0);
		SHPaddleSticker sticker = new SHPaddleSticker(ball, paddle);
		
		sticker.update(1);
		assertTrue(SHUtils.areEqual(new Vector3f(-1, 2, 0), ball.getLocation(),
				0.001f));
		
		paddle.setLocation(2, 0, 0);
		sticker.update(1);
		assertTrue(SHUtils.areEqual(new Vector3f(1, 2, 0), ball.getLocation(),
				0.001f));
		
		paddle.setLocation(-3, 0, 0);
		sticker.update(1);
		assertTrue(SHUtils.areEqual(new Vector3f(-4, 2, 0), ball.getLocation(),
				0.001f));
		
	}

}
