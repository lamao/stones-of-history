/* 
 * SHEntityTest.java 24.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;


import org.junit.Test;

import com.jme.bounding.BoundingBox;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.shape.Box;

import static org.junit.Assert.*;

/**
 * @author lamao
 *
 */
public class SHEntityTest
{
	@Test
	public void testConstructors()
	{
		SHEntity entity = new SHEntity();
		assertNotNull(entity);
		assertNotNull(entity.getRoot());
		assertNull(entity.getModel());
		assertEquals("default", entity.getType());
		assertEquals(null, entity.getName());
		
		entity = new SHEntity("type", "name", new Node("model"));
		assertNotNull(entity.getRoot());
		assertEquals(1, entity.getRoot().getQuantity());
		assertNotNull(entity.getModel());
		assertEquals("type", entity.getType());
		assertEquals("name", entity.getName());
		assertEquals("name", entity.getRoot().getName());
	}
	
	@Test
	public void testSetName()
	{
		SHEntity entity = new SHEntity();
		
		entity.setName("name");		
		assertEquals("name", entity.getName());
		assertEquals("name", entity.getRoot().getName());
		
		entity.setName("other");		
		assertEquals("other", entity.getName());
		assertEquals("other", entity.getRoot().getName());
	}
	
	@Test
	public void testSetModel()
	{
		SHEntity entity = new SHEntity();
		Node node = new Node("node");
		Node node2 = new Node("node2");
		
		entity.setModel(node);
		assertEquals(1, entity.getRoot().getQuantity());
		assertSame(node, entity.getModel());
		
		entity.setModel(node2);
		assertEquals(1, entity.getRoot().getQuantity());
		assertSame(node2, entity.getModel());
		
		entity.setModel(null);
		assertEquals(0, entity.getRoot().getQuantity());
		assertNull(entity.getModel());
	}
	
	@Test
	public void testMoving()
	{
		Box box = new Box("entity", Vector3f.ZERO.clone(), 3, 2 ,1);
		box.setModelBound(new BoundingBox());
		box.updateModelBound();
		
		SHEntity entity = new SHEntity(box);
		entity.setLocation(new Vector3f(0, 2, 0));
		assertEquals(new Vector3f(0, 2, 0), entity.getLocation());
		assertEquals(new Vector3f(0, 2, 0), entity.getRoot().getLocalTranslation());
		assertSame(box, entity.getModel());
		
	}
	
}
