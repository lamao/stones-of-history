/* 
 * SHBallWallCollisionHandlerTest.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.collisionhandlers;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

import com.jme.math.Vector3f;

import lamao.soh.core.SHEntity;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;
import lamao.soh.core.entities.SHBall;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 *
 */
public class SHBallWallCollisionHandlerTest
{
	@Mock
	private SHEventDispatcher dispatcher;
	
	@Mock
	private SHScene scene;
	
	@Mock
	private SHEvent event;
	
	private SHBallWallCollisionHandler handler;
	
	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		handler = new SHBallWallCollisionHandler(dispatcher, scene); 
	}
	
	@Test
	public void testHandlerLeftWall()
	{
		SHBall ball = new SHBall(null, new Vector3f(-1, 1, 0));
		SHEntity wall = new SHEntity(null, "left-wall", null);
		
		when(event.getParameter("src", SHBall.class)).thenReturn(ball);
		when(event.getParameter("dst", SHEntity.class)).thenReturn(wall);
		
		handler.processEvent(event);
		
		assertTrue(SHUtils.areEqual(new Vector3f(1, 1, 0), ball.getVelocity(), 0.001f));
	}
	
	@Test 
	public void testHandlerRightWall() {
		SHBall ball = new SHBall(null, new Vector3f(1, 1, 0));
		SHEntity wall = new SHEntity(null, "right-wall", null);
		
		when(event.getParameter("src", SHBall.class)).thenReturn(ball);
		when(event.getParameter("dst", SHEntity.class)).thenReturn(wall);
		
		handler.processEvent(event);
		
		assertTrue(SHUtils.areEqual(new Vector3f(-1, 1, 0), ball.getVelocity(), 0.001f));
	}
	
	@Test 
	public void testHandlerTopWall() {
		SHBall ball = new SHBall(null, new Vector3f(1, 1, 0));
		SHEntity wall = new SHEntity(null, "top-wall", null);
		
		when(event.getParameter("src", SHBall.class)).thenReturn(ball);
		when(event.getParameter("dst", SHEntity.class)).thenReturn(wall);
		
		handler.processEvent(event);
		
		assertTrue(SHUtils.areEqual(new Vector3f(1, -1, 0), ball.getVelocity(), 0.001f));
	}

}
