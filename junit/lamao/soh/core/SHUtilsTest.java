/* 
 * SHUtils.java 23.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import static org.junit.Assert.*;
import org.junit.Test;

import com.jme.math.Vector3f;

/**
 * @author lamao
 *
 */
public class SHUtilsTest
{
	@Test
	public void testVectorComparsion()
	{
		Vector3f v1 = new Vector3f(0.1f, -0.2f, -0.2f);
		Vector3f v2 = new Vector3f(0.2f, -0.1f, -0.3f);
		assertTrue(SHUtils.areEqual(v1, v2 , 0.11f));
		assertFalse(SHUtils.areEqual(v1, v2, 0.1f));
		
		v2.x = 0;
		assertTrue(SHUtils.areEqual(v1, v2 , 0.11f));
		assertFalse(SHUtils.areEqual(v1, v2, 0.1f));
		
		v1.x = 0.05f;
		v2.x = -0.05f;
		assertTrue(SHUtils.areEqual(v1, v2 , 0.11f));
		assertFalse(SHUtils.areEqual(v1, v2, 0.1f));
		
		v1 = new Vector3f(0, 0, 0);
		v2 = new Vector3f(-0.0f, -0.0f, -0.0f);
		assertTrue(SHUtils.areEqual(v1, v2 , 0.000001f));
		assertFalse(SHUtils.areEqual(v1, v2, 0.0f));
	}
	
	@Test
	public void testInRange()
	{
		assertTrue(SHUtils.inRange(1.2f, 1.1f, 1.3f));
		assertTrue(SHUtils.inRange(1.1f, 1.1f, 1.3f));
		assertTrue(SHUtils.inRange(1.3f, 1.1f, 1.3f));
		assertFalse(SHUtils.inRange(1.2f, 1.21f, 1.3f));
		
		assertTrue(SHUtils.inRange(1.2f, 1.3f, 1.1f));
		assertTrue(SHUtils.inRange(1.1f, 1.3f, 1.1f));
		assertTrue(SHUtils.inRange(1.3f, 1.3f, 1.1f));
		assertFalse(SHUtils.inRange(1.2f, 1.3f, 1.21f));
		
		assertTrue(SHUtils.inRange(-1.2f, -1.1f, -1.3f));
		assertTrue(SHUtils.inRange(-1.1f, -1.1f, -1.3f));
		assertTrue(SHUtils.inRange(-1.3f, -1.1f, -1.3f));
		assertFalse(SHUtils.inRange(-1.2f, -1.21f, -1.3f));
		
		assertTrue(SHUtils.inRange(-1.2f, -1.3f, -1.1f));
		assertTrue(SHUtils.inRange(-1.1f, -1.3f, -1.1f));
		assertTrue(SHUtils.inRange(-1.3f, -1.3f, -1.1f));
		assertFalse(SHUtils.inRange(-1.2f, -1.3f, -1.21f));

	}
}
