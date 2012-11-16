/** 
 * SHEpochServiceTest.java 16.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.service;

import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;
import lamao.soh.core.SHUtils;
import lamao.soh.core.model.entity.SHEpoch;
import lamao.soh.core.model.entity.SHLevel;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author lamao
 *
 */
public class SHEpochServiceTest
{
	
	private SHEpochService epochService;
	
	@BeforeMethod
	public void setUp()
	{
		epochService = new SHEpochService();
	}

	@Test
	public void testUpdateYears()
	{
		SHEpoch epoch = new SHEpoch();
		
		SHLevel level = new SHLevel();
		level.setYear(1);
		epoch.getLevels().add(level);
		
		level = new SHLevel();
		level.setYear(123);
		epoch.getLevels().add(level);
		
		level = new SHLevel();
		level.setYear(12);
		epoch.getLevels().add(level);
		
		epochService.updateYears(epoch);
		assertTrue(SHUtils.inRange(epoch.getMinYear(), 0.9999999f, 1.00000001f));
		assertTrue(SHUtils.inRange(epoch.getMaxYear(), 122.999999f, 123.0000001f));
		
	}

	@Test
	public void testSortLevels()
	{
		SHLevel l1 = new SHLevel();
		l1.setYear(2);		
		SHLevel l2 = new SHLevel();
		l2.setYear(1);		
		SHLevel l3 = new SHLevel();
		l3.setYear(1.1f);
		
		SHEpoch epoch = new SHEpoch();
		epoch.getLevels().add(l1);
		epoch.getLevels().add(l2);
		epoch.getLevels().add(l3);
		
		epochService.sortLevels(epoch);
		
		assertSame(l2, epoch.getLevels().get(0));
		assertSame(l3, epoch.getLevels().get(1));
		assertSame(l1, epoch.getLevels().get(2));
	}
}
