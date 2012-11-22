/** 
 * SHUserServiceTest.java 16.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.service;

import static org.testng.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import lamao.soh.SHConstants;
import lamao.soh.core.model.entity.SHEpoch;
import lamao.soh.core.model.entity.SHLevel;
import lamao.soh.core.model.entity.SHUser;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.thoughtworks.xstream.XStream;
import static org.testng.Assert.*;

/**
 * @author lamao
 *
 */
public class SHUserServiceTest
{
	private final static String PLAYER_NAME = "player";
	
	private static String PLAYERS_DIR = "data/test/players/";
	
	private static String PLAYERS_LIST_DIR = "data/test/test_get_players/";
	
	private static final String NEW_PLAYER = "newplayer";
	
	private SHUserService userService;
	
	SHConstants constants = new SHConstants();
	
	@BeforeMethod
	public void setUp()
	{
		initMocks(this);
		constants.PLAYERS_DIR = PLAYERS_DIR;
		userService = new SHUserService(constants);
	}
	
	private SHUser createDefaultPlayer()
	{
		SHUser player = new SHUser();
		player.setName(PLAYER_NAME);
		
		SHEpoch epoch1 = new SHEpoch();
		epoch1.getLevels().add(new SHLevel());
		epoch1.getLevels().add(new SHLevel());
		
		SHEpoch epoch2 = new SHEpoch();
		epoch2.getLevels().add(new SHLevel());
		epoch2.setName("epoch2");
		
		return player;
	}
	
	private void checkPlayer(SHUser player, SHUser player2)
	{
		assertEquals(player.getName(), player2.getName());
        assertEquals(player.getCompletedLevels().size(), player2.getCompletedLevels().size());
        assertTrue(player.getCompletedLevels().containsAll(player2.getCompletedLevels()));
        assertTrue(player2.getCompletedLevels().containsAll(player.getCompletedLevels()));
	}
	
	@Test
	public void testXmlStorage() throws FileNotFoundException
	{
		SHUser player = createDefaultPlayer();
		
		
		XStream xstream = new XStream();
        xstream.processAnnotations(SHLevel.class);
        xstream.processAnnotations(SHEpoch.class);
        xstream.processAnnotations(SHUser.class);
        
        String xml = xstream.toXML(player);
        SHUser player2 = (SHUser) xstream.fromXML(xml);
        
        checkPlayer(player, player2);
	}
	
	@Test
	public void testSelfSerialization() throws IOException
	{
		SHUser player = createDefaultPlayer();
	
		File file = new File(constants.PLAYERS_DIR + player.getName() + ".xml");
		if (!file.exists())
		{
			file.createNewFile();
		}
		
		userService.save(player);		
		SHUser player2 = userService.load(PLAYER_NAME);
		
		checkPlayer(player, player2);
	}
	
	@Test
	public void testSaveNewFile() throws IOException, InterruptedException
	{
		File file = new File(constants.PLAYERS_DIR + NEW_PLAYER + ".xml");
		if (file.exists())
		{
			file.delete();
		}
		SHUser player = new SHUser();
		player.setName("newplayer");
		
		userService.save(player);
		
		assertTrue(file.exists());
		file.delete();
	}
	
	@Test(expectedExceptions=FileNotFoundException.class)
	public void testLoadNotFound() throws FileNotFoundException
	{
		userService.load("anyname");
	}
	
	@Test
	public void testGetPlayersEmpty()
	{
		constants.PLAYERS_DIR = "some/dir";
		List<SHUser> players = userService.getAll();
		assertEquals(players.size(), 0);
	}
	
	@Test
	public void testGetPlayersSuccess() 
	{
		constants.PLAYERS_DIR = PLAYERS_LIST_DIR;
		List<SHUser> players = userService.getAll();
		assertEquals(players.size(), 3);
	}
	
	@Test
	public void testDeleteNotFound()
	{
		assertFalse(userService.delete("anyuser"));
	}
	
	@Test
	public void testDeleteSuccess() throws IOException
	{
		File file = new File(PLAYERS_DIR + "someplayer.xml");
		file.createNewFile();
		assertTrue(file.exists());
		
		boolean isDeleted = userService.delete("someplayer");
		
		file = new File(PLAYERS_DIR + "someplayer.xml");
		
		assertTrue(isDeleted);
		assertFalse(file.exists());
		
	}
	
	@Test
	public void testIsExistExist() throws IOException
	{
		File file = new File(PLAYERS_DIR + "existentplayer.xml");
		file.createNewFile();
		assertTrue(userService.isExists("existentplayer"));
		file.delete();
	}
	
	@Test
	public void testIsExistNotExist() 
	{
		assertFalse(userService.isExists("asdasha"));
	}
}
