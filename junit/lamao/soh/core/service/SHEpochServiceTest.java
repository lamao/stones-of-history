/** 
 * SHEpochServiceTest.java 16.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.service;

import static org.testng.Assert.*;

import java.util.List;

import lamao.soh.SHConstants;
import lamao.soh.core.model.entity.SHEpoch;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author lamao
 *
 */
public class SHEpochServiceTest
{
	private static final String EPOCHS_DIR = "data/test/test-get-epochs";
	
	private SHEpochService epochService;
	
	@Mock
	private SHConstants constants;
	
	@BeforeMethod
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
		
		constants.EPOCHS_DIR = EPOCHS_DIR;		
		epochService = new SHEpochService(constants);
	}
	
	@Test
	public void testGetAll()
	{
		List<SHEpoch> epochs = epochService.getAll();
		
		assertEquals(epochs.size(), 2);
		assertEquals(epochs.get(0).getOrder(), 2.0f);
		assertEquals(epochs.get(1).getOrder(), 3.0f);
	}
	
	@Test
	public void testGetWrongDir()
	{
		constants.EPOCHS_DIR = "some/dir";
		List<SHEpoch> epochs = epochService.getAll();
		
		assertEquals(epochs.size(), 0);
	}

}
