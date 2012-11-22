/** 
 * XmlFileFilterTest.java 22.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.service;

import java.io.File;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author lamao
 *
 */
public class XmlFilenameFilterTest
{
	private XmlFilenameFilter filter;
	
	@BeforeMethod
	public void setUp()
	{
		filter = new XmlFilenameFilter();
	}
	
	@Test
	public void testAcceptSuccess()
	{
		assertTrue(filter.accept(new File("dir"), "somefile.xml"));
	}
	
	@Test
	public void testAcceptFail()
	{
		assertFalse(filter.accept(new File("dir"), "somefile.txt"));
	}


}
