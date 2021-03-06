/* 
 * SHIncPaddleWidthBonusTest.java 26.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;

import lamao.soh.core.SHEntityCreator;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;
import lamao.soh.core.entities.SHPaddle;


import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * @author lamao
 *
 */
public class SHIncPaddleWidthBonusTest
{
	@Mock
	private SHScene scene;
	
	private SHPaddle paddle;
	
	private SHIncPaddleWidthBonus bonus;
	
	@BeforeMethod
	public void setUp()
	{
		initMocks(this);
		
		bonus = new SHIncPaddleWidthBonus();
		
		paddle = SHEntityCreator.createDefaultPaddle();
		when(scene.getEntity("paddle", "paddle")).thenReturn(paddle);
	}
	
	@Test
	public void testConstructors()
	{
		SHBonus bonus = new SHDecPaddleWidthBonus();
		assertTrue(Math.abs(bonus.getDuration() - 
				SHDecPaddleWidthBonus.DURATION) < 0.001f);
	}
	
	@Test
	public void testBonusApply()
	{
		bonus.apply(scene);		
		Spatial paddleModel = paddle.getModel();
		assertTrue(SHUtils.areEqual(new Vector3f(1.2f, 1, 1), 
				paddleModel.getLocalScale(), 0.001f));
		
		bonus.apply(scene);		
		assertTrue(SHUtils.areEqual(new Vector3f(1.44f, 1, 1), 
				paddleModel.getLocalScale(), 0.001f));
		
	}
	
	@Test
	public void testBonusCleanup()
	{
		Spatial paddleModel = paddle.getModel();
		paddleModel.setLocalScale(new Vector3f(1.44f, 1f, 1f));
		paddle.updateModelBound();
		
		bonus.cleanup(scene);
		assertTrue(SHUtils.areEqual(new Vector3f(1.2f, 1, 1), 
				paddleModel.getLocalScale(), 0.001f));
		
		bonus.cleanup(scene);
		assertTrue(SHUtils.areEqual(new Vector3f(1, 1, 1), 
				paddleModel.getLocalScale(), 0.001f));
		
	}

}
