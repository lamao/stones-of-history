/* 
 * SHEventTest.java May 26, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.events;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * @author V_Mishcheryakov
 *
 */
public class SHEventTest
{
	
	@Test
	public void testConstructors()
	{
		SHEvent event = new SHEvent();		
		assertTrue(event.time < 0);
		
		event = new SHEvent("type", "sender", null);
		assertTrue(event.time < 0);
	}

}
