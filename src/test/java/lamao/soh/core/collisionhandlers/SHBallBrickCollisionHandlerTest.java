/* 
 * SHBallBrickCollisionHandlerTest.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.SHEntityCreator;
import lamao.soh.core.SHScene;
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.core.controllers.SHDefaultMover;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHBrick;
import lamao.soh.states.LevelState;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jme3.math.Vector3f;

/**
 * @author lamao
 *
 */
public class SHBallBrickCollisionHandlerTest
{
	
	@Mock
	private SHEventDispatcher dispatcher;

	@Mock
	private LevelState levelState;

	@Mock
	private SHScene scene;
	
	@Mock
	private SHEvent event;
	
	@Mock
	private SHBall ball;
	
	private SHBallBrickCollisionHandler handler;
	
	
	
	@BeforeMethod
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);

		when(levelState.getScene()).thenReturn(scene);
		
		handler = new SHBallBrickCollisionHandler(dispatcher, levelState);
		
		when(event.getParameter("src", SHBall.class)).thenReturn(ball);
		
	}

	@Test(enabled = false) 
	public void testBallCollisionWithDefaultBrick()
	{
		SHBrick brick = SHEntityCreator.createDefaultBrick("brick");
		brick.setStrength(-1); // ball hit on brick
		
		when(event.getParameter("dst", SHBrick.class)).thenReturn(brick);
		
		handler.processEvent(event);		
		
		verify(event).getParameter("src", SHBall.class);
		verify(event).getParameter("dst", SHBrick.class);
		verify(dispatcher).addEventEx("level-brick-hit", handler, "brick", brick);
		verify(scene).remove(brick);
		verify(dispatcher).addEventEx("level-brick-deleted", handler, "brick", brick);
		verify(dispatcher, never()).addEventEx(eq("level-bonus-extracted"), 
				same(handler), eq("bonus"), any(SHBonus.class));
	}
	
	@Test(enabled = false) 
	public void testBallCollisionWithSuperBrick()
	{
		SHBrick brick = SHEntityCreator.createDefaultBrick("brick");
		brick.setStrength(2); // ball hit on brick, but it has 2 lives
		
		when(event.getParameter("dst", SHBrick.class)).thenReturn(brick);
		
		handler.processEvent(event);		
		
		verify(event).getParameter("src", SHBall.class);
		verify(event).getParameter("dst", SHBrick.class);
		verify(dispatcher).addEventEx("level-brick-hit", handler, "brick", brick);
		verify(scene, never()).remove(brick);
		verify(dispatcher, never()).addEventEx("level-brick-deleted", handler, "brick", brick);
		verify(dispatcher, never()).addEventEx(eq("level-bonus-extracted"), 
				same(handler), eq("bonus"), any(SHBonus.class));
	}
	
	@Test(enabled = false) 
	public void testBallCollisionWithBrickContainsBonus()
	{
		SHBonus bonus = mock(SHBonus.class);
		SHBrick brick = mock(SHBrick.class);
		when(brick.getLocation()).thenReturn(Vector3f.UNIT_X.clone());
		when(brick.getStrength()).thenReturn(-1); // ball hit on brick
		when(brick.getBonus()).thenReturn(bonus);
		when(bonus.getType()).thenReturn("bonus");
		
		
		
		when(event.getParameter("dst", SHBrick.class)).thenReturn(brick);
		
		handler.processEvent(event);		
		
		verify(event).getParameter("src", SHBall.class);
		verify(event).getParameter("dst", SHBrick.class);
		verify(dispatcher).addEventEx("level-brick-hit", handler, "brick", brick);
		verify(scene).remove(brick);
		verify(dispatcher).addEventEx("level-brick-deleted", handler, "brick", brick);
		verify(scene).add("bonus", bonus);
		verify(dispatcher).addEventEx(eq("level-bonus-extracted"), 
				same(handler), eq("bonus"), any(SHBonus.class));
		
		verify(bonus).addControl(any(SHDefaultMover.class));
		verify(bonus).setLocation(eq(Vector3f.UNIT_X));
		verify(bonus, times(2)).updateGeometricState();
		verify(dispatcher).addEventEx("level-bonus-extracted", handler, "bonus", bonus);
	}
}
