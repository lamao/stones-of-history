/* 
 * SHSceneTest.java 29.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import lamao.junit.common.SHEventTestCase;
import lamao.soh.core.entities.SHBall;

import org.junit.Before;
import org.junit.Test;

import com.jme.bounding.BoundingBox;
import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.shape.Box;

import static org.junit.Assert.*;

/**
 * @author lamao
 *
 */
public class SHSceneTest extends SHEventTestCase
{
	
	private SHScene scene = null;
	
	@Before
	public void setUp()
	{
		super.setUp();
		scene = new SHScene();
	}
	
	@Test
	public void testConstructors()
	{
		assertNotNull(scene.getRootNode());
		assertEquals(0, scene.getEntities().size());
		assertEquals(0, scene.getModels().size());
	}
	
	@Test
	public void testAddRemoveModel()
	{
		scene.addModel("box", new Node("box1"));		
		assertEquals(1, scene.getModels().size());
		assertEquals(1, scene.getModels("box").size());
		assertEquals(1, scene.getRootNode().getChildren().size());
		assertEquals(1, ((Node)scene.getRootNode().getChild("box")).getQuantity());
		
		scene.addModel("box", new Node("box2"));		
		assertEquals(1, scene.getModels().size());
		assertEquals(2, scene.getModels("box").size());
		assertEquals(1, scene.getRootNode().getChildren().size());
		assertEquals(2, ((Node)scene.getRootNode().getChild("box")).getQuantity());
		
		scene.addModel("cone", new Node("cone"));		
		assertEquals(2, scene.getModels().size());
		assertEquals(1, scene.getModels("cone").size());
		assertEquals(2, scene.getRootNode().getChildren().size());
		assertEquals(1, ((Node)scene.getRootNode().getChild("cone")).getQuantity());
		
		scene.removeModel("box", scene.getModels("box").get(0));
		assertEquals(2, scene.getModels().size());
		assertEquals(1, scene.getModels("box").size());
		assertEquals(2, scene.getRootNode().getQuantity());
		assertEquals(1, ((Node)scene.getRootNode().getChild("box")).getQuantity());
		
		scene.removeModel("box", scene.getModels("box").get(0));
		assertEquals(1, scene.getModels().size());
		assertNull(scene.getModels("box"));
		assertEquals(1, scene.getRootNode().getQuantity());
		assertNull(scene.getRootNode().getChild("box"));
		
		scene.removeModel("asdfas", null);
		assertEquals(1, scene.getModels().size());
		assertEquals(1, scene.getModels("cone").size());
		assertEquals(1, scene.getRootNode().getQuantity());
		assertEquals(1, ((Node)scene.getRootNode().getChild("cone")).getQuantity());
	}
	
	@Test
	public void testAddRemoveEntity()
	{
		SHEntity entity = new SHEntity("box", "box1", new Node("box1"));
		Spatial model = null;
		
		scene.addEntity(entity);
		assertEquals(1, scene.getEntities().size());
		assertEquals(1, scene.getEntities("box").size());
		assertEquals(1, scene.getModels("box").size());
		assertEquals(1, scene.getRootNode().getQuantity());
		assertEquals(1, ((Node)scene.getRootNode().getChild("box")).getQuantity());
		model = ((Node)scene.getRootNode().getChild("box")).getChild(0);
		assertSame(model, entity.getRoot());
		
		entity = new SHEntity("box", "box2", new Node("box2"));
		scene.addEntity(entity);
		assertEquals(1, scene.getEntities().size());
		assertEquals(2, scene.getEntities("box").size());
		assertEquals(2, scene.getModels("box").size());
		assertEquals(1, scene.getRootNode().getQuantity());
		assertEquals(2, ((Node)scene.getRootNode().getChild("box")).getQuantity());
		model = ((Node)scene.getRootNode().getChild("box")).getChild(1);
		assertSame(model, entity.getRoot());
		
		
		scene.addEntity(new SHEntity("cone", "cone1", new Node("cone1")));
		assertEquals(2, scene.getEntities().size());
		assertEquals(1, scene.getEntities("cone").size());
		assertEquals(1, scene.getModels("cone").size());
		assertEquals(2, scene.getRootNode().getQuantity());
		assertEquals(1, ((Node)scene.getRootNode().getChild("cone")).getQuantity());
		
		model = scene.getEntities("box").get(0).getRoot();
		scene.removeEntity(scene.getEntities("box").get(0));
		assertEquals(2, scene.getEntities().size());
		assertEquals(1, scene.getEntities("box").size());
		assertEquals(1, scene.getModels("box").size());
		assertEquals(2, scene.getRootNode().getQuantity());
		assertEquals(1, ((Node)scene.getRootNode().getChild("box")).getQuantity());
		
		scene.removeEntity(scene.getEntities("box").get(0));
		assertEquals(1, scene.getEntities().size());
		assertNull(scene.getEntities("box"));
		assertNull(scene.getModels("box"));
		assertEquals(1, scene.getRootNode().getQuantity());
		assertNull(scene.getRootNode().getChild("box"));
		
		scene.removeEntity(new SHEntity("asdfas", null, null));
		assertEquals(1, scene.getEntities().size());
		assertEquals(1, scene.getEntities("cone").size());
		assertEquals(1, scene.getEntities("cone").size());
		assertEquals(1, scene.getRootNode().getQuantity());
		assertEquals(1, ((Node)scene.getRootNode().getChild("cone")).getQuantity());
	}
	
