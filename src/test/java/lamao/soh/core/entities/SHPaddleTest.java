/* 
 * SHPaddleTest.java 24.03. 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.entities;

import com.jme3.scene.Geometry;
import lamao.soh.core.SHDefaultPaddleHitHandler;
import lamao.soh.core.entities.SHPaddle;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

import com.jme3.math.Vector3f;
import com.jme3.scene.shape.Box;

/**
 * @author lamao
 *
 */
public class SHPaddleTest
{
	@Test
	public void testConstructors()
	{
		SHPaddle paddle = new SHPaddle();
		assertNotNull(paddle);
		assertNull(paddle.getModel());
		assertTrue(paddle.getHitHandler() instanceof SHDefaultPaddleHitHandler);		
		
		Geometry box = new Geometry();
		box.setMesh(new Box(new Vector3f(0, 0, 0), 1, 2, 3));
		paddle = new SHPaddle(box);
		assertNotNull(paddle.getModel());
		
	}
	
}
