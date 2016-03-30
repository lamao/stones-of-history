/** 
 * SHGameContextServiceTest.java 19.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.service;

import java.util.ArrayList;

import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.core.SHScene;
import lamao.soh.core.entities.SHBrick;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.jme3.scene.Spatial;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

/**
 * @author lamao
 *
 */
public class SHGameContextServiceTest
{
	
	private SHGameContextService contextService;
	
	@Mock
	private SHScene scene;
	
	private SHBreakoutGameContext context;
	
	@BeforeMethod
	public void setUp()
	{
		initMocks(this);
		contextService = new SHGameContextService(scene);
		
		context = new SHBreakoutGameContext();
		context.setNumberOfDeletableBricks(0);
	}
	
	@Test
	public void testUpdateNumberDeletableBricksAllDeletable()
	{
		ArrayList<Spatial> bricks = new ArrayList<Spatial>();
		bricks.add(new SHBrick(null, 1, false));
		bricks.add(new SHBrick(null, 1, false));
		bricks.add(new SHBrick(null, 1, false));
		when(scene.get("brick")).thenReturn(bricks);
		
		contextService.updateNumberOfDeletableBricks(context);
		
		assertEquals(context.getNumDeletableBricks(), 3);
	}
	
	@Test
	public void testUpdateNumberDeletableBricksNull()
	{
		contextService.updateNumberOfDeletableBricks(context);
		
		assertEquals(context.getNumDeletableBricks(), 0);
	}
	
	@Test
	public void testUpdateNumberDeletableBricksSuuperBrick()
	{
		ArrayList<Spatial> bricks = new ArrayList<Spatial>();
		bricks.add(new SHBrick(null, 1, false));
		bricks.add(new SHBrick(null, SHBrick.SUPER_STRENGTH, false));
		bricks.add(new SHBrick(null, 1, false));
		when(scene.get("brick")).thenReturn(bricks);
		
		contextService.updateNumberOfDeletableBricks(context);
		
		assertEquals(context.getNumDeletableBricks(), 2);
	}

}
