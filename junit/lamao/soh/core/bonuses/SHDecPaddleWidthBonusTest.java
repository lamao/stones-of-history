/* 
 * SHDecPaddleWidthBonusTest.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import static junit.framework.Assert.assertTrue;
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
public class SHDecPaddleWidthBonusTest
{
private SHLevel sharedLevel = SHCoreTestHelper.createDefaultLevel();
	
	@Test
	public void testConstructors()
	{
		SHBonus bonus = new SHDecPaddleWidthBonus();
		assertTrue(Math.abs(bonus.getDuration() - 
				SHDecPaddleWidthBonus.DURATION) < 0.001f);
	}
	
	@Test
	public void testBonus()
	{
		SHBonus bonus = new SHDecPaddleWidthBonus();
		Spatial paddleModel = sharedLevel.getPaddle().getModel();
		
		bonus.apply(sharedLevel);
		assertTrue(SHUtils.areEqual(new Vector3f(0.9f, 1, 1), 
				paddleModel.getLocalScale(), 0.001f));
		
		bonus.cleanup(sharedLevel);
		assertTrue(SHUtils.areEqual(new Vector3f(1, 1, 1), 
				paddleModel.getLocalScale(), 0.001f));
		
	}

}
