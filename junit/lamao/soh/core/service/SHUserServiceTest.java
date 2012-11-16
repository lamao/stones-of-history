/** 
 * SHUserServiceTest.java 16.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.service;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import lamao.soh.core.model.entity.SHEpoch;
import lamao.soh.core.model.entity.SHLevel;
import lamao.soh.core.model.entity.SHUser;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.thoughtworks.xstream.XStream;

/**
 * @author lamao
 *
 */
public class SHUserServiceTest
{
	private final static String TEST_PLAYER_FILE = "data/test/players/player.xml";
	
	private static String PLAYERS_DIR = "data/test/test_get_players/";
	
	private SHUserService userService;
	
	@Mock
	private SHEpochService epochService;
	
	@BeforeMethod
	public void setUp()
	{
		initMocks(this);
		userService = new SHUserService(epochService);
	}
	
	private SHUser createDefaultPlayer()
	{
		SHUser player = new SHUser();
		player.setLives(5);
		player.setName("ugly player");
		
		SHEpoch epoch1 = new SHEpoch();
		epoch1.getLevels().add(new SHLevel());
		epoch1.getLevels().add(new SHLevel());
		
		SHEpoch epoch2 = new SHEpoch();
		epoch2.getLevels().add(new SHLevel());
		epoch2.setName("epoch2");
		
		player.getEpochs().add(epoch1);
		player.getEpochs().add(epoch2);
		player.setCurrentEpoch(epoch2);
		
		return player;
	}
	
	private void checkPlayer(SHUser player, SHUser player2)
	{
		assertEquals(player.getName(), player2.getName());
        assertEquals(player.getLives(), player2.getLives());
        assertEquals(player.getCurrentEpoch().getName(), player2.getCurrentEpoch().getName());
        assertEquals(player.getEpochs().size(), player2.getEpochs().size());
        for (int i = 0; i < player.getEpochs().size(); i++) 
        {
        	assertEquals(player.getEpochs().get(i).getLevels().size(),
        				player2.getEpochs().get(i).getLevels().size());
        }
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
	public void testSelfSerialization() throws FileNotFoundException
	{
		SHUser player = createDefaultPlayer();
		
		userService.save(player, new FileOutputStream(new File(TEST_PLAYER_FILE)));		
		SHUser player2 = userService.load(new File(TEST_PLAYER_FILE));
		
		checkPlayer(player, player2);
		
		boolean wasException = false;
		try
		{
			player2 = userService.load(new File("dummy/file.xml"));
		}
		catch (FileNotFoundException expected)
		{
			wasException = true;
		}
		assertTrue(wasException);
	}
	
	@Test
	public void testGetPlayersNull()
	{
		SHUser[] players = userService.getAll("some/dir");
		assertNull(players);
	}
	
	@Test
	public void testGetPlayersSuccess() 
	{
		SHUser[] players = userService.getAll(PLAYERS_DIR);
		assertEquals(players.length, 3);
	}
	
	@Test
	public void testUpdateEpochs()
	{
		SHEpoch e1 = new SHEpoch();
		e1.setMinYear(1);
		e1.setMaxYear(3);
		
		SHEpoch e2 = new SHEpoch();
		e2.setMinYear(1);
		e2.setMaxYear(2);

		SHEpoch e3 = new SHEpoch();
		e3.setMinYear(1.5f);
		e3.setMaxYear(3);

		
		SHUser player = new SHUser();
		player.getEpochs().add(e1);
		player.getEpochs().add(e2);
		player.getEpochs().add(e3);
		
		userService.updateEpochs(player);
		
		verify(epochService, times(3)).updateYears(any(SHEpoch.class));
		assertSame(e2, player.getEpochs().get(0));
		assertSame(e1, player.getEpochs().get(1));
		assertSame(e3, player.getEpochs().get(2));
	}

}
