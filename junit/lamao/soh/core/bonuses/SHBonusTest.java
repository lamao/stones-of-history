/* 
 * SHBonusTest.java 29.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import org.junit.Before;
import org.junit.Test;

import com.jme.bounding.BoundingBox;
import com.jme.math.Vector3f;
import com.jme.scene.shape.Box;

import static org.junit.Assert.*;

import lamao.soh.core.SHBall;
import lamao.soh.core.SHBrick;
import lamao.soh.core.SHCoreTestHelper;
import lamao.soh.core.SHLevel;
import lamao.soh.utils.events.SHEventCounter;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 *
 */
public class SHBonusTest
{
	
	private class SHStubBonus extends SHBonus
	{
		public int applied = 0;
		public int cleanuped = 0;
		
		public SHStubBonus(String id)
		{
			Box model = new Box(id, new Vector3f(0, 0, 0), 
					0.25f, 0.25f, 0.25f);
			model.setModelBound(new BoundingBox());
			model.updateModelBound();
			setModel(model);
		}
		
		@Override
		public void apply(SHLevel level)
		{
			applied++;
		}
		
		@Override
		public void cleanup(SHLevel level)
		{
			cleanuped++;
		}
	}
	
	SHLevel level;
	SHBrick brick;
	SHBall ball;
	SHStubBonus bonus;
	SHEventCounter counter = new SHEventCounter();
	
	@Before
	public void setUp()
	{
		level = SHCoreTestHelper.createDefaultLevel();
		brick = level.getBricks().get(0);
		ball = level.getBalls().get(0);
		
		bonus = new SHStubBonus("1");
		bonus.setDuration(5);
		level.getBonuses().put(brick, bonus);
		
		counter.reset();
		SHEventDispatcher.getInstance().reset();
		SHEventDispatcher.getInstance().addHandler("all", counter);
	}
	
	@Test
	public void testDefaultBonus()
	{
		ball.setLocation(-5, -1.99f, 0);
		level.getPaddle().setLocation(-5, -3, 0);
		level.update(0);		
		assertEquals(0, level.getActiveBonuses().size());
		
		level.getRootNode().updateGeometricState(3, true);
		level.update(3);
		assertEquals(1, level.getActiveBonuses().size());
		assertEquals(1, bonus.applied);
		
		level.update(4.95f);
		assertEquals(1, level.getActiveBonuses().size());
		assertEquals(0, bonus.cleanuped);
		
		level.update(0.1f);
		assertEquals(0, level.getActiveBonuses().size());
		assertEquals(1, bonus.cleanuped);
	}
	
	@Test
	public void testAddictiveBonus()
	{
		bonus.setAddictive(true);
		
		SHStubBonus extraBonus = new SHStubBonus("2");
		extraBonus.setDuration(5);
		extraBonus.setAddictive(true);
		level.getBonuses().put(level.getBricks().get(1), extraBonus);
		
		// extract first bonus
		ball.setLocation(-5, -1.99f, 0);
		level.getPaddle().setLocation(-5, -3, 0);
		level.update(0);	
		
		//activate bonus
		level.getRootNode().updateGeometricState(3, true);
		level.update(3);
		
		// extract second bonus
		ball.setLocation(0, -1.99f, 0);
		level.getPaddle().setLocation(0, -3, 0);
		level.update(0);
		
		// activate second bonus
		level.getRootNode().updateGeometricState(3, true);
		level.update(3);
		
		assertEquals(1, level.getActiveBonuses().size());
		assertTrue(Float.toString(bonus.getDuration()), 
				Math.abs(bonus.getDuration() - 7 ) < 0.001f);
		assertTrue(1 == counter.numEvents.get("level-bonus-activated"));
		
		level.getRootNode().updateGeometricState(7, true);
		level.update(7);
		
		assertEquals(0, level.getActiveBonuses().size());
		assertTrue(Math.abs(bonus.getDuration()) < 0.001f);
		
	}

}
