/** 
 * SHSessionServiceTest.java 26.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.service;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import lamao.soh.core.SHSessionInfo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author lamao
 *
 */
public class SHSessionServiceTest
{
	private static final String SESSION_FILE = "data/test/session/session.xml";
	
	private SHSessionService service;
	
	@BeforeMethod
	public void setUp()
	{
		service = new SHSessionService(SESSION_FILE);
		Logger.getLogger("").setLevel(Level.OFF);
	}
	
	@Test
	public void testGetSession()
	{
		SHSessionInfo session = service.getSessionInfo();
		
		assertEquals(session.getLastSelectedUsername(), "profile");
	}
	
	@Test
	public void testGetSessionNoFile()
	{
		File file = new File("data/test/session/no-session.xml");
		if (file.exists())
		{
			file.delete();
		}
		service = new SHSessionService(file.getAbsolutePath());
		SHSessionInfo session = service.getSessionInfo();
		assertNotNull(session);
		assertNull(session.getLastSelectedUsername());
	}
	
	@Test
	public void testSave()
	{
		service = new SHSessionService("data/test/session/new-session.xml");
		assertTrue(service.saveSessionInfo());
	}
	
}
