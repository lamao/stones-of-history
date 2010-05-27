/* 
 * SHBallWallCollisionHandlerTest.java 01.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.collisionhandlers;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.*;

import com.jme.math.Vector3f;

import lamao.junit.common.SHEventTestCase;
import lamao.soh.core.SHEntity;
import lamao.soh.core.SHUtils;
import lamao.soh.core.entities.SHBall;
import lamao.soh.utils.events.SHEvent;

/**
 * @author lamao
 *
 */
public class SHBallWallCollisionHandlerTest extends SHEventTestCase
{
	@Test
	public void testHandler()
	{
		SHBallWallCollisionHandler handler = new SHBallWallCollisionHandler();
		SHBall ball = new SHBall(null, new Vector3f(-1, 1, 0));
		SHEntity wall = new SHEntity("wall", "left-wall", null);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("src", ball);
		params.put("dst", wall);
		SHEvent event = new SHEvent("type", null, params);
		handler.processEvent(event);
		
		assertTrue(SHUtils.areEqual(new Vector3f(1, 1, 0), ball.getVelocity(), 0.001f));
		assertTrue(1 == counter.numEvents.get("level-wall-hit"));
		
		wall.setName("top-wall");
		handler.processEvent(event);
		assertTrue(SHUtils.areEqual(new Vector3f(1, -1, 0), ball.getVelocity(), 0.001f));
		assertTrue(2 == counter.numEvents.get("level-wall-hit"));
		
		wall.setName("right-wall");
		handler.processEvent(event);
		assertTrue(SHUtils.areEqual(new Vector3f(-1, -1, 0), ball.getVelocity(), 0.001f));
		assertTrue(3 == counter.numEvents.get("level-wall-hit"));
	}

}
