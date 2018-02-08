/* 
 * SHPaddleGunBonusTest.java 12.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import lamao.soh.SHConstants;
import lamao.soh.core.SHEntityCreator;
import lamao.soh.core.SHScene;
import lamao.soh.core.entities.SHPaddle;
import lamao.soh.ngutils.AbstractJmeTest;
//import lamao.soh.utils.SHResourceManager;


//import com.jme3.input.InputHandler;
//import com.jme3.input.action.MouseInputAction;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.*;
import static org.mockito.Mockito.*;
/**
 * @author lamao
 *
 */
public class SHPaddleGunBonusTest extends AbstractJmeTest
{
//	@Mock
//	private SHScene scene;
//
//	@Mock
//	private SHResourceManager manager;
//	@Mock
//	private Spatial paddleModel = mock(Spatial.class);
//	@Mock
//	private Spatial paddleGunModel = mock(Spatial.class);
//	@Mock
//	private InputHandler inputHandler;
//
//	private SHPaddleGunBonus bonus;
//
//	private SHPaddle paddle;
//
//	@BeforeMethod
//	public void setUp()
//	{
//		initMocks(this);
//		bonus = new SHPaddleGunBonus(paddleModel, inputHandler, manager);
//
//		when(manager.get(SHResourceManager.TYPE_MODEL, SHConstants.PADDLE))
//				.thenReturn(paddleModel);
//		when(manager.get(SHResourceManager.TYPE_MODEL, SHConstants.PADDLE_GUN))
//				.thenReturn(paddleGunModel);
//
//		paddle = SHEntityCreator.createDefaultPaddle();
//		paddle.setLocation(new Vector3f(2, 3, 4));
//
//		when(scene.getEntity("paddle", "paddle", SHPaddle.class)).thenReturn(paddle);
//	}
//
//	@Test
//	public void testConstructors()
//	{
//		assertTrue(Math.abs(bonus.getDuration() - SHPaddleGunBonus.DEFAULT_DURATION) <
//				0.001f);
//	}
//
//	@Test
//	public void testBonusApply()
//	{
//		bonus.apply(scene);
//		verify(scene).getEntity("paddle", "paddle", SHPaddle.class);
//		verify(inputHandler).addAction(any(MouseInputAction.class));
//		assertSame(paddle.getModel(), paddleGunModel);
//	}
//
//	public void testBonusCleanup()
//	{
//		bonus.apply(scene);
//		bonus.cleanup(scene);
//		verify(scene).getEntity("paddle", "paddle", SHPaddle.class);
//		verify(inputHandler).removeAction(any(MouseInputAction.class));
//		assertSame(paddle.getModel(), paddleModel);
//
//	}

}
