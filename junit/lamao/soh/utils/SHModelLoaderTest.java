/* 
 * SHModelLoaderTest.java 1.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils;

import java.io.File;

import lamao.soh.ngutils.AbstractJmeTest;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * @author lamao
 *
 */
public class SHModelLoaderTest extends AbstractJmeTest
{
	private final static String TEST_JME = "/data/models/monkey.jme";
	private final static String TEST_OBJ = "/data/models/monkey.obj";
	private final static String TEST_DPS = "/data/models/monkey.dps";
	
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
	public void testLoadDps()
	{
		Spatial model = SHModelLoader.loadDps(SHModelLoaderTest.class
				.getResource(TEST_DPS));
		assertNotNull(model);
		assertTrue(model instanceof Node);
		assertEquals(2, ((Node)model).getChildren().size());
		
		model = SHModelLoader.loadDps(new File(SHModelLoaderTest.class
				.getResource(TEST_DPS).getFile()));
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
		
		model = SHModelLoader.load(new File(SHModelLoader.class
				.getResource(TEST_DPS).getFile()));
		assertNotNull(model);
		assertTrue(model instanceof Node);
		assertEquals(2, ((Node)model).getChildren().size());
	}
	
}
