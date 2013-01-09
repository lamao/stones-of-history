/* 
 * SHBallBottomWallCollisionHandlerTest.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.collisionhandlers;

import java.util.Collections;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jme.math.Vector3f;

import static org.testng.Assert.*;

import lamao.soh.core.SHEntityCreator;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHBottomWall;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 *
 */
public class SHBallBottomWallCollisionHandlerTest
{
	@Mock
	private SHEventDispatcher dispatcher;
	
	@Mock
	private SHScene scene;
	
	@Mock
	private SHEvent event;
	
	private SHBallBottomWallCollisionHandler handler;
	
	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		handler = new SHBallBottomWallCollisionHandler(dispatcher, scene); 
	}
	
	
	@Test
	public void testHandlerWallIsActive()
	{
		SHBall ball = SHEntityCreator.createDefaultBall("ball", "ball");
		SHBottomWall wall = new SHBottomWall("wall", "bottom-wall", null);
		wall.setActive(true);
		ball.setVelocity(1, 0, 1);
		
		when(event.getParameter("src", SHBall.class)).thenReturn(ball);
		when(event.getParameter("dst", SHBottomWall.class)).thenReturn(wall);
		
		handler.processEvent(event);
		
		verify(event).getParameter("src", SHBall.class);
		verify(event).getParameter("dst", SHBottomWall.class);
		assertTrue(SHUtils.areEqual(ball.getVelocity(), new Vector3f(1, 0, -1), 0.001f));
		verify(dispatcher).addEvent("level-wall-hit", handler);
	}
	
	@Test
	public void testHandlerWallIsNotActiveLevelFailed()
	{
		SHBall ball = SHEntityCreator.createDefaultBall("ball", "ball");
		SHBottomWall wall = new SHBottomWall("wall", "bottom-wall", null);
		wall.setActive(false);
		
		when(event.getParameter("src", SHBall.class)).thenReturn(ball);
		when(event.getParameter("dst", SHBottomWall.class)).thenReturn(wall);
		when(scene.get(anyString())).thenReturn(null);
		
		handler.processEvent(event);
		
		verify(scene).remove(ball);
		verify(dispatcher).addEvent("level-failed", handler);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testHandlerWallIsNotActiveLevelNotFailed()
	{
		SHBall ball = SHEntityCreator.createDefaultBall("ball", "ball");
		SHBottomWall wall = new SHBottomWall("wall", "bottom-wall", null);
		wall.setActive(false);
		
		when(event.getParameter("src", SHBall.class)).thenReturn(ball);
		when(event.getParameter("dst", SHBottomWall.class)).thenReturn(wall);
		when(scene.get(anyString())).thenReturn(Collections.EMPTY_LIST);
		
		handler.processEvent(event);
		
		verify(scene).remove(ball);
		verify(dispatcher, never()).addEvent("level-failed", handler);
	}

}
