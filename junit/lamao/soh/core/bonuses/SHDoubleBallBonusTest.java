/* 
 * SHDoubleBonusTest.java 29.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import java.util.List;

import lamao.soh.core.SHEntity;
import lamao.soh.core.SHEntityCreator;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;
import lamao.soh.core.entities.SHBall;

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
		
		SHScene scene = new SHScene();
		scene.addEntity(SHEntityCreator.createDefaultBall());
		SHBall ball = SHEntityCreator.createDefaultBall();
		ball.setVelocity(-1, 2, 0);
		scene.addEntity(ball);
		
		// double balls
		bonus.apply(scene);
		List<SHEntity> balls = scene.getEntities("ball");
		assertEquals(4, balls.size());
		assertEquals(1, balls.get(2).getRoot().getControllerCount());
		assertEquals(1, balls.get(3).getRoot().getControllerCount());
		float angle = SHUtils.angle(((SHBall)balls.get(0)).getVelocity()) - 
				SHUtils.angle(((SHBall)balls.get(2)).getVelocity());
		assertTrue(Math.abs(angle - Math.PI / 4) < 0.001f);
		angle = SHUtils.angle(((SHBall)balls.get(1)).getVelocity()) - 
				SHUtils.angle(((SHBall)balls.get(3)).getVelocity());
		assertTrue(Float.toString(angle), Math.abs(angle - Math.PI / 4) < 0.001f);
		
		// add new ball to level and double balls
		scene.addEntity(SHEntityCreator.createDefaultBall());
		bonus.apply(scene);
		assertEquals(10, balls.size());
		
		// first cleanup
		bonus.cleanup(scene);
		assertEquals(5, balls.size());
		
		// remove few balls and perform cleanup
		scene.removeEntity(balls.get(0));
		scene.removeEntity(balls.get(0));		
		bonus.cleanup(scene);
		assertEquals(2, balls.size());
	}
}
