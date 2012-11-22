/** 
 * SHEpochInfoTest.java Jun 2, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.model.entity;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


/**
 * @author lamao
 *
 */
public class SHEpochTest
{
	private SHEpoch epoch;
	
	@BeforeMethod
	public void setUp()
	{
		epoch = new SHEpoch();
	}
			
	@Test
	public void testToString()
	{
		epoch.setName("name");
		
		assertEquals(epoch.toString(), "name");
	}
}
