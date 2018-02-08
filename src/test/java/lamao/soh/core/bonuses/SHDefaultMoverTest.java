/* 
 * SHDefaultMoverTest.java 27.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import com.jme3.scene.Geometry;
import lamao.soh.core.SHUtils;

import lamao.soh.core.controllers.SHDefaultMover;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;
import com.jme3.scene.shape.Box;

/**
 * @author lamao
 *
 */
public class SHDefaultMoverTest
{
	@Test
	public void testContstructors()
	{
		Geometry box = new Geometry("box");
		box.setMesh(new Box(Vector3f.ZERO.clone(), 1, 1, 1));
		box.setModelBound(new BoundingBox());
		box.updateModelBound();
		SHDefaultMover mover = new SHDefaultMover();
		mover.setSpatial(box);
		
		assertTrue(SHUtils.areEqual(new Vector3f(0, 0, 1), mover.getVelocity(),
				0.001f));
		
		mover = new SHDefaultMover();
		assertNull(mover.getSpatial());
		assertTrue(SHUtils.areEqual(new Vector3f(0, 0, 1), mover.getVelocity(), 
				0.001f));
	}
	
	@Test
	public void testMoving()
	{
		Geometry box = new Geometry();
		box.setMesh(new Box(Vector3f.ZERO.clone(), 1, 1, 1));
		box.setModelBound(new BoundingBox());
		box.updateModelBound();

		SHDefaultMover mover = new SHDefaultMover();
		mover.setSpatial(box);
		
		mover.update(0.5f);
		assertTrue(SHUtils.areEqual(new Vector3f(0, 0, 0.5f), 
				box.getLocalTranslation(), 0.001f));
	}
}
