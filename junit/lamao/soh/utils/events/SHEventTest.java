/* 
 * SHEventTest.java May 26, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.events;

import org.testng.annotations.Test;
import static org.testng.Assert.*;/**
 * @author V_Mishcheryakov
 *
 */
public class SHEventTest
{
	
	@Test
	public void testConstructors()
	{
		SHEvent event = new SHEvent();		
		assertTrue(event.getTime() < 0);
		
		event = new SHEvent("type", "sender", null);
		assertTrue(event.getTime() < 0);
	}

}
