/* 
 * SHSceneTest.java 29.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import java.util.Collections;

import lamao.junit.common.SHEventTestCase;

import static org.mockito.Mockito.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.shape.Box;


/**
 * @author lamao
 *
 */
public class SHSceneTest extends SHEventTestCase
{
	private static final String TYPE = "type";
	private static final String NAME = "name";
	private static final String OTHER_TYPE = "type2";
	private static final String OTHER_NAME = "name2";
	
	private SHScene scene = null;
	
	
	
	@BeforeMethod
	public void setUp()
	{
		scene = new SHScene();
	}
	
	@Test
	public void testConstructors()
	{
		assertNotNull(scene.getRootNode());
		assertEquals(0, scene.getNumberOfTypes());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testNumberOfTypes() {
		Node rootNode = mock(Node.class);
		when(rootNode.getChildren()).thenReturn(Collections.EMPTY_LIST);
		scene.setRootNode(rootNode);
		
		int numberOfTypes = scene.getNumberOfTypes();
		
		assertEquals(0, numberOfTypes);
		verify(rootNode, times(2)).getChildren();
	}
	
	@Test
	public void testNumberOfTypesChildreanAreNull() {
		Node rootNode = mock(Node.class);
		when(rootNode.getChildren()).thenReturn(null);
		scene.setRootNode(rootNode);
		
		int numberOfTypes = scene.getNumberOfTypes();
		
		assertEquals(0, numberOfTypes);		
		verify(rootNode).getChildren();
	}
	
	@Test
	public void testGetByType() {
		
		scene.add(TYPE, new Node());
		scene.add(OTHER_TYPE, new Box());
		
		assertEquals(1, scene.get(TYPE).size());
		assertEquals(1, scene.get(OTHER_TYPE).size());
	}
	
	@Test
	public void testGetByTypeAndName() {
		Node node1 = new Node(NAME);		
		scene.add(TYPE, node1);
		Spatial box = new Box(OTHER_NAME);
		scene.add(OTHER_TYPE, box);
		
		assertSame(scene.get(TYPE, NAME), node1);
		assertSame(scene.get(OTHER_TYPE, OTHER_NAME), box);
	}
	
	@Test
	public void testGetEntity() {
		SHEntity entity = new SHEntity();
		entity.setName(NAME);
		scene.add(TYPE, entity);
		scene.add(OTHER_TYPE, new Node(OTHER_NAME));
		
		assertSame(scene.getEntity(TYPE, NAME), entity);
		assertNull(scene.getEntity(OTHER_TYPE, OTHER_NAME));
	}
	
	
	
	@Test
	public void testAdd()
	{
		scene.add(TYPE, new Node(NAME));		
		assertEquals(1, scene.getNumberOfTypes());
		assertEquals(1, scene.get(TYPE).size());
		assertEquals(1, ((Node)scene.getRootNode().getChild(TYPE)).getQuantity());
		
		scene.add(TYPE, new Node(OTHER_NAME));		
		assertEquals(1, scene.getNumberOfTypes());
		assertEquals(2, scene.get(TYPE).size());
		assertEquals(2, ((Node)scene.getRootNode().getChild(TYPE)).getQuantity());
		
		scene.add(OTHER_TYPE, new Node(NAME));		
		assertEquals(2, scene.getNumberOfTypes());
		assertEquals(1, scene.get(OTHER_TYPE).size());
		assertEquals(1, ((Node)scene.getRootNode().getChild(OTHER_TYPE)).getQuantity());
	}
	
	public void testAddEntity() 
	{
		SHEntity entity = new SHEntity(TYPE, NAME, null);
		scene.add(entity);
		
		assertEquals(1, scene.getNumberOfTypes());
		assertEquals(1, scene.get(TYPE).size());
		assertSame(1, scene.getEntity(TYPE, NAME));
	}
	
	@Test
	public void testRemove()
	{
		Spatial model1 = new Node(NAME);
		Spatial model2 = new Node(OTHER_NAME);
		Spatial model3 = new Node(NAME);
		scene.add(TYPE, model1);
		scene.add(TYPE, model2);
		scene.add(OTHER_TYPE, model3);
		
		scene.remove(TYPE, model1);
		assertEquals(2, scene.getNumberOfTypes());
		assertEquals(1, scene.get(TYPE).size());
		assertEquals(1, ((Node)scene.getRootNode().getChild(TYPE)).getQuantity());
		
		scene.remove(TYPE, model2);
		assertEquals(1, scene.getRootNode().getChildren().size());
		assertNull(scene.get(TYPE));
		assertEquals(1, scene.getNumberOfTypes());
		assertNull(scene.getRootNode().getChild(TYPE));
		
		scene.remove("asdfas", null);
		assertEquals(1, scene.getRootNode().getChildren().size());
		assertEquals(1, scene.get(OTHER_TYPE).size());
		assertEquals(1, scene.getNumberOfTypes());
		assertEquals(1, ((Node)scene.getRootNode().getChild(OTHER_TYPE)).getQuantity());
	}
	
	public void testRemoveEntity() 
	{
		SHEntity entity = new SHEntity(TYPE, NAME, null);
		scene.add(entity);
		
		scene.remove(entity);
		
		assertEquals(0, scene.getNumberOfTypes());
		assertEquals(null, scene.get(TYPE));
		assertSame(null, scene.getEntity(TYPE, NAME));
	}
	
	@Test
	public void testUpdate() {
		ISHCollisionProcessor processor = mock(ISHCollisionProcessor.class);
		scene.setCollisionProcessor(processor);
		
		scene.update(1);
		verify(processor).processCollisions(any(Node.class));
	}
}
