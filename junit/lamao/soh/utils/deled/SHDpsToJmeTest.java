/* 
 * SHSceneLoaderTest.java 22.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.deled;

import java.io.File;

import lamao.soh.ngutils.AbstractJmeTest;


import org.testng.annotations.Test;
import static org.testng.Assert.*;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.state.RenderState.StateType;

/**
 * @author lamao
 *
 */
public class SHDpsToJmeTest extends AbstractJmeTest
{
	
	@Test
	public void testLoading()
	{
		SHDpsToJme loader = new SHDpsToJme();
		
		loader.load(new File("data/test/non-existent.dps"));
		Node scene = (Node)loader.getResult();		
		assertNull(scene);
		
		loader.load(new File("data/test/test-level.dps"));
		scene = (Node)loader.getResult();		
		assertNotNull(scene);
		assertEquals(21, scene.getChildren().size());
		assertNotNull(scene.getChild("pyramid22"));
		assertNotNull(scene.getChild("left-wall"));
		assertNotNull(scene.getChild("top-wall"));
		assertNotNull(scene.getChild("bottom-wall"));
		assertNotNull(scene.getChild("right-wall"));
		assertNotNull(scene.getChild("decoration"));
		assertNotNull(scene.getChild("brick1"));
		assertNotNull(scene.getChild("brick2"));
		assertNotNull(scene.getChild("brick3"));
		assertNotNull(scene.getChild("brick4"));
		assertNotNull(scene.getChild("brick5"));
		assertNotNull(scene.getChild("brick6"));
		assertNotNull(scene.getChild("brick7"));
		assertNotNull(scene.getChild("brick8"));
		assertNotNull(scene.getChild("brick9"));
		assertNotNull(scene.getChild("brick-tank"));
		assertNotNull(scene.getChild("brick-boxes1"));
		assertNotNull(scene.getChild("brick-boxes2"));
		assertNotNull(scene.getChild("brick-column1"));
		assertNotNull(scene.getChild("brick-column2"));
		assertNotNull(scene.getChild("brick-column3"));
		
		for (Spatial spatial : scene.getChildren())
		{
			assertNotNull( spatial.getWorldBound(), spatial.toString());
			assertNotNull(spatial.getRenderState(StateType.Material));
		}
		
		assertNotNull(scene.getChild("decoration").getRenderState(StateType.Texture));
		assertNotNull(scene.getChild("brick-boxes1").getRenderState(StateType.Texture));
		assertNotNull(scene.getChild("brick-boxes2").getRenderState(StateType.Texture));
		assertSame(scene.getChild("brick-boxes1").getRenderState(StateType.Texture),
				scene.getChild("brick-boxes2").getRenderState(StateType.Texture));
		assertNotNull(scene.getChild("brick-tank").getRenderState(StateType.Texture));
		assertNotNull(scene.getChild("brick-column1").getRenderState(StateType.Texture));
		assertNotNull(scene.getChild("brick-column2").getRenderState(StateType.Texture));
		assertNotNull(scene.getChild("brick-column3").getRenderState(StateType.Texture));
		assertSame(scene.getChild("brick-column1").getRenderState(StateType.Texture),
				scene.getChild("brick-column2").getRenderState(StateType.Texture));
		assertSame(scene.getChild("brick-column1").getRenderState(StateType.Texture),
				scene.getChild("brick-column3").getRenderState(StateType.Texture));
		
		loader.load(new File("data/test/non-existent.dps"));
		scene = (Node)loader.getResult();		
		assertNull(scene);
	}
}
