/* 
 * SHEntityTest.java 24.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;


import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import com.jme.bounding.BoundingBox;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.shape.Box;
import static org.mockito.Mockito.*;


/**
 * @author lamao
 *
 */
public class SHEntityTest
{
	@Mock
	private Node root;
	@Mock
	private Node model;
	
	private SHEntity entity;
	
	@BeforeTest
	public void initalizeMocks() {
		MockitoAnnotations.initMocks(this);
		entity = new SHEntity();
		entity.setRoot(root);
		
		when(root.attachChild(any(Spatial.class))).thenReturn(1);
		
	}
	
	
	@BeforeMethod
	public void setUp() {
		
	}
	
	
	@Test
	public void testConstructorDefault()
	{
		SHEntity entity = new SHEntity();
		assertNotNull(entity);
		assertNull(entity.getRoot());
		assertNull(entity.getModel());
		assertEquals("default", entity.getType());
		assertEquals(null, entity.getName());
	}
	
	@Test
	public void testConstructorWithModel()
	{
		SHEntity entity = new SHEntity(model);
		entity.setRoot(root);
		assertNotNull(entity);
		assertNotNull(entity.getRoot());
		assertSame(entity.getModel(), model);
		assertEquals("default", entity.getType());
		assertEquals(null, entity.getName());
	}
	
	@Test
	public void testParametrizedContstructor() {
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
		
		entity = new SHEntity(box);
		entity.setLocation(new Vector3f(0, 2, 0));
		assertEquals(new Vector3f(0, 2, 0), entity.getLocation());
		assertEquals(new Vector3f(0, 2, 0), entity.getRoot().getLocalTranslation());
		assertSame(box, entity.getModel());
		
	}
	
}
