/* 
 * SHPlayerInfoTest.java Jun 2, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.model.entity;


import java.util.HashSet;

import lamao.soh.core.model.entity.SHUser;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * @author lamao
 *
 */
public class SHUserTest
{
	private static final String LEVEL_ID = "levelId";
	private static final String EPOCH_ID = "epochId";
	
	private SHUser player;
	
	@BeforeMethod
	public void setUp() {
		player = new SHUser();
	}
	
	@Test
	public void testToString()
	{
		assertNull(player.toString());
		
		player.setName("name");
		assertEquals("name", player.toString());
	}
	
	@Test
	public void testAddCompletedLevelNoEpoch() {
		player.addCompletedLevel(EPOCH_ID, LEVEL_ID);
		
		assertTrue(player.getCompletedLevels().get(EPOCH_ID).contains(LEVEL_ID));
	}
	
	@Test
	public void testAddCompletedLevel() {
		player.getCompletedLevels().put(EPOCH_ID, new HashSet<String>());
		
		player.addCompletedLevel(EPOCH_ID, LEVEL_ID);
		
		assertTrue(player.getCompletedLevels().get(EPOCH_ID).contains(LEVEL_ID));
	}
	
	@Test
	public void testAddCompletedLevelDuplicate() {
		player.getCompletedLevels().put(EPOCH_ID, new HashSet<String>());
		
		player.addCompletedLevel(EPOCH_ID, LEVEL_ID);
		player.addCompletedLevel(EPOCH_ID, LEVEL_ID);
		
		assertEquals(player.getCompletedLevels().get(EPOCH_ID).size(), 1);
	}
}
