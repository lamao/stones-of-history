/* 
 * SHBallBrickCollisionHandlerTest.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.collisionhandlers;

import static org.junit.Assert.*;
import lamao.junit.common.SHEventTestCase;
import lamao.soh.core.SHEntityCreator;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHBrick;
import lamao.soh.utils.events.SHEvent;

import org.junit.Before;
import org.junit.Test;

import com.jme.math.Vector3f;

/**
 * @author lamao
 *
 */
public class SHBallBrickCollisionHandlerTest extends SHEventTestCase
{
	
	private SHScene scene = null;
	private SHBall ball = null;
	private SHBallBrickCollisionHandler handler = new SHBallBrickCollisionHandler();
	
	@Before
	public void setUp()
	{
		super.setUp();
		scene = new SHScene();
		ball = SHEntityCreator.createDefaultBall();
		scene.addEntity(ball);
		ball.setLocation(0, -1.99f, 0);
		ball.setVelocity(1, 1, 0);
	}

	@Test 
	public void testBallCollisionWithDefaultBrick()
	{
		SHBrick brick = SHEntityCreator.createDefaultBrick("brick");
		scene.addEntity(brick);
		
		handler.processEvent(new SHEvent("ball-brick-collision", scene, 
				SHUtils.buildEventMap("src", ball, "dst", brick)));
		
		
		assertEquals(1, counter.getNumEvents("level-brick-deleted"));
		assertFalse(ball.getVelocity().toString(), 
				SHUtils.areEqual(new Vector3f(1, 1, 0), ball.getVelocity(), 
				0.001f));
		assertNull(scene.getEntities("brick"));
		
	}
	
	@Test 
	public void testBallCollisionWithSuperBrick()
	{
		SHBrick brick = SHEntityCreator.createSuperBrick("brick");
		scene.addEntity(brick);
		
		handler.processEvent(new SHEvent("ball-brick-collision", scene, 
				SHUtils.buildEventMap("src", ball, "dst", brick)));
		
		
		assertEquals(0, counter.getNumEvents("level-brick-deleted"));
		assertFalse(ball.getVelocity().toString(), 
				SHUtils.areEqual(new Vector3f(1, 1, 0), ball.getVelocity(), 
				0.001f));
		assertEquals(1, scene.getEntities("brick").size());
		
	}
	
	@Test 
	public void testBallCollisionWithGlassBrick()
	{
		SHBrick brick = SHEntityCreator.createGlassBrick("brick");
		scene.addEntity(brick);
		
		handler.processEvent(new SHEvent("ball-brick-collision", scene, 
				SHUtils.buildEventMap("src", ball, "dst", brick)));
		
		
		assertEquals(1, counter.getNumEvents("level-brick-deleted"));
		assertTrue(ball.getVelocity().toString(), 
				SHUtils.areEqual(new Vector3f(1, 1, 0), ball.getVelocity(), 
				0.001f));
		assertNull(scene.getEntities("brick"));
		
	}
	
}
