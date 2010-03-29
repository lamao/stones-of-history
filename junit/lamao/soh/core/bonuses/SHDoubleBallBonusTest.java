/* 
 * SHDoubleBonusTest.java 29.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import lamao.soh.core.SHBall;
import lamao.soh.core.SHCoreTestHelper;
import lamao.soh.core.SHLevel;
import lamao.soh.core.SHUtils;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author lamao
 *
 */
public class SHDoubleBallBonusTest
{
	
	@Test
	public void testBonus()
	{
		SHDoubleBallBonus bonus = new SHDoubleBallBonus();
		assertTrue(Math.abs(bonus.getDuration() - SHDoubleBallBonus.DURATION) < 
				0.001f);
		
		SHLevel level = new SHLevel();
		level.addBall(SHCoreTestHelper.createDefaultBall());
		SHBall ball = SHCoreTestHelper.createDefaultBall();
		ball.setVelocity(-1, 2, 0);
		level.addBall(ball);
		
		// double balls
		bonus.apply(level);
		assertEquals(4, level.getBalls().size());
		assertEquals(1, level.getBalls().get(2).getModel().getControllerCount());
		assertEquals(1, level.getBalls().get(3).getModel().getControllerCount());
		float angle = SHUtils.angle(level.getBalls().get(0).getVelocity()) - 
				SHUtils.angle(level.getBalls().get(2).getVelocity());
		assertTrue(Math.abs(angle - Math.PI / 4) < 0.001f);
		angle = SHUtils.angle(level.getBalls().get(1).getVelocity()) - 
				SHUtils.angle(level.getBalls().get(3).getVelocity());
		assertTrue(Float.toString(angle), Math.abs(angle - Math.PI / 4) < 0.001f);
		
		// add new ball to level and double balls
		level.addBall(SHCoreTestHelper.createDefaultBall());
		bonus.apply(level);
		assertEquals(10, level.getBalls().size());
		
		// first cleanup
		bonus.cleanup(level);
		assertEquals(5, level.getBalls().size());
		
		// remove few balls and perform cleanup
		level.getBalls().remove(0);
		level.getBalls().remove(0);		
		bonus.cleanup(level);
		assertEquals(2, level.getBalls().size());
	}
}
