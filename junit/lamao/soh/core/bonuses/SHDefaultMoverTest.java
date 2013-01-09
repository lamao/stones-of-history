/* 
 * SHDefaultMoverTest.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import lamao.soh.core.SHUtils;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

import com.jme.bounding.BoundingBox;
import com.jme.math.Vector3f;
import com.jme.scene.shape.Box;

/**
 * @author lamao
 *
 */
public class SHDefaultMoverTest
{
	@Test
	public void testContstructors()
	{
		Box box = new Box("box", Vector3f.ZERO.clone(), 1, 1, 1);
		box.setModelBound(new BoundingBox());
		box.updateModelBound();
		SHDefaultMover mover = new SHDefaultMover(box);
		
		assertNotNull(mover.getModel());
		assertTrue(SHUtils.areEqual(new Vector3f(0, 0, 1), mover.getVelocity(), 
				0.001f));
		
		mover = new SHDefaultMover();
		assertNull(mover.getModel());
		assertTrue(SHUtils.areEqual(new Vector3f(0, 0, 1), mover.getVelocity(), 
				0.001f));
	}
	
	@Test
	public void testMoving()
	{
		Box box = new Box("box", Vector3f.ZERO.clone(), 1, 1, 1);
		box.setModelBound(new BoundingBox());
		box.updateModelBound();
		SHDefaultMover mover = new SHDefaultMover(box);
		
		mover.update(0.5f);
		assertTrue(SHUtils.areEqual(new Vector3f(0, 0, 0.5f), 
				box.getLocalTranslation(), 0.001f));
	}
}
