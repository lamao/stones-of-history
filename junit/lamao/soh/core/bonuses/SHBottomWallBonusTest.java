/* 
 * SHBottomWallBonusTest.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import static junit.framework.Assert.*;
import lamao.soh.core.SHBottomWall;
import lamao.soh.core.SHScene;

import org.junit.Test;

/**
 * @author lamao
 */
public class SHBottomWallBonusTest
{
	@Test
	public void testBonus()
	{
		SHBottomWallBonus bonus = new SHBottomWallBonus();
		SHScene scene = new SHScene();
		
		SHBottomWall wall = new SHBottomWall("bottom-wall", "bottom-wall", null);
		scene.addEntity(wall);
		
		assertTrue(bonus.isAddictive());	
		
		bonus.apply(scene);
		assertTrue(wall.isActive());
		bonus.cleanup(scene);
		assertFalse(wall.isActive());
	}
	

}