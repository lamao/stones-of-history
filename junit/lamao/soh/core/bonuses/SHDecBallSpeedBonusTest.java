/* 
 * SHDecBallSpeedBonusTest.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import lamao.soh.core.SHBall;
import lamao.soh.core.SHCoreTestHelper;
import lamao.soh.core.SHLevel;

import org.junit.Test;

import com.jme.math.Vector3f;

/**
 * @author lamao
 *
 */
public class SHDecBallSpeedBonusTest
{
	@Test
	public void testBonus()
	{
		SHLevel level = new SHLevel();
		
		level.addBall(SHCoreTestHelper.createDefaultBall());
		level.addBall(SHCoreTestHelper.createDefaultBall());
		for (SHBall ball : level.getBalls())
		{
			ball.setVelocity(-1, -1, 0);
		}
		
		// first bonus
		SHDecBallSpeedBonus bonus = new SHDecBallSpeedBonus();
		assertNotNull(bonus);
		bonus.apply(level);
		for (SHBall ball : level.getBalls())
		{
			assertTrue(Math.abs(Math.abs(ball.getVelocity().length() / 
					new Vector3f(-1, -1, 0).length()) - 
					(1 - SHDecBallSpeedBonus.DEC_PERCENT)) < 0.001f);
		}
		
		SHDecBallSpeedBonus bonus2 = new SHDecBallSpeedBonus();
		bonus2.apply(level);
		for (SHBall ball : level.getBalls())
		{
			assertTrue(Math.abs(Math.abs(ball.getVelocity().length() / 
					new Vector3f(-1, -1, 0).length()) - 
					(1 - SHDecBallSpeedBonus.DEC_PERCENT) * 
					(1 - SHDecBallSpeedBonus.DEC_PERCENT)) < 0.001f);
		}
		
		level.addBall(SHCoreTestHelper.createDefaultBall());
		level.getBalls().get(2).setVelocity(-1, -1, 0);
		
		bonus.cleanup(level);
		for (int i = 0; i < 2; i++)
		{
			SHBall ball = level.getBalls().get(i);
			assertTrue(Math.abs(Math.abs(ball.getVelocity().length() / 
					new Vector3f(-1, -1, 0).length()) - 
					(1 - SHIncBallSpeedBonus.INC_PERCENT)) < 0.001f);
		}
		
		bonus2.cleanup(level);
		for (SHBall ball : level.getBalls())
		{
			assertTrue(Math.abs(ball.getVelocity().length() - 
					new Vector3f(-1, -1, 0).length()) < 0.001f);
		}
	}
}
