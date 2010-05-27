/* 
 * SHBrick.java 23.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.entities;

import lamao.soh.core.SHEntityCreator;
import lamao.soh.core.entities.SHBrick;

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
		assertNull(brick.getBonus());
		
		brick = new SHBrick(null);
		assertEquals(1, brick.getStrength());
		assertFalse(brick.isGlass());
	}
	
	@Test
	public void testHit()
	{
		SHBrick brick = SHEntityCreator.createDefaultBrick("brick");
		brick.setStrength(10);
		
		brick.hit();
		assertEquals(9, brick.getStrength());
		
		brick.setGlass(true);
		brick.hit();
		assertEquals(8, brick.getStrength());
		
		brick.setStrength(Integer.MAX_VALUE);
		brick.hit();
		assertEquals(Integer.MAX_VALUE, brick.getStrength());
		
		brick.setGlass(false);
		brick.hit();
		assertEquals(Integer.MAX_VALUE, brick.getStrength());
	}

}
