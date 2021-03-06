/* 
 * SHSceneTest.java 29.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import java.util.Collections;

import lamao.junit.common.SHEventTestCase;
import lamao.soh.core.entities.SHBall;

import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;


/**
 * @author lamao
 *
 */
@Test(enabled = false)
public class SHSceneTest extends SHEventTestCase
{
	private static final String TYPE = "type";
	private static final String NAME = "name";
	private static final String OTHER_TYPE = "type2";
	private static final String OTHER_NAME = "name2";
	
	private SHScene scene = null;
	
	@Mock
	private ISHCollisionProcessor collisionProcessor;

	
	@BeforeMethod
	public void setUp()
	{
		MockitoAnnotations.initMocks(this);
		scene = new SHScene(collisionProcessor);
	}
	
	@Test(enabled = false)
	public void testConstructors()
	{
		assertNotNull(scene.getRootNode());
		assertEquals(0, scene.getNumberOfTypes());
	}
	
	@SuppressWarnings("unchecked")
	@Test(enabled = false)
	public void testNumberOfTypes() {
		Node rootNode = mock(Node.class);
		when(rootNode.getChildren()).thenReturn(Collections.EMPTY_LIST);
		scene.setRootNode(rootNode);
		
		int numberOfTypes = scene.getNumberOfTypes();
		
		assertEquals(0, numberOfTypes);
		verify(rootNode, times(2)).getChildren();
	}
	
	@Test(enabled = false)
	public void testNumberOfTypesChildreanAreNull() {
		Node rootNode = mock(Node.class);
		when(rootNode.getChildren()).thenReturn(null);
		scene.setRootNode(rootNode);
		
		int numberOfTypes = scene.getNumberOfTypes();
		
		assertEquals(0, numberOfTypes);		
		verify(rootNode).getChildren();
	}
	
	@Test(enabled = false)
	public void testGetByType() {

		scene.add(TYPE, new Node());
		scene.add(OTHER_TYPE, new Node());

		assertEquals(1, scene.get(TYPE).size());
		assertEquals(1, scene.get(OTHER_TYPE).size());
	}
	
	@Test(enabled = false)
	public void testGetEntities() {
		scene.add(TYPE, new SHEntity());
		scene.add(TYPE, new Node());
		scene.add(TYPE, new SHEntity());

		assertEquals(scene.getEntities(TYPE).size(), 2);
	}
	
	@Test(enabled = false)
	public void testGetByTypeAndName() {
		Node node1 = new Node(NAME);
		scene.add(TYPE, node1);
		Spatial box = new Node(OTHER_NAME);
		scene.add(OTHER_TYPE, box);

		assertSame(scene.get(TYPE, NAME), node1);
		assertSame(scene.get(OTHER_TYPE, OTHER_NAME), box);
	}
	
	@Test(enabled = false)
	public void testGetEntity() {
		SHEntity entity = new SHEntity();
		entity.setName(NAME);
		scene.add(TYPE, entity);
		scene.add(OTHER_TYPE, new Node(OTHER_NAME));
		
		assertSame(scene.getEntity(TYPE, NAME), entity);
		assertNull(scene.getEntity(OTHER_TYPE, OTHER_NAME));
	}
	
	@Test(enabled = false)
	public void testGetEntityCasted() {
		SHBall entity = new SHBall();
		entity.setName(NAME);
		scene.add(TYPE, entity);
		scene.add(OTHER_TYPE, new Node(OTHER_NAME));
		
		SHBall actualEntity = scene.getEntity(TYPE, NAME, SHBall.class);
		assertSame(actualEntity, entity);
		assertNull(scene.getEntity(OTHER_TYPE, OTHER_NAME));
	}
	
	
	
	@Test(enabled = false)
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
	
	@Test(enabled = false)
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

	@Test(enabled = false)
	public void testRemoveEntity() 
	{
		SHEntity entity = new SHEntity(TYPE, NAME, null);
		scene.add(entity);
		
		scene.remove(entity);
		
		assertEquals(0, scene.getNumberOfTypes());
		assertEquals(null, scene.get(TYPE));
		assertSame(null, scene.getEntity(TYPE, NAME));
	}
	
	@Test(enabled = false)
	public void testUpdate() {
		ISHCollisionProcessor processor = mock(ISHCollisionProcessor.class);
		scene.setCollisionProcessor(processor);
		
		scene.update(1);
		verify(processor).processCollisions(any(Node.class));
	}
}
