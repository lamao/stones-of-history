/** 
 * SHEpochServiceTest.java 16.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.service;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lamao.soh.SHConstants;
import lamao.soh.core.model.SHEpochLevelItem;
import lamao.soh.core.model.entity.SHEpoch;
import lamao.soh.core.model.entity.SHLevel;

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
		assertEquals(epochs.get(0).getId(), "epoch2");
		assertEquals(epochs.get(1).getOrder(), 3.0f);
		assertEquals(epochs.get(1).getId(), "epoch1");
	}
	
	@Test
	public void testGetWrongDir()
	{
		constants.EPOCHS_DIR = "some/dir";
		List<SHEpoch> epochs = epochService.getAll();
		
		assertEquals(epochs.size(), 0);
	}

	@Test
	public void testGetFirstUncompletedEpoch()
	{
		SHEpoch epoch1 = new SHEpoch("e1", null, 0);
		epoch1.getLevels().add(new SHLevel("l1", null, null, null));
		epoch1.getLevels().add(new SHLevel("l2", null, null, null));
		
		SHEpoch epoch2 = new SHEpoch("e2", null, 0);
		epoch2.getLevels().add(new SHLevel("l1", null, null, null));
		epoch2.getLevels().add(new SHLevel("l2", null, null, null));
		
		SHEpoch epoch3 = new SHEpoch("e3", null, 0);
		epoch3.getLevels().add(new SHLevel("l1", null, null, null));
		epoch3.getLevels().add(new SHLevel("l2", null, null, null));
		
		List<SHEpoch> epochs = new ArrayList<SHEpoch>();
		epochs.add(epoch1);
		epochs.add(epoch2);
		epochs.add(epoch3);
		
		Map<String, Set<String>> completedLevels = new HashMap<String, Set<String>>();
		Set<String> firstEpochLevels = new HashSet<String>();
		firstEpochLevels.add("l1");
		firstEpochLevels.add("l2");
		Set<String> secondEpochLevels = new HashSet<String>();
		secondEpochLevels.add("l1");
		Set<String> thirdEpochLevels = new HashSet<String>();
		thirdEpochLevels.add("l1");
		thirdEpochLevels.add("l2");
		completedLevels.put("e1", firstEpochLevels);
		completedLevels.put("e2", secondEpochLevels);
		completedLevels.put("e3", thirdEpochLevels);
		
		SHEpochLevelItem result = epochService.getFirstUncompletedEpoch(epochs, completedLevels);
		
		assertSame(result.getEpoch(), epoch2);
		assertSame(result.getLevel().getId(), "l2");
	}
	
	@Test
	public void testGetFirstUncompletedEpochEmptyCompletedIds()
	{
		SHEpoch epoch1 = new SHEpoch("e1", null, 0);
		epoch1.getLevels().add(new SHLevel("l1", null, null, null));
		epoch1.getLevels().add(new SHLevel("l2", null, null, null));
		
		SHEpoch epoch2 = new SHEpoch("e2", null, 0);
		epoch2.getLevels().add(new SHLevel("l1", null, null, null));
		epoch2.getLevels().add(new SHLevel("l2", null, null, null));
		
		List<SHEpoch> epochs = new ArrayList<SHEpoch>();
		epochs.add(epoch1);
		epochs.add(epoch2);
		
		Map<String, Set<String>> completedLevels = new HashMap<String, Set<String>>();
		
		SHEpochLevelItem result = epochService.getFirstUncompletedEpoch(epochs, completedLevels);
		
		assertSame(result.getEpoch(), epoch1);
		assertSame(result.getLevel().getId(), "l1");
	}
	
	@Test
	public void testGetFirstUncompletedEpochAllCompleted()
	{
		SHEpoch epoch1 = new SHEpoch("e1", null, 0);
		epoch1.getLevels().add(new SHLevel("l1", null, null, null));
		epoch1.getLevels().add(new SHLevel("l2", null, null, null));
		
		SHEpoch epoch2 = new SHEpoch("e2", null, 0);
		epoch2.getLevels().add(new SHLevel("l1", null, null, null));
		epoch2.getLevels().add(new SHLevel("l2", null, null, null));
		
		List<SHEpoch> epochs = new ArrayList<SHEpoch>();
		epochs.add(epoch1);
		epochs.add(epoch2);
		
		Map<String, Set<String>> completedLevels = new HashMap<String, Set<String>>();
		Set<String> firstEpochLevels = new HashSet<String>();
		firstEpochLevels.add("l1");
		firstEpochLevels.add("l2");
		Set<String> secondEpochLevels = new HashSet<String>();
		secondEpochLevels.add("l1");
		secondEpochLevels.add("l2");
		completedLevels.put("e1", firstEpochLevels);
		completedLevels.put("e2", secondEpochLevels);
		
		SHEpochLevelItem result = epochService.getFirstUncompletedEpoch(epochs, completedLevels);
		
		assertNull(result);
	}
	
	@Test
	public void testGetFirstUncompletedLevel()
	{
		List<SHLevel> levels = new ArrayList<SHLevel>();
		levels.add(new SHLevel("l1"));
		levels.add(new SHLevel("l2"));
		levels.add(new SHLevel("l3"));
		
		Set<String> completedIds = new HashSet<String>();
		completedIds.add("l2");
		completedIds.add("l1");
		
		SHLevel result = epochService.getFirstUncompletedLevel(levels, completedIds);
		
		assertEquals(result.getId(), "l3");
	}
	
	@Test
	public void testGetFirstUncompletedLevelNullCompletedIds()
	{
		List<SHLevel> levels = new ArrayList<SHLevel>();
		levels.add(new SHLevel("l1"));
		levels.add(new SHLevel("l2"));
		
		SHLevel result = epochService.getFirstUncompletedLevel(levels, null);
		
		assertEquals(result.getId(), "l1");
	}
	
	@Test
	public void testGetFirstUncompletedLevelNoUncompleted()
	{
		List<SHLevel> levels = new ArrayList<SHLevel>();
		levels.add(new SHLevel("l1"));
		levels.add(new SHLevel("l2"));
		
		Set<String> completedIds = new HashSet<String>();
		completedIds.add("l2");
		completedIds.add("l1");
		
		SHLevel result = epochService.getFirstUncompletedLevel(levels, completedIds);
		
		assertNull(result);
	}
	
	
	
}
