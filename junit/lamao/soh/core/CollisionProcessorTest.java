/* 
 * CollisionProcessorTest.java 16.09.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;
import lamao.soh.core.entities.SHBall;

import org.testng.annotations.Test;

import com.jme.bounding.BoundingBox;
import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.shape.Box;

/**
 * @author lamao
 *
 */
public class CollisionProcessorTest
{
	@Test
	public void testAddCollisionTask()
	{
		scene.addCollisionTask(new SHCollisionTask(TYPE, OTHER_TYPE, false));
		assertEquals(1, scene.getCollisionTasks().size());
		
		SHCollisionTask someTask = new SHCollisionTask(TYPE, OTHER_TYPE, false);
		scene.addCollisionTask(someTask);
		assertEquals(2, scene.getCollisionTasks().size());
	}
	
	@Test
	public void testRemoveCollisionTasks() {
		scene.addCollisionTask(new SHCollisionTask(TYPE, OTHER_TYPE, false));
		
		SHCollisionTask someTask = new SHCollisionTask(TYPE, OTHER_TYPE, false);
		scene.addCollisionTask(someTask);
		scene.addCollisionTask(new SHCollisionTask("type3", "type4", false));
		
		scene.removeCollisionTask(new SHCollisionTask(TYPE, OTHER_TYPE, true));
		assertEquals(2, scene.getCollisionTasks().size());
		
		scene.removeCollisionTask(someTask);
		assertEquals(1, scene.getCollisionTasks().size());
		
		scene.removeCollisionTask(new SHCollisionTask("type3", "type4", false));
		assertEquals(0, scene.getCollisionTasks().size());
	}
	
	@Test
	public void testCollisions()
	{
		Box box1 = new Box("box1", new Vector3f(0, 0, 0), 1, 1, 1);
		box1.setModelBound(new BoundingBox());
		box1.updateModelBound();
		SHEntity entity1 = new SHEntity("type1", "box1", box1);
		scene.addEntity(entity1);
		
		Box box2 = new Box("box2", new Vector3f(0, 0, 0), 1, 1, 1);
		box2.setModelBound(new BoundingBox());
		box2.updateModelBound();
		box2.setLocalTranslation(2, 0, 0);
		SHEntity entity2 = new SHEntity("type2", "box2", box2);
		scene.addEntity(entity2);
		
		scene.addCollisionTask(new SHCollisionTask("type1", "type2", false));

		// general collision
		scene.getRootNode().updateGeometricState(0, true);
		scene.update(0);
		assertEquals(1, counter.getNumEvents("scene-collision-type1-type2"));
		assertSame(entity1, counter.lastEvent.parameters.get("src"));
		assertSame(entity2, counter.lastEvent.parameters.get("dst"));
		
		// bounding collision
		box2.setLocalRotation(new Quaternion(new float[] {0, 0, FastMath.PI / 4}));
		box2.setLocalTranslation(2, 1.5f, 0);
		box2.updateModelBound();
		scene.getRootNode().updateGeometricState(0, true);
		scene.update(0);
		assertTrue(2 == counter.numEvents.get("scene-collision-type1-type2"));
		assertSame(entity1, counter.lastEvent.parameters.get("src"));
		assertSame(entity2, counter.lastEvent.parameters.get("dst"));
		
		// triangle collision
		scene.getCollisionTasks().remove(0);
		scene.addCollisionTask(new SHCollisionTask("type1", "type2", true));
		scene.update(0);
		assertTrue(2 == counter.numEvents.get("scene-collision-type1-type2"));
		assertSame(entity1, counter.lastEvent.parameters.get("src"));
		assertSame(entity2, counter.lastEvent.parameters.get("dst"));
		
		box2.setLocalTranslation(2, 1f, 0);
		scene.update(0);
		assertTrue(3 == counter.numEvents.get("scene-collision-type1-type2"));
		assertSame(entity1, counter.lastEvent.parameters.get("src"));
		assertSame(entity2, counter.lastEvent.parameters.get("dst"));
	}
	
	@Test
	public void testSingleCollision()
	{
		SHEntity brick1 = SHEntityCreator.createDefaultBrick("brick1");
		scene.addEntity(brick1);
		
		SHEntity brick2 = SHEntityCreator.createDefaultBrick("brick2");
		brick2.setLocation(10, 10, 10);
		brick2.getRoot().updateGeometricState(0, true);
		scene.addEntity(brick2);
		
		SHBall ball = SHEntityCreator.createDefaultBall();
		ball.setLocation(0, -1.99f, 0);
		ball.getRoot().updateGeometricState(0, true);
		scene.addEntity(ball);
		
		SHCollisionTask task = new SHCollisionTask("ball", "brick", false);
		scene.addCollisionTask(task);
		
		scene.update(0);
		assertEquals(1, counter.getNumEvents("scene-collision-ball-brick"));
		
		task.checkTris = true;
		scene.update(0);
		assertEquals(2, counter.getNumEvents("scene-collision-ball-brick"));
		
	}
	
	
	
	
	@Test
	public void testReset()
	{
		scene.addEntity(new SHEntity("type", "name", null));
		Box box = new Box("box", new Vector3f(0, 0, 0), 1, 1, 1);
		scene.add("type1", box);
		scene.addCollisionTask(new SHCollisionTask("type", "type1", false));
		scene.reset();
		
		assertEquals(1, scene.getCollisionTasks().size());
		assertEquals(0, scene.getEntities().size());
		assertEquals(0, scene.getAll().size());
		assertEquals(0, scene.getRootNode().getQuantity());
		assertNull(scene.getEntity(box));
		
		scene.addEntity(new SHEntity("type", "name", null));
		scene.add("type1", box);
		scene.addCollisionTask(new SHCollisionTask("type", "type1", false));
		scene.resetAll();
		
		assertEquals(0, scene.getCollisionTasks().size());
		assertEquals(0, scene.getEntities().size());
		assertEquals(0, scene.getAll().size());
		assertEquals(0, scene.getRootNode().getQuantity());
		assertNull(scene.getEntity(box));
		
	}

}
