/* 
 * SHIncPaddleWidthBonusTest.java 26.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import static junit.framework.Assert.*;
import lamao.soh.core.SHCoreTestHelper;
import lamao.soh.core.SHLevel;
import lamao.soh.core.SHUtils;

import org.junit.Test;

import com.jme.math.Vector3f;
import com.jme.scene.Spatial;

/**
 * @author lamao
 *
 */
public class SHIncPaddleWidthBonusTest
{
	private SHLevel sharedLevel = SHCoreTestHelper.createDefaultLevel();
	
	@Test
	public void testConstructors()
	{
		SHBonus bonus = new SHIncPaddleWidthBonus();
		assertTrue(Math.abs(bonus.getDuration() - 
				SHIncPaddleWidthBonus.DURATION) < 0.001f);
	}
	
	@Test
	public void testBonus()
	{
		SHBonus bonus = new SHIncPaddleWidthBonus();
		Spatial paddleModel = sharedLevel.getPaddle().getModel();
		
		bonus.apply(sharedLevel);
		assertTrue(SHUtils.areEqual(new Vector3f(1.1f, 1, 1), 
				paddleModel.getLocalScale(), 0.001f));
		
		bonus.cleanup(sharedLevel);
		assertTrue(SHUtils.areEqual(new Vector3f(1, 1, 1), 
				paddleModel.getLocalScale(), 0.001f));
		
	}

}
