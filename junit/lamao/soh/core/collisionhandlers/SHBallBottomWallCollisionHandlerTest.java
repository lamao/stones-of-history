/* 
 * SHBallBottomWallCollisionHandlerTest.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.collisionhandlers;

import org.junit.Test;
import static org.junit.Assert.*;

import lamao.junit.common.SHEventTestCase;
import lamao.soh.core.SHEntityCreator;
import lamao.soh.core.SHGamePack;
import lamao.soh.core.SHScene;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHBottomWall;

/**
 * @author lamao
 *
 */
public class SHBallBottomWallCollisionHandlerTest extends SHEventTestCase
{
	@Test
	public void testHandler()
	{
		SHScene scene = new SHScene();
		SHGamePack.scene = scene;
		SHBall ball = SHEntityCreator.createDefaultBall("ball", "ball");
		SHBottomWall wall = new SHBottomWall("wall", "bottom-wall", null);
		
		scene.addEntity(ball);
		wall.setActive(true);
		
		SHBallBottomWallCollisionHandler handler = new SHBallBottomWallCollisionHandler();
		handler.processEvent(createEvent(null, scene, "src", ball, "dst", wall));
		
		assertEquals(1, counter.getNumEvents("level-wall-hit"));
		assertEquals(1, scene.getEntities("ball").size());
		
		wall.setActive(false);
		handler.processEvent(createEvent(null, scene, "src", ball, "dst", wall));
		assertEquals(1, counter.getNumEvents("level-wall-hit"));
		assertEquals(1, counter.getNumEvents("level-failed"));
		assertNull(scene.getEntities("ball"));
		
	}

}
