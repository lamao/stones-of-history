/* 
 * SHStickyPaddleBonusTest.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import java.util.ArrayList;
import java.util.List;

import lamao.soh.core.SHDefaultPaddleHitHandler;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHStickyPaddleHitHandler;
import lamao.soh.core.controllers.SHDefaultBallMover;
import lamao.soh.core.controllers.SHPaddleSticker;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHPaddle;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jme3.scene.Controller;
import com.jme3.scene.Spatial;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;
import static org.mockito.Mockito.*;
/**
 * @author lamao
 *
 */
public class SHStickyPaddleBonusTest
{
	@Mock
	private SHScene scene;
	@Mock
	private SHPaddle paddle;
	@Mock
	private SHBall ball;
	
	private SHStickyPaddleBonus bonus;
	
	@BeforeMethod
	public void setUp()
	{
		initMocks(this);
		
		bonus = new SHStickyPaddleBonus();
		when(scene.getEntity("paddle", "paddle", SHPaddle.class)).thenReturn(paddle);
		List<Spatial> balls = new ArrayList<Spatial>();
		balls.add(ball);
		when(scene.get("ball")).thenReturn(balls);
	}
	
	@Test
	public void testConstructor()
	{
		assertTrue(Math.abs(bonus.getDuration() - SHStickyPaddleBonus.DURATION) 
				< 0.001f);
		assertTrue(bonus.isAddictive());
	}
	
	@Test
	public void testBonusApply()
	{
		bonus.apply(scene);
		verify(paddle).setHitHandler(any(SHStickyPaddleHitHandler.class));
	}
	
	@Test
	public void testCleanup()
	{
		ArrayList<Controller> controllers = new ArrayList<Controller>();
		controllers.add(mock(SHPaddleSticker.class));
		when(ball.getControllers()).thenReturn(controllers);
		
		bonus.cleanup(scene);
		
		verify(paddle).setHitHandler(any(SHDefaultPaddleHitHandler.class));
		verify(ball).removeController(any(SHPaddleSticker.class));
		verify(ball).addController(any(SHDefaultBallMover.class));
		
		
	}

}
