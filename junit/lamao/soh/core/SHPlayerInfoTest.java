/* 
 * SHPlayerInfoTest.java Jun 2, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.testng.annotations.Test;
import static org.testng.Assert.*;
import com.thoughtworks.xstream.XStream;

/**
 * @author lamao
 *
 */
public class SHPlayerInfoTest
{
	private final static String TEST_PLAYER_FILE = "data/test/players/player.xml";
	
	private SHPlayerInfo createDefaultPlayer()
	{
		SHPlayerInfo player = new SHPlayerInfo();
		player.setLives(5);
		player.setName("ugly player");
		
		SHEpochInfo epoch1 = new SHEpochInfo();
		epoch1.getLevels().add(new SHLevelInfo());
		epoch1.getLevels().add(new SHLevelInfo());
		
		SHEpochInfo epoch2 = new SHEpochInfo();
		epoch2.getLevels().add(new SHLevelInfo());
		epoch2.setName("epoch2");
		
		player.getEpochs().add(epoch1);
		player.getEpochs().add(epoch2);
		player.setCurrentEpoch(epoch2);
		
		return player;
	}
	
	private void checkPlayer(SHPlayerInfo player, SHPlayerInfo player2)
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
		SHPlayerInfo player = createDefaultPlayer();
		
		
		XStream xstream = new XStream();
        xstream.processAnnotations(SHLevelInfo.class);
        xstream.processAnnotations(SHEpochInfo.class);
        xstream.processAnnotations(SHPlayerInfo.class);
        
        String xml = xstream.toXML(player);
        SHPlayerInfo player2 = (SHPlayerInfo) xstream.fromXML(xml);
        
        checkPlayer(player, player2);
	}
	
	@Test
	public void testSelfSerialization() throws FileNotFoundException
	{
		SHPlayerInfo player = createDefaultPlayer();
		
		player.save(new FileOutputStream(new File(TEST_PLAYER_FILE)));		
		SHPlayerInfo player2 = SHPlayerInfo.load(new File(TEST_PLAYER_FILE));
		
		checkPlayer(player, player2);
		
		boolean wasException = false;
		try
		{
			player2 = SHPlayerInfo.load(new File("dummy/file.xml"));
		}
		catch (FileNotFoundException expected)
		{
			wasException = true;
		}
		assertTrue(wasException);
	}
	
	@Test
	public void testUpdateEpochs()
	{
		SHLevelInfo l1 = new SHLevelInfo();
		l1.setYear(3);		
		SHLevelInfo l2 = new SHLevelInfo();
		l2.setYear(1);	
		SHEpochInfo e1 = new SHEpochInfo();
		e1.getLevels().add(l1);
		e1.getLevels().add(l2);
		
		SHLevelInfo l3 = new SHLevelInfo();
		l3.setYear(2);		
		SHLevelInfo l4 = new SHLevelInfo();
		l4.setYear(1);	
		SHEpochInfo e2 = new SHEpochInfo();
		e2.getLevels().add(l3);
		e2.getLevels().add(l4);
		
		SHLevelInfo l5 = new SHLevelInfo();
		l5.setYear(3);		
		SHLevelInfo l6 = new SHLevelInfo();
		l6.setYear(1.5f);	
		SHEpochInfo e3 = new SHEpochInfo();
		e3.getLevels().add(l5);
		e3.getLevels().add(l6);
		
		SHPlayerInfo player = new SHPlayerInfo();
		player.getEpochs().add(e1);
		player.getEpochs().add(e2);
		player.getEpochs().add(e3);
		
		player.updateEpochs();
		
		assertSame(e2, player.getEpochs().get(0));
		assertSame(e1, player.getEpochs().get(1));
		assertSame(e3, player.getEpochs().get(2));
	}
	
	@Test
	public void testToString()
	{
		SHPlayerInfo player = new SHPlayerInfo();		
		assertNull(player.toString());
		
		player.setName("name");
		assertEquals("name", player.toString());
	}
}
