/* 
 * SHResourceManagerTest.java 1.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lamao.soh.utils.resparser.ISHResourceParser;

import org.junit.Test;

import com.jme.system.DisplaySystem;
import com.jme.system.dummy.DummySystemProvider;

import static org.junit.Assert.*;

/**
 * @author lamao
 *
 */
public class SHResourceManagerTest
{
	private class DummyParser implements ISHResourceParser
	{
		@Override
		public void parse(Map<String, String> args, SHResourceManager manager)
		{
		}
	}
	
	private SHResourceManager manager = SHResourceManager.getInstance();
	private static String line = null;
	private static String line2 = null;
	
	static
	{
		DisplaySystem.setSystemProvider(new DummySystemProvider());
		
		String type = SHResourceManager.TYPE_MODEL;
		String path = SHResourceManagerTest.class
				.getResource("/data/models/monkey.jme").getPath();
		line = "  \ttype =\t" + type + " label=model path='" + path + "'";
		
		type = SHResourceManager.TYPE_TEXTURE;
		path = SHResourceManagerTest.class
			.getResource("/data/textures/bubble.jpg").getPath();
		line2 = "type=" + type + " label=texture1 path='" + path + "'";
	}

	@Test
	public void testConstructor()
	{
		assertNotNull(manager);
		assertEquals(5, manager.getSupportedTypes().size());
		assertEquals(5, manager.getParsers().size());
		assertNotNull(manager.getParser(SHResourceManager.TYPE_MODEL));
	}
	
	@Test
	public void testSetRemoveParser()
	{
		manager.removeParser("Dummy type");
		
		int numParsers = manager.getParsers().size();
		manager.setParser("Dummy type", new DummyParser());
		assertEquals(numParsers + 1, manager.getParsers().size());
		assertNotNull(manager.getParser("Dummy type"));
		assertTrue(manager.getSupportedTypes().contains("Dummy type"));
		
		manager.removeParser("Dummy type");
		assertEquals(numParsers, manager.getParsers().size());
		assertNull(manager.getParser("Dummy type"));
		assertTrue(manager.getSupportedTypes().contains("Dummy type"));
	}
	
	@Test
	public void testAddRemoveResource()
	{
		manager.getSupportedTypes().remove("string");
		
		assertNull(manager.get("string", "1"));
		manager.add("string", "1", "one");
		assertEquals("one", manager.get("string", "1"));
		assertTrue(manager.isSupported("string"));
		
		manager.remove("string", "1");
		assertNull(manager.get("string", "1"));
		assertEquals(0, manager.get("string").size());
		
		manager.add("string", "1", "one");
		manager.add("string", "2", "two");
		assertEquals(2, manager.get("string").size());
		
		manager.remove("string");
		assertEquals(0, manager.get("string").size());
	}
	
	@Test
	public void testLoadOneLine()
	{
		manager.parseOneLine(line);
		assertEquals(1, manager.get(SHResourceManager.TYPE_MODEL).size());
		assertNotNull(manager.get(SHResourceManager.TYPE_MODEL, "model"));
	}
	
	@Test
	public void testParseConfig()
	{
		manager.getConfig().clear();
		manager.remove(SHResourceManager.TYPE_MODEL);

		List<String> config = new LinkedList<String>();
		config.add("#comment line");
		config.add(line);
		config.add(line2);			

		manager.parseConfig(config);
		assertEquals(1, manager.get(SHResourceManager.TYPE_MODEL).size());
		assertEquals(1, manager.get(SHResourceManager.TYPE_TEXTURE).size());
	}
}
