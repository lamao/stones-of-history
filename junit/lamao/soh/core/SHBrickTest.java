/* 
 * SHBrick.java 23.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 
 * @author lamao
 *
 */
public class SHBrickTest
{
	@Test
	public void testConstructors()
	{
		SHBrick brick = new SHBrick();
		assertNotNull(brick);
		assertEquals(1, brick.getStrength());
		assertFalse(brick.isGlass());
		
		brick = new SHBrick(null);
		assertEquals(1, brick.getStrength());
		assertFalse(brick.isGlass());
	}

}
