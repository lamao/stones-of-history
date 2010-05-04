/* 
 * SHBrekoutEntityFactoryTest.java 04.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author lamao
 *
 */
public class SHBreakoutEntityFactoryTest
{
	private SHBreakoutEntityFactory _factory = new SHBreakoutEntityFactory();
	
	@Test
	public void testBricks()
	{
		
		SHBrick brick = (SHBrick)_factory.createEntity(SHUtils.buildMap(
				"type brick"));		
		assertEquals(1, brick.getStrength());
		assertFalse(brick.isGlass());
		
		brick = (SHBrick)_factory.createEntity(SHUtils.buildMap(
		"type brick|strength 3|glass true"));		
		assertEquals(3, brick.getStrength());
		assertTrue(brick.isGlass());
		
		brick = (SHBrick)_factory.createEntity(SHUtils.buildMap(
		"type brick|strength super|glass false"));		
		assertEquals(Integer.MAX_VALUE, brick.getStrength());
		assertFalse(brick.isGlass());
	}
	
	@Test
	public void testWalls()
	{
		SHEntity wall = _factory.createEntity(SHUtils.buildMap("type wall"));
		assertNotNull(wall);
		
		wall = _factory.createEntity(SHUtils.buildMap("type bottom-wall"));
		assertTrue(wall instanceof SHBottomWall);
		
	}
	
	@Test
	public void testNull()
	{
		assertNull(_factory.createEntity(SHUtils.buildMap("type some-type")));
		assertNull(_factory.createEntity(null));
	}

}
