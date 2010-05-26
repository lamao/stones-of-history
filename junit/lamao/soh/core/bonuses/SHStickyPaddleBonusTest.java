/* 
 * SHStickyPaddleBonusTest.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import lamao.soh.core.SHBall;
import lamao.soh.core.SHDefaultBallMover;
import lamao.soh.core.SHDefaultPaddleHitHandler;
import lamao.soh.core.SHEntityCreator;
import lamao.soh.core.SHPaddle;
import lamao.soh.core.SHPaddleSticker;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHStickyPaddleHitHandler;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author lamao
 *
 */
public class SHStickyPaddleBonusTest
{
	@Test
	public void testBonus()
	{
		SHStickyPaddleBonus bonus = new SHStickyPaddleBonus();
		assertTrue(Math.abs(bonus.getDuration() - SHStickyPaddleBonus.DURATION) 
				< 0.001f);
		assertTrue(bonus.isAddictive());
		
		SHScene scene = new SHScene();
		SHPaddle paddle = SHEntityCreator.createDefaultPaddle();
		SHBall ball1 = SHEntityCreator.createDefaultBall("ball", "ball1");
		
		scene.addEntity(paddle);
		scene.addEntity(ball1);
		ball1.setVelocity(0, -1, 0);
		
		bonus.apply(scene);
		paddle.onHit(ball1);
		assertTrue(paddle.getHitHandler() instanceof 
				SHStickyPaddleHitHandler);
		assertTrue(ball1.getRoot().getController(0) instanceof SHPaddleSticker);
		
		bonus.cleanup(scene);
		assertTrue(paddle.getHitHandler() instanceof
				SHDefaultPaddleHitHandler);
		assertTrue(ball1.getRoot().getController(0) instanceof SHDefaultBallMover);
		
		
	}

}
