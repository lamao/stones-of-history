/* 
 * SHStickyPaddleBonusTest.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import lamao.soh.core.SHBall;
import lamao.soh.core.SHCoreTestHelper;
import lamao.soh.core.SHDefaultBallMover;
import lamao.soh.core.SHDefaultPaddleHitHandler;
import lamao.soh.core.SHLevel;
import lamao.soh.core.SHPaddleSticker;
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
		
		SHLevel level = SHCoreTestHelper.createDefaultLevel();
		SHBall ball = level.getBalls().get(0);
		ball.setVelocity(0, -1, 0);
		
		bonus.apply(level);
		level.getPaddle().onHit(ball);
		assertTrue(level.getPaddle().getHitHandler() instanceof 
				SHStickyPaddleHitHandler);
		assertTrue(ball.getModel().getController(0) instanceof SHPaddleSticker);
		
		bonus.cleanup(level);
		assertTrue(level.getPaddle().getHitHandler() instanceof
				SHDefaultPaddleHitHandler);
		assertTrue(ball.getModel().getController(0) instanceof SHDefaultBallMover);
		
		
	}

}
