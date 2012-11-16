/* 
 * SHPlayerInfoTest.java Jun 2, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.model.entity;


import lamao.soh.core.model.entity.SHUser;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * @author lamao
 *
 */
public class SHUserTest
{
	@Test
	public void testToString()
	{
		SHUser player = new SHUser();		
		assertNull(player.toString());
		
		player.setName("name");
		assertEquals("name", player.toString());
	}
}
