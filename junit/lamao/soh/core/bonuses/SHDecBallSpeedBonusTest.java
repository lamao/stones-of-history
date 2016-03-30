/* 
 * SHDecBallSpeedBonusTest.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import java.util.ArrayList;
import java.util.List;

import lamao.soh.core.SHEntityCreator;
import lamao.soh.core.SHScene;
import lamao.soh.core.entities.SHBall;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * @author lamao
 *
 */
public class SHDecBallSpeedBonusTest
{
	@Mock
	private SHScene scene;
	
	@BeforeMethod
	public void setUp()
	{
		initMocks(this);
	}
	@Test
	public void testBonuOnce()
	{
		List<Spatial> balls = new ArrayList<Spatial>();
		SHBall ball1 = SHEntityCreator.createDefaultBall();
		ball1.setVelocity(-1, 0, 1);
		SHBall ball2 = SHEntityCreator.createDefaultBall();
		ball2.setVelocity(1, 0, -1);
		balls.add(ball1);
		balls.add(ball2);
		when(scene.get("ball")).thenReturn(balls);
		
		// first bonus
		SHDecBallSpeedBonus bonus = new SHDecBallSpeedBonus();
		assertNotNull(bonus);
		bonus.apply(scene);
		assertTrue(Math.abs(Math.abs(ball1.getVelocity().length() / 
					new Vector3f(-1, 0, 1).length()) - 
					(1 - SHDecBallSpeedBonus.DEC_PERCENT)) < 0.001f);
		assertTrue(Math.abs(Math.abs(ball2.getVelocity().length() / 
				new Vector3f(1, 0, -1).length()) - 
				(1 - SHDecBallSpeedBonus.DEC_PERCENT)) < 0.001f);
	}
	
	@Test
	public void testBonusTwice() 
	{
		List<Spatial> balls = new ArrayList<Spatial>();
		SHBall ball1 = SHEntityCreator.createDefaultBall();
		ball1.setVelocity(-1, 0, 1);
		SHBall ball2 = SHEntityCreator.createDefaultBall();
		ball2.setVelocity(1, 0, -1);
		balls.add(ball1);
		balls.add(ball2);
		when(scene.get("ball")).thenReturn(balls);
		
		SHDecBallSpeedBonus bonus = new SHDecBallSpeedBonus();
		bonus.apply(scene);
		bonus.apply(scene);
		
		assertTrue(Math.abs(Math.abs(ball1.getVelocity().length() / 
					new Vector3f(-1, 0, 1).length()) - 
					(1 - SHDecBallSpeedBonus.DEC_PERCENT) * 
					(1 - SHDecBallSpeedBonus.DEC_PERCENT)) < 0.001f);
		assertTrue(Math.abs(Math.abs(ball2.getVelocity().length() / 
				new Vector3f(1, 0, -1).length()) - 
				(1 - SHDecBallSpeedBonus.DEC_PERCENT) * 
				(1 - SHDecBallSpeedBonus.DEC_PERCENT)) < 0.001f);
	}
	
	@Test
	public void testBonusCleanup()
	{
		List<Spatial> balls = new ArrayList<Spatial>();
		SHBall ball1 = SHEntityCreator.createDefaultBall();
		ball1.setVelocity(-1, 0, 1);
		SHBall ball2 = SHEntityCreator.createDefaultBall();
		ball2.setVelocity(1, 0, -1);
		balls.add(ball1);
		balls.add(ball2);
		when(scene.get("ball")).thenReturn(balls);
		
		SHDecBallSpeedBonus bonus = new SHDecBallSpeedBonus();
		bonus.apply(scene);
		
		SHBall ball3 = SHEntityCreator.createDefaultBall();
		ball3.setVelocity(1, 0, 1);
		balls.add(ball3);
		when(scene.get("ball")).thenReturn(balls);
		
		bonus.cleanup(scene);
		assertTrue(Math.abs(ball1.getVelocity().length() - 
					new Vector3f(-1, 0, 1).length()) < 0.001f);
		assertTrue(Math.abs(ball2.getVelocity().length() - 
				new Vector3f(1, 0, -1).length()) < 0.001f);
		assertTrue(Math.abs(ball3.getVelocity().length() - 
				new Vector3f(1, 0, 1).length()) < 0.001f);
	}
}
