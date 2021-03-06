/* 
 * SHBallTest.java 23.10.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.entities;

import lamao.soh.core.entities.SHBall;

import com.jme3.math.Vector3f;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;
/**
 * @author lamao
 *
 */
public class SHBallTest
{
	@Test
	public void testConstructors()
	{
		SHBall ball = new SHBall();
		assertNotNull(ball);
		assertNull(ball.getModel());
		assertEquals(new Vector3f(0, 0, -1), ball.getVelocity());
		assertFalse(ball.isSuper());
	}

}
