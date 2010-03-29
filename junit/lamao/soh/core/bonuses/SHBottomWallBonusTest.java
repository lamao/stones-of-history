/* 
 * SHBottomWallBonusTest.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import static junit.framework.Assert.*;
import lamao.soh.core.SHCoreTestHelper;
import lamao.soh.core.SHLevel;

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
		SHLevel level = SHCoreTestHelper.createDefaultLevel();
		assertTrue(bonus.isAddictive());
		
		level.setBottomWallActive(false);
		bonus.apply(level);
		assertTrue(level.isBottomWallActive());
		bonus.cleanup(level);
		assertFalse(level.isBottomWallActive());
	}
	

}
