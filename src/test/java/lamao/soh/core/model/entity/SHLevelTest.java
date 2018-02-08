/** 
 * SHLevelInfoTest.java Jun 2, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.model.entity;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author lamao
 *
 */
public class SHLevelTest
{
	private SHLevel level;
	
	@BeforeMethod
	public void setUp()
	{
		level = new SHLevel();
	}
			
	@Test
	public void testToString()
	{
		level.setName("name");
		
		assertEquals(level.toString(), "name");
	}
	
}
