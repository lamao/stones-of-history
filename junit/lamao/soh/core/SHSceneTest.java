/* 
 * SHSceneTest.java 29.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import lamao.soh.utils.events.SHEventCounter;
import lamao.soh.utils.events.SHEventDispatcher;

import org.junit.Before;
import org.junit.Test;

import com.jme.bounding.BoundingBox;
import com.jme.intersection.CollisionData;
import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.shape.Box;

import static org.junit.Assert.*;

/**
 * @author lamao
 *
 */
public class SHSceneTest
{
	
	private SHScene scene = null;
	private SHEventDispatcher dispatcher = SHEventDispatcher.getInstance();
	private SHEventCounter counter = new SHEventCounter();
	
	@Before
	public void setUp()
	{
		scene = new SHScene();
		dispatcher.reset();
		counter.reset();
		dispatcher.addHandler("all", counter);
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
		assertEquals(1, ((Node)scene.getRootNode().getChild("box"))
				.getChildren().size());
		
		scene.addModel("box", new Node("box2"));		
		assertEquals(1, scene.getModels().size());
		assertEquals(2, scene.getModels("box").size());
		assertEquals(1, scene.getRootNode().getChildren().size());
		assertEquals(2, ((Node)scene.getRootNode().getChild("box"))
				.getChildren().size());
		
		scene.addModel("cone", new Node("cone"));		
		assertEquals(2, scene.getModels().size());
		assertEquals(1, scene.getModels("cone").size());
		assertEquals(2, scene.getRootNode().getChildren().size());
		assertEquals(1, ((Node)scene.getRootNode().getChild("cone"))
				.getChildren().size());
		
		scene.removeModel("box", scene.getModels("box").get(0));
		assertEquals(2, scene.getModels().size());
		assertEquals(1, scene.getModels("box").size());
		assertEquals(2, scene.getRootNode().getChildren().size());
		assertEquals(1, ((Node)scene.getRootNode().getChild("box"))
				.getChildren().size());
		
		scene.removeModel("box", scene.getModels("box").get(0));
		assertEquals(1, scene.getModels().size());
		assertNull(scene.getModels("box"));
		assertEquals(1, scene.getRootNode().getChildren().size());
		assertNull(scene.getRootNode().getChild("box"));
		
		scene.removeModel("asdfas", null);
		assertEquals(1, scene.getModels().size());
		assertEquals(1, scene.getModels("cone").size());
		assertEquals(1, scene.getRootNode().getChildren().size());
		assertEquals(1, ((Node)scene.getRootNode().getChild("cone"))
				.getChildren().size());
	}
	
	@Test
	public void testAddRemoveEntity()
	{
		scene.addEntity(new SHEntity("box", new Node("box1")));
		assertEquals(1, scene.getEntities().size());
		assertEquals(1, scene.getEntities("box").size());
		assertEquals(1, scene.getModels("box").size());
		assertEquals(1, scene.getRootNode().getChildren().size());
		assertEquals(1, ((Node)scene.getRootNode().getChild("box"))
				.getChildren().size());
		
		scene.addEntity(new SHEntity("box", new Node("box2")));
		assertEquals(1, scene.getEntities().size());
		assertEquals(2, scene.getEntities("box").size());
		assertEquals(2, scene.getModels("box").size());
		assertEquals(1, scene.getRootNode().getChildren().size());
		assertEquals(2, ((Node)scene.getRootNode().getChild("box"))
				.getChildren().size());
		
		scene.addEntity(new SHEntity("cone", new Node("cone1")));
		assertEquals(2, scene.getEntities().size());
		assertEquals(1, scene.getEntities("cone").size());
		assertEquals(1, scene.getModels("cone").size());
		assertEquals(2, scene.getRootNode().getChildren().size());
		assertEquals(1, ((Node)scene.getRootNode().getChild("cone"))
				.getChildren().size());
		
		scene.removeEntity(scene.getEntities("box").get(0));
		assertEquals(2, scene.getEntities().size());
		assertEquals(1, scene.getEntities("box").size());
		assertEquals(1, scene.getModels("box").size());
		assertEquals(2, scene.getRootNode().getChildren().size());
		assertEquals(1, ((Node)scene.getRootNode().getChild("box"))
				.getChildren().size());
		
		scene.removeEntity(scene.getEntities("box").get(0));
		assertEquals(1, scene.getEntities().size());
		assertNull(scene.getEntities("box"));
		assertNull(scene.getModels("box"));
		assertEquals(1, scene.getRootNode().getChildren().size());
		assertNull(scene.getRootNode().getChild("box"));
		
		scene.removeEntity(new SHEntity("asdfas", null));
		assertEquals(1, scene.getEntities().size());
		assertEquals(1, scene.getEntities("cone").size());
		assertEquals(1, scene.getEntities("cone").size());
		assertEquals(1, scene.getRootNode().getChildren().size());
		assertEquals(1, ((Node)scene.getRootNode().getChild("cone"))
				.getChildren().size());
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
		scene.addEntity(new SHEntity("type1", box1));
		
		Box box2 = new Box("box2", new Vector3f(0, 0, 0), 1, 1, 1);
		box2.setModelBound(new BoundingBox());
		box2.updateModelBound();
		box2.setLocalTranslation(2, 0, 0);
		scene.addEntity(new SHEntity("type2", box2));
		
		scene.addCollisionTask(new SHCollisionTask("type1", "type2", false));

		// general collision
		scene.getRootNode().updateGeometricState(0, true);
		scene.update(0);
		assertTrue(1 == counter.numEvents.get("scene-collision-type1-type2"));
		
		// bounding collision
		box2.setLocalRotation(new Quaternion(new float[] {0, 0, FastMath.PI / 4}));
		box2.setLocalTranslation(2, 1.5f, 0);
		box2.updateModelBound();
		scene.getRootNode().updateGeometricState(0, true);
		scene.update(0);
		assertTrue(2 == counter.numEvents.get("scene-collision-type1-type2"));
		
		// triangle collision
		scene.getCollisionTasks().remove(0);
		scene.addCollisionTask(new SHCollisionTask("type1", "type2", true));
		scene.update(0);
		assertTrue(2 == counter.numEvents.get("scene-collision-type1-type2"));
		
		box2.setLocalTranslation(2, 1f, 0);
		scene.update(0);
		assertTrue(3 == counter.numEvents.get("scene-collision-type1-type2"));
		
	}
}
