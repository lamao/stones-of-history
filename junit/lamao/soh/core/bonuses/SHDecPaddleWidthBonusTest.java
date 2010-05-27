/* 
 * SHDecPaddleWidthBonusTest.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import static junit.framework.Assert.assertTrue;
import lamao.soh.core.SHEntityCreator;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;
import lamao.soh.core.entities.SHPaddle;

import org.junit.Test;

import com.jme.math.Vector3f;
import com.jme.scene.Spatial;

/**
 * @author lamao
 *
 */
public class SHDecPaddleWidthBonusTest
{
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
		SHScene scene = new SHScene();
		SHBonus bonus = new SHDecPaddleWidthBonus();
		
		SHPaddle paddle = SHEntityCreator.createDefaultPaddle();
		Spatial paddleModel = paddle.getModel();
		scene.addEntity(paddle);
		
		bonus.apply(scene);
		assertTrue(SHUtils.areEqual(new Vector3f(0.8f, 1, 1), 
				paddleModel.getLocalScale(), 0.001f));
		
		bonus.apply(scene);
		assertTrue(SHUtils.areEqual(new Vector3f(0.64f, 1, 1), 
				paddleModel.getLocalScale(), 0.001f));
		
		bonus.cleanup(scene);
		assertTrue(SHUtils.areEqual(new Vector3f(0.8f, 1, 1), 
				paddleModel.getLocalScale(), 0.001f));
		
		bonus.cleanup(scene);
		assertTrue(SHUtils.areEqual(new Vector3f(1, 1, 1), 
				paddleModel.getLocalScale(), 0.001f));
		
	}

}
