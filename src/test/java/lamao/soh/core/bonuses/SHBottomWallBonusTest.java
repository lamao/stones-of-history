/* 
 * SHBottomWallBonusTest.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import lamao.soh.core.SHScene;
import lamao.soh.core.entities.SHBottomWall;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.*;
/**
 * @author lamao
 */
public class SHBottomWallBonusTest
{
	@Mock
	private SHScene scene;
	
	@BeforeMethod
	public void setUp()
	{
		initMocks(this);
	}
	
	@Test
	public void testBonus()
	{
		SHBottomWallBonus bonus = new SHBottomWallBonus();
		
		SHBottomWall wall = new SHBottomWall("bottom-wall", "bottom-wall", null);
		when(scene.getEntity("bottom-wall", "bottom-wall", SHBottomWall.class))
			.thenReturn(wall);
		
		assertTrue(bonus.isAddictive());	
		
		bonus.apply(scene);
		assertTrue(wall.isActive());
		bonus.cleanup(scene);
		assertFalse(wall.isActive());
	}
	

}
