/* 
 * SHSceneLoaderTest.java 22.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.deled;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.junit.Test;
import static org.junit.Assert.*;

import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.state.RenderState.StateType;
import com.jme.system.DisplaySystem;
import com.jme.system.dummy.DummySystemProvider;

/**
 * @author lamao
 *
 */
public class SHDpsToJmeTest
{
	
	static
	{
		Logger.getLogger("").setLevel(Level.OFF);
		DisplaySystem.setSystemProvider(new DummySystemProvider());
	}

	@Test
	public void testLoading()
	{
		SHDpsToJme loader = new SHDpsToJme();
		
		Node scene = (Node)loader.load(new File("data/test/non-existent.dps"));		
		assertNull(scene);
		
		scene = (Node)loader.load(new File("data/test/test-level.dps"));		
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
			assertNotNull(spatial.toString(), spatial.getWorldBound());
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
		
		scene = (Node)loader.load(new File("data/test/non-existent.dps"));		
		assertNull(scene);
	}
}
