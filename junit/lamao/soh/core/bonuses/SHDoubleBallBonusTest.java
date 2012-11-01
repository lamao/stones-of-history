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

import org.testng.annotations.Test;

import com.jme.scene.Spatial;

import static org.testng.Assert.*;

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
		scene.add(SHEntityCreator.createDefaultBall());
		SHBall ball = SHEntityCreator.createDefaultBall();
		ball.setVelocity(-1, 2, 0);
		scene.add(ball);
		
		// double balls
		bonus.apply(scene);
		List<Spatial> balls = scene.get("ball");
		assertEquals(4, balls.size());
		assertEquals(1, balls.get(2).getControllerCount());
		assertEquals(1, balls.get(3).getControllerCount());
		float angle = SHUtils.angle(((SHBall)balls.get(0)).getVelocity()) - 
				SHUtils.angle(((SHBall)balls.get(2)).getVelocity());
		assertTrue(Math.abs(angle - Math.PI / 4) < 0.001f);
		angle = SHUtils.angle(((SHBall)balls.get(1)).getVelocity()) - 
				SHUtils.angle(((SHBall)balls.get(3)).getVelocity());
		assertTrue(Math.abs(angle - Math.PI / 4) < 0.001f, Float.toString(angle));
		
		// add new ball to level and double balls
		scene.add(SHEntityCreator.createDefaultBall());
		bonus.apply(scene);
		assertEquals(10, scene.get("ball").size());
		
		// first cleanup
		bonus.cleanup(scene);
		assertEquals(5, scene.get("ball").size());
		
		// remove few balls and perform cleanup
		List<SHEntity> ballEntities = scene.getEntities("ball");
		scene.remove(ballEntities.get(0));
		scene.remove(ballEntities.get(1));		
		bonus.cleanup(scene);
		assertEquals(2, scene.get("ball").size());
	}
}
