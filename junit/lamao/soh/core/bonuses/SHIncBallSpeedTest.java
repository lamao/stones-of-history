/* 
 * SHIncBallSpeedTest.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import lamao.soh.core.SHBall;
import lamao.soh.core.SHEntityCreator;
import lamao.soh.core.SHScene;

import org.junit.Test;

import com.jme.math.Vector3f;

import static org.junit.Assert.*;

/**
 * Increases ball speed.
 * @author lamao
 *
 */
public class SHIncBallSpeedTest
{
	@Test
	public void testBonus()
	{
		SHScene scene = new SHScene();
		
		SHBall ball1 = SHEntityCreator.createDefaultBall();
		ball1.setVelocity(-1, -1, 0);
		SHBall ball2 = SHEntityCreator.createDefaultBall();
		ball2.setVelocity(1, 1, 0);

		scene.addEntity(ball1);
		scene.addEntity(ball2);
		
		// first bonus
		SHIncBallSpeedBonus bonus = new SHIncBallSpeedBonus();
		assertNotNull(bonus);
		bonus.apply(scene);
		assertTrue(Math.abs(Math.abs(ball1.getVelocity().length() / 
					new Vector3f(-1, -1, 0).length()) - 
					(1 + SHIncBallSpeedBonus.INC_PERCENT)) < 0.001f);
		assertTrue(Math.abs(Math.abs(ball2.getVelocity().length() / 
				new Vector3f(1, 1, 0).length()) - 
				(1 + SHIncBallSpeedBonus.INC_PERCENT)) < 0.001f);
		
		SHIncBallSpeedBonus bonus2 = new SHIncBallSpeedBonus();
		bonus2.apply(scene);
		
		assertTrue(Math.abs(Math.abs(ball1.getVelocity().length() / 
					new Vector3f(-1, -1, 0).length()) - 
					(1 + SHIncBallSpeedBonus.INC_PERCENT) * 
					(1 + SHIncBallSpeedBonus.INC_PERCENT)) < 0.001f);
		assertTrue(Math.abs(Math.abs(ball2.getVelocity().length() / 
				new Vector3f(1, 1, 0).length()) - 
				(1 + SHIncBallSpeedBonus.INC_PERCENT) * 
				(1 + SHIncBallSpeedBonus.INC_PERCENT)) < 0.001f);
		
		SHBall ball3 = SHEntityCreator.createDefaultBall();
		ball3.setVelocity(1, -1, 0);
		scene.addEntity(ball3);
		
		bonus.cleanup(scene);
		assertTrue(Math.abs(Math.abs(ball1.getVelocity().length() / 
					new Vector3f(-1, -1, 0).length()) - 
					(1 + SHIncBallSpeedBonus.INC_PERCENT)) < 0.001f);
		assertTrue(Math.abs(Math.abs(ball2.getVelocity().length() / 
				new Vector3f(1, 1, 0).length()) - 
				(1 + SHIncBallSpeedBonus.INC_PERCENT)) < 0.001f);
		assertTrue(Math.abs(ball3.getVelocity().length() - 
				new Vector3f(1, -1, 0).length()) < 0.001f);
		
		bonus2.cleanup(scene);
		assertTrue(Math.abs(ball1.getVelocity().length() - 
					new Vector3f(-1, -1, 0).length()) < 0.001f);
		assertTrue(Math.abs(ball2.getVelocity().length() - 
				new Vector3f(1, 1, 0).length()) < 0.001f);
		assertTrue(Math.abs(ball3.getVelocity().length() - 
				new Vector3f(1, -1, 0).length()) < 0.001f);
		
	}
	
}