	@Test
	public void testSearchMap()
	{
		SHEntity entity = new SHEntity("type", "name", null);
		
		scene.addEntity(entity);		
		assertSame(entity, scene.getEntity(entity.getRoot()));
		
		scene.removeEntity(entity);
		assertSame(null, scene.getEntity(entity.getRoot()));
	}
	
	
	@Test
	public void testAddRemoveCollisionTask()
	{
		scene.addCollisionTask(new SHCollisionTask("type1", "type2", false));
		assertEquals(1, scene.getCollisionTasks().size());
		
		SHCollisionTask someTask = new SHCollisionTask("type1", "type2", false);
		scene.addCollisionTask(someTask);
		assertEquals(2, scene.getCollisionTasks().size());
		
		scene.addCollisionTask(new SHCollisionTask("type3", "type4", false));
		assertEquals(3, scene.getCollisionTasks().size());
		
		scene.removeCollisionTask(new SHCollisionTask("type1", "type2", true));
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
		assertSame(entity1, counter.lastEvent.params.get("src"));
		assertSame(entity2, counter.lastEvent.params.get("dst"));
		
		// bounding collision
		box2.setLocalRotation(new Quaternion(new float[] {0, 0, FastMath.PI / 4}));
		box2.setLocalTranslation(2, 1.5f, 0);
		box2.updateModelBound();
		scene.getRootNode().updateGeometricState(0, true);
		scene.update(0);
		assertTrue(2 == counter.numEvents.get("scene-collision-type1-type2"));
		assertSame(entity1, counter.lastEvent.params.get("src"));
		assertSame(entity2, counter.lastEvent.params.get("dst"));
		
		// triangle collision
		scene.getCollisionTasks().remove(0);
		scene.addCollisionTask(new SHCollisionTask("type1", "type2", true));
		scene.update(0);
		assertTrue(2 == counter.numEvents.get("scene-collision-type1-type2"));
		assertSame(entity1, counter.lastEvent.params.get("src"));
		assertSame(entity2, counter.lastEvent.params.get("dst"));
		
		box2.setLocalTranslation(2, 1f, 0);
		scene.update(0);
		assertTrue(3 == counter.numEvents.get("scene-collision-type1-type2"));
		assertSame(entity1, counter.lastEvent.params.get("src"));
		assertSame(entity2, counter.lastEvent.params.get("dst"));
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
	public void testGetEntity()
	{
		scene.addEntity(new SHEntity("type", "name", null));
		
		assertNotNull(scene.getEntity("type", "name"));
		assertNull(scene.getEntity("type", "name2"));
		assertNull(scene.getEntity("type2", "name"));
	}
	
	@Test
	public void testReset()
	{
		scene.addEntity(new SHEntity("type", "name", null));
		Box box = new Box("box", new Vector3f(0, 0, 0), 1, 1, 1);
		scene.addModel("type1", box);
		scene.addCollisionTask(new SHCollisionTask("type", "type1", false));
		scene.reset();
		
		assertEquals(1, scene.getCollisionTasks().size());
		assertEquals(0, scene.getEntities().size());
		assertEquals(0, scene.getModels().size());
		assertEquals(0, scene.getRootNode().getQuantity());
		assertNull(scene.getEntity(box));
		
		scene.addEntity(new SHEntity("type", "name", null));
		scene.addModel("type1", box);
		scene.addCollisionTask(new SHCollisionTask("type", "type1", false));
		scene.resetAll();
		
		assertEquals(0, scene.getCollisionTasks().size());
		assertEquals(0, scene.getEntities().size());
		assertEquals(0, scene.getModels().size());
		assertEquals(0, scene.getRootNode().getQuantity());
		assertNull(scene.getEntity(box));
		
	}
}
