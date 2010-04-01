/* 
 * SHModelLoaderTest.java 1.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;
import static org.junit.Assert.*;

import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.system.DisplaySystem;
import com.jme.system.dummy.DummySystemProvider;

/**
 * @author lamao
 *
 */
public class SHModelLoaderTest
{
	private final static String TEST_JME = "/data/models/monkey.jme";
	private final static String TEST_OBJ = "/data/models/monkey.obj";
	
	static 
	{
		DisplaySystem.setSystemProvider(new DummySystemProvider());
		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.OFF);
	}
	
	@Test
	public void testLoadJME()
	{
		Spatial model = SHModelLoader.loadJME(SHModelLoaderTest.class
				.getResource(TEST_JME));
		assertNotNull(model);
		assertTrue(model instanceof Node);
		assertEquals(2, ((Node)model).getChildren().size());
		
		model = SHModelLoader.loadJME(new File(SHModelLoader.class
				.getResource(TEST_JME).getFile()));
		assertNotNull(model);
		assertTrue(model instanceof Node);
		assertEquals(2, ((Node)model).getChildren().size());
		
	}
	
	@Test
	public void testLoadObj()
	{
		Spatial model = SHModelLoader.loadObj(SHModelLoaderTest.class
				.getResource(TEST_OBJ));
		assertNotNull(model);
		assertTrue(model instanceof Node);
		assertEquals(2, ((Node)model).getChildren().size());
		
		model = SHModelLoader.loadObj(new File(SHModelLoader.class
				.getResource(TEST_OBJ).getFile()));
		assertNotNull(model);
		assertTrue(model instanceof Node);
		assertEquals(2, ((Node)model).getChildren().size());
	}
	
	@Test
	public void testLoad()
	{
		Spatial model = SHModelLoader.load(SHModelLoaderTest.class
				.getResource(TEST_JME));
		assertNotNull(model);
		assertTrue(model instanceof Node);
		assertEquals(2, ((Node)model).getChildren().size());
		
		model = SHModelLoader.load(new File(SHModelLoader.class
				.getResource(TEST_OBJ).getFile()));
		assertNotNull(model);
		assertTrue(model instanceof Node);
		assertEquals(2, ((Node)model).getChildren().size());
	}
	
}
