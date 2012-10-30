/* 
 * CollisionProcessorTest.java 16.09.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import lamao.soh.core.entities.SHBall;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jme.scene.Node;
import com.jme.scene.Spatial;

/**
 * @author lamao
 *
 */
public class CollisionProcessorTest
{
	private final static String TYPE = "type";
	private final static String OTHER_TYPE = "other type";
	
	private SHCollisionProcessor processor;
	
	@Mock
	private SHEventDispatcher dispatcher;
	
	@BeforeMethod
	public void setUp() {
		initMocks(this);
		processor = new SHCollisionProcessor(dispatcher);
	}
	
	@Test
	public void testAddCollisionTask()
	{
		processor.addCollisionTask(new SHCollisionTask(TYPE, OTHER_TYPE, false));
		assertEquals(1, processor.getCollisionTasks().size());
		
		SHCollisionTask someTask = new SHCollisionTask(TYPE, OTHER_TYPE, false);
		processor.addCollisionTask(someTask);
		assertEquals(2, processor.getCollisionTasks().size());
	}
	
	@Test
	public void testRemoveCollisionTasks() {
		processor.addCollisionTask(new SHCollisionTask(TYPE, OTHER_TYPE, false));
		
		processor.addCollisionTask(new SHCollisionTask(OTHER_TYPE, TYPE, false));
		
		processor.removeCollisionTask(new SHCollisionTask(OTHER_TYPE, TYPE, true));
		assertEquals(processor.getCollisionTasks().size(), 1);
		
		processor.removeCollisionTask(new SHCollisionTask(TYPE, OTHER_TYPE, true));
		assertEquals(processor.getCollisionTasks().size(), 0);
	}
	
	@Test
	public void testCollisionBoundingSuccess()
	{
		SHEntity brick1 = SHEntityCreator.createDefaultBrick("brick1");
		
		SHBall ball = SHEntityCreator.createDefaultBall();
		ball.setLocation(-2.99f, -1.99f, 0);
		ball.updateGeometricState(0, true);
		
		Node rootNode = new Node();
		Node bricks = new Node("brick");
		bricks.attachChild(brick1);
		Node balls = new Node("ball");
		balls.attachChild(ball);
		rootNode.attachChild(bricks);
		rootNode.attachChild(balls);
		
		SHCollisionTask task = new SHCollisionTask("ball", "brick", false);
		processor.addCollisionTask(task);
		
		
		processor.processCollisions(rootNode);
		
		verify(dispatcher).addEvent(any(SHEvent.class));
	}
	
	@Test
	public void testCollisionBoundingFail()
	{
		SHEntity brick1 = SHEntityCreator.createDefaultBrick("brick1");
		brick1.updateGeometricState(0, true);
		
		SHBall ball = SHEntityCreator.createDefaultBall();
		ball.setLocation(-3.01f, -2.01f, 0);
		ball.updateGeometricState(0, true);
		
		SHCollisionTask task = new SHCollisionTask("ball", "brick", false);
		processor.addCollisionTask(task);
		
		Node rootNode = mock(Node.class);
		Node bricks = mock(Node.class);
		Node balls = mock(Node.class);
		when(rootNode.getChild("brick")).thenReturn(bricks);
		when(rootNode.getChild("ball")).thenReturn(balls);
		when(bricks.getChildren()).thenReturn(new ArrayList<Spatial>(Arrays.asList(brick1)));
		when(balls.getChildren()).thenReturn(new ArrayList<Spatial>(Arrays.asList(ball)));
		
		processor.processCollisions(rootNode);
		
		verify(dispatcher, never()).addEvent(any(SHEvent.class));
	}
	
	@Test
	public void testCollisionTriangleFail()
	{
		SHEntity brick1 = SHEntityCreator.createDefaultBrick("brick1");
		
		SHBall ball = SHEntityCreator.createDefaultBall();
		ball.setLocation(-2.99f, -1.99f, 0);
		ball.updateGeometricState(0, true);
		
		SHCollisionTask task = new SHCollisionTask("ball", "brick", true);
		processor.addCollisionTask(task);
		
		Node rootNode = mock(Node.class);
		Node bricks = mock(Node.class);
		Node balls = mock(Node.class);
		when(rootNode.getChild("brick")).thenReturn(bricks);
		when(rootNode.getChild("ball")).thenReturn(balls);
		when(bricks.getChildren()).thenReturn(new ArrayList<Spatial>(Arrays.asList(brick1)));
		when(balls.getChildren()).thenReturn(new ArrayList<Spatial>(Arrays.asList(ball)));
		processor.processCollisions(rootNode);
		
		verify(dispatcher, never()).addEvent(any(SHEvent.class));
	}
	
	@Test
	public void testCollisionTriangleSuccess()
	{
		SHEntity brick1 = SHEntityCreator.createDefaultBrick("brick1");
		
		SHBall ball = SHEntityCreator.createDefaultBall();
		ball.setLocation(-2.5f, -1.5f, 0);
		ball.updateGeometricState(0, true);
		
		SHCollisionTask task = new SHCollisionTask("ball", "brick", true);
		processor.addCollisionTask(task);
		
		Node rootNode = mock(Node.class);
		Node bricks = mock(Node.class);
		Node balls = mock(Node.class);
		when(rootNode.getChild("brick")).thenReturn(bricks);
		when(rootNode.getChild("ball")).thenReturn(balls);
		when(bricks.getChildren()).thenReturn(new ArrayList<Spatial>(Arrays.asList(brick1)));
		when(balls.getChildren()).thenReturn(new ArrayList<Spatial>(Arrays.asList(ball)));
		processor.processCollisions(rootNode);
		
		verify(dispatcher).addEvent(any(SHEvent.class));
	}
	
	
}
