/* 
 * SHResourceManagerTest.java 1.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lamao.soh.ngutils.AbstractJmeTest;
import lamao.soh.utils.resparser.ISHResourceParser;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;


/**
 * @author lamao
 *
 */
public class SHResourceManagerTest extends AbstractJmeTest 
{
	private class DummyParser implements ISHResourceParser
	{
		@Override
		public void parse(Map<String, String> args, SHResourceManager manager)
		{
		}
	}
	
	private SHResourceManager manager = null;
	private String line = null;
	private String line2 = null;
	
	@BeforeMethod
	public void setUp()
	{
		String type = SHResourceManager.TYPE_MODEL;
		String path = SHResourceManagerTest.class
				.getResource("/data/models/monkey.jme").getPath();
		line = "  \ttype =\t" + type + " label=model path='" + path + "'";
		
		type = SHResourceManager.TYPE_TEXTURE;
		path = SHResourceManagerTest.class
			.getResource("/data/textures/bubble.jpg").getPath();
		line2 = "type=" + type + " label=texture1 path='" + path + "'";
		
	}
	
	@BeforeMethod
	public void setupTest() 
	{
		manager = new SHResourceManager();
	}

	@Test
	public void testConstructor()
	{
		assertNotNull(manager);
		assertEquals(5, manager.getParsers().size());
		assertNotNull(manager.getParser(SHResourceManager.TYPE_MODEL));
		assertNotNull(manager.getParser(SHResourceManager.TYPE_TEXTURE));
		assertNotNull(manager.getParser(SHResourceManager.TYPE_SOUND));
		assertNotNull(manager.getParser(SHResourceManager.TYPE_TTFONT));
		assertNotNull(manager.getParser(SHResourceManager.TYPE_BMFONT));
		
		assertNull(manager.get(SHResourceManager.TYPE_MODEL));
		assertNull(manager.get(SHResourceManager.TYPE_TEXTURE));
		assertNull(manager.get(SHResourceManager.TYPE_SOUND));
		assertNull(manager.get(SHResourceManager.TYPE_TTFONT));
		assertNull(manager.get(SHResourceManager.TYPE_BMFONT));
	}
	
	@Test
	public void testSetRemoveParser()
	{
		int numParsers = manager.getParsers().size();
		manager.setParser("Dummy type", new DummyParser());
		assertEquals(numParsers + 1, manager.getParsers().size());
		assertNotNull(manager.getParser("Dummy type"));
		assertNull(manager.get("Dummy type"));
		
		manager.removeParser("Dummy type");
		assertEquals(numParsers, manager.getParsers().size());
		assertNull(manager.getParser("Dummy type"));
		assertNull(manager.get("Dummy type"));
	}
	
	@Test
	public void testAddRemoveResource()
	{
		assertNull(manager.get("string", "1"));
		manager.add("string", "1", "one");
		assertEquals("one", manager.get("string", "1"));
		assertNotNull(manager.get("string"));
		
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
		List<String> config = new LinkedList<String>();
		config.add("#comment line");
		config.add(line);
		config.add(line2);			

		manager.parseConfig(config);
		assertEquals(1, manager.get(SHResourceManager.TYPE_MODEL).size());
		assertEquals(1, manager.get(SHResourceManager.TYPE_TEXTURE).size());
	}
	
	@Test
	public void testLoadAll() throws FileNotFoundException
	{
		manager.loadAll(new File("data/model_test.txt"));
		
		assertEquals(9, manager.get(SHResourceManager.TYPE_MODEL).size());
		
		manager.loadAll(new FileInputStream(new File("data/model_test2.txt")));
		assertEquals(10, manager.get(SHResourceManager.TYPE_MODEL).size());
	}
}
