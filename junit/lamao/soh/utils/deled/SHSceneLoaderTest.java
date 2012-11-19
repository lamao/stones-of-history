/* 
 * SHSceneLoaderTest.java 03.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.deled;

import java.io.File;

import lamao.soh.core.ISHCollisionProcessor;
import lamao.soh.core.SHBreakoutEntityFactory;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;
import lamao.soh.core.entities.SHBrick;
import lamao.soh.ngutils.AbstractJmeTest;
import lamao.soh.utils.SHResourceManager;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import com.jme.input.InputHandler;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author lamao
 *
 */
public class SHSceneLoaderTest extends AbstractJmeTest
{
	@Mock
	private ISHCollisionProcessor collisionProcessor;
	@Mock
	private InputHandler inputHandler;
	@Mock
	private SHResourceManager manager;
	
	private SHBreakoutEntityFactory entityFactory;
	
	@BeforeMethod
	public void setUp()
	{
		initMocks(this);
		entityFactory = new SHBreakoutEntityFactory(manager);
		
	}
	
	@Test
	public void testLoading()
	{
		when(manager.get(SHResourceManager.TYPE_MODEL, "double-ball"))
			.thenReturn(new Node("ball"));
		SHScene scene = new SHScene(collisionProcessor, inputHandler);
		SHSceneLoader loader = new SHSceneLoader(scene, entityFactory);
		
		loader.load(new File("data/test/test-level.dps"));
		
		//assertNull(scene.getEntities("decoration"));
		assertEquals(2, scene.get("decoration").size());
		assertEquals(3, scene.get("wall").size());
		assertEquals(3, scene.get("wall").size());
		assertEquals(1, scene.get("bottom-wall").size());
		assertEquals(15, scene.get("brick").size());
		
		SHBrick brick = (SHBrick)scene.getEntity("brick", "brick-tank");
		assertEquals(Integer.MAX_VALUE, brick.getStrength());
		
		brick = (SHBrick)scene.getEntity("brick", "brick4");
		assertTrue(brick.isGlass());
		
		brick = (SHBrick)scene.getEntity("brick", "brick1");
		assertEquals(1, brick.getStrength());
	
		Node rootNode = scene.getRootNode();
		for (Spatial groupSpatial : rootNode.getChildren())
		{
			Node group = (Node)groupSpatial;
			for (Spatial model : group.getChildren())
			{
				assertFalse( 
						SHUtils.areEqual(model.getLocalTranslation(), 
								new Vector3f(0, 0, 0), 0.001f), 
						model.getName() + " " + model.getLocalTranslation());
			}
		}
	}
		
	
}
