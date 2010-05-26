/* 
 * SHPaddleGunBonusTest.java 12.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import java.util.logging.Level;
import java.util.logging.Logger;

import lamao.soh.SHConstants;
import lamao.soh.core.SHEntityCreator;
import lamao.soh.core.SHGamePack;
import lamao.soh.core.SHPaddle;
import lamao.soh.core.SHBreakoutInputHandler;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;
import lamao.soh.utils.SHResourceManager;

import org.junit.Test;

import com.jme.input.MouseInput;
import com.jme.input.dummy.DummyMouseInput;
import com.jme.math.Vector3f;
import com.jme.scene.shape.Box;
import com.jme.system.DisplaySystem;
import com.jme.system.dummy.DummySystemProvider;

import static org.junit.Assert.*;

/**
 * @author lamao
 *
 */
public class SHPaddleGunBonusTest
{
	static
	{
		DisplaySystem.setSystemProvider(new DummySystemProvider());
		MouseInput.setProvider(DummyMouseInput.class);
		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.OFF);
	}
	
	@Test
	public void testBonus()
	{
		// TODO : Implement this
		SHPaddleGunBonus bonus = new SHPaddleGunBonus();
		assertTrue(Math.abs(bonus.getDuration() - SHPaddleGunBonus.DURATION) < 
				0.001f);
		
		SHResourceManager manager = new SHResourceManager();
		SHGamePack.manager = manager;
		manager.add(SHResourceManager.TYPE_MODEL, SHConstants.PADDLE, 
				new Box(SHConstants.PADDLE,
				new Vector3f(0, 0, 0), 2, 1, 1));
		manager.add(SHResourceManager.TYPE_MODEL, SHConstants.PADDLE_GUN, 
				new Box(SHConstants.PADDLE_GUN,
				new Vector3f(0, 0, 0), 2, 1, 1));
		
		SHPaddle paddle = SHEntityCreator.createDefaultPaddle();
		paddle.setLocation(new Vector3f(2, 3, 4));
		SHGamePack.input = new SHBreakoutInputHandler(paddle);

		SHScene scene = new SHScene();
		scene.addEntity(paddle);
		
		bonus.apply(scene);
		assertEquals(SHConstants.PADDLE_GUN, paddle.getModel().getName());
		assertTrue(paddle.getLocation().toString(),
				SHUtils.areEqual(new Vector3f(2, 3, 4), paddle.getLocation(), 
				0.001f));
		
		bonus.cleanup(scene);
		assertEquals(SHConstants.PADDLE, paddle.getModel().getName());
		assertTrue(SHUtils.areEqual(new Vector3f(2, 3, 4), paddle.getLocation(), 
				0.001f));
		
	}

}
