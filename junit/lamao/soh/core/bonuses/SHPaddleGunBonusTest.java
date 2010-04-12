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
import lamao.soh.core.SHLevel;
import lamao.soh.core.SHPaddle;
import lamao.soh.core.SHPaddleInputHandler;
import lamao.soh.core.SHUtils;
import lamao.soh.utils.SHResourceManager;

import org.junit.Test;

import com.jme.input.MouseInput;
import com.jme.input.dummy.DummyMouseInput;
import com.jme.math.Vector3f;
import com.jme.scene.Spatial;
import com.jme.scene.shape.Box;
import com.jme.system.DisplaySystem;
import com.jme.system.dummy.DummySystemProvider;

import static org.junit.Assert.*;

/**
 * @author lamao
 *
 */
public class SHPaddleGunBonusTest implements SHConstants
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
		SHPaddleGunBonus bonus = new SHPaddleGunBonus();
		assertTrue(Math.abs(bonus.getDuration() - SHPaddleGunBonus.DURATION) < 
				0.001f);
		
		
		
		SHResourceManager manager = SHResourceManager.getInstance();
		manager.add(SHResourceManager.TYPE_MODEL, PADDLE, new Box(PADDLE,
				new Vector3f(0, 0, 0), 2, 1, 1));
		manager.add(SHResourceManager.TYPE_MODEL, PADDLE_GUN, new Box(PADDLE_GUN,
				new Vector3f(0, 0, 0), 2, 1, 1));
		
		SHLevel level = new SHLevel();
		SHPaddle paddle = new SHPaddle((Spatial)manager.get(
				SHResourceManager.TYPE_MODEL, PADDLE));
		paddle.setLocation(new Vector3f(2, 3, 4));
		level.setPaddle(paddle);
		SHPaddleInputHandler input = new SHPaddleInputHandler(paddle); 
		level.setInputHandler(input);
		
		bonus.apply(level);
		assertNull(level.getRootNode().getChild(PADDLE));
		assertNotNull(level.getRootNode().getChild(PADDLE_GUN));
		assertEquals(PADDLE_GUN, paddle.getModel().getName());
		assertTrue(paddle.getLocation().toString(),
				SHUtils.areEqual(new Vector3f(2, 3, 4), paddle.getLocation(), 
				0.001f));
		
		bonus.cleanup(level);
		assertNull(level.getRootNode().getChild(PADDLE_GUN));
		assertNotNull(level.getRootNode().getChild(PADDLE));
		assertEquals(PADDLE, paddle.getModel().getName());
		assertTrue(SHUtils.areEqual(new Vector3f(2, 3, 4), paddle.getLocation(), 
				0.001f));
		
	}

}
