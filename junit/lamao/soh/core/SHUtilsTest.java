/* 
 * SHUtils.java 23.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;


import java.util.Map;

import org.testng.annotations.Test;
import static org.testng.Assert.*;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Point;
import com.jme3.scene.SharedMesh;
import com.jme3.scene.SharedNode;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

/**
 * @author lamao
 *
 */
public class SHUtilsTest
{
	@Test
	public void testVectorComparsion()
	{
		Vector3f v1 = new Vector3f(0.1f, -0.2f, -0.2f);
		Vector3f v2 = new Vector3f(0.2f, -0.1f, -0.3f);
		assertTrue(SHUtils.areEqual(v1, v2 , 0.11f));
		assertFalse(SHUtils.areEqual(v1, v2, 0.1f));
		
		v2.x = 0;
		assertTrue(SHUtils.areEqual(v1, v2 , 0.11f));
		assertFalse(SHUtils.areEqual(v1, v2, 0.1f));
		
		v1.x = 0.05f;
		v2.x = -0.05f;
		assertTrue(SHUtils.areEqual(v1, v2 , 0.11f));
		assertFalse(SHUtils.areEqual(v1, v2, 0.1f));
		
		v1 = new Vector3f(0, 0, 0);
		v2 = new Vector3f(-0.0f, -0.0f, -0.0f);
		assertTrue(SHUtils.areEqual(v1, v2 , 0.000001f));
		assertFalse(SHUtils.areEqual(v1, v2, 0.0f));
	}
	
	@Test
	public void testInRange()
	{
		assertTrue(SHUtils.inRange(1.2f, 1.1f, 1.3f));
		assertTrue(SHUtils.inRange(1.1f, 1.1f, 1.3f));
		assertTrue(SHUtils.inRange(1.3f, 1.1f, 1.3f));
		assertFalse(SHUtils.inRange(1.2f, 1.21f, 1.3f));
		
		assertTrue(SHUtils.inRange(1.2f, 1.3f, 1.1f));
		assertTrue(SHUtils.inRange(1.1f, 1.3f, 1.1f));
		assertTrue(SHUtils.inRange(1.3f, 1.3f, 1.1f));
		assertFalse(SHUtils.inRange(1.2f, 1.3f, 1.21f));
		
		assertTrue(SHUtils.inRange(-1.2f, -1.1f, -1.3f));
		assertTrue(SHUtils.inRange(-1.1f, -1.1f, -1.3f));
		assertTrue(SHUtils.inRange(-1.3f, -1.1f, -1.3f));
		assertFalse(SHUtils.inRange(-1.2f, -1.21f, -1.3f));
		
		assertTrue(SHUtils.inRange(-1.2f, -1.3f, -1.1f));
		assertTrue(SHUtils.inRange(-1.1f, -1.3f, -1.1f));
		assertTrue(SHUtils.inRange(-1.3f, -1.3f, -1.1f));
		assertFalse(SHUtils.inRange(-1.2f, -1.3f, -1.21f));
	}
	
	@Test
	public void testAngle()
	{
		Vector3f v = new Vector3f(1, 0, 0);
		
		assertTrue(Math.abs(SHUtils.angle(v) - 0) < 0.001f);
		
		v.set(1, 0, -1);
		assertTrue(Math.abs(SHUtils.angle(v) - Math.PI / 4) < 0.001f);
		
		v.set(0, 0, -1);
		assertTrue(Math.abs(SHUtils.angle(v) - Math.PI / 2) < 0.001f);
		
		v.set(-1, 0, -1);
		assertTrue(Math.abs(SHUtils.angle(v) - 3 * Math.PI / 4) < 0.001f);
		
		v.set(-1, 0, 0);
		assertTrue(Math.abs(SHUtils.angle(v) - Math.PI) < 0.001f);
		
		v.set(-1, 0, 1);
		assertTrue(Math.abs(SHUtils.angle(v) - 5 * Math.PI / 4) < 0.001f);
		
		v.set(0, 0, 1);
		assertTrue(Math.abs(SHUtils.angle(v) - 3 * Math.PI / 2) < 0.001f);
		
		v.set(1, 0, 1);
		assertTrue(Math.abs(SHUtils.angle(v) - 7 * Math.PI / 4) < 0.001f);
		
		v.set(1, 0, 0.00000000000000000001f);
		assertTrue(Math.abs(SHUtils.angle(v) - 2 * Math.PI) < 0.001f);
	}
	
	@Test
	public void testCreateSharedModel()
	{
		Box box = new Box("box", new Vector3f(0, 0, 0), 1, 1, 1);
		Spatial sharedMesh = SHUtils.createSharedModel("shared-mesh", box);
		assertTrue(sharedMesh instanceof SharedMesh);
		assertEquals("shared-mesh", sharedMesh.getName());
		assertNotNull(((SharedMesh)sharedMesh).getTarget());
		
		Node node = new Node("node");
		node.attachChild(new Sphere("sphere", 15, 15, 1));
		Spatial sharedNode = SHUtils.createSharedModel("shared-node", node);
		assertTrue(sharedNode instanceof SharedNode);
		assertEquals("shared-node", sharedNode.getName());
		
		Point point = new Point();
		Spatial sharedOther = SHUtils.createSharedModel("shared-other", point);
		assertNull(sharedOther);
	}
	
	@Test
	public void testGetClassName()
	{
		assertEquals("lamao.soh.bonuses.SHDoubleBallBonus", 
				SHUtils.getClassName("lamao.soh.bonuses.SH", "double-ball", "Bonus"));
	}
	
	@Test
	public void testBuildMap()
	{
		Map<String, String> map = SHUtils.buildMap("key value");
		assertEquals(1, map.size());
		assertEquals("value", map.get("key"));
		
		map = SHUtils.buildMap("");
		assertNull(map);
		
		map = SHUtils.buildMap("key\tvalue|");
		assertEquals(1, map.size());
		assertEquals("value", map.get("key"));
		
		map = SHUtils.buildMap("key value|key2 value2");
		assertEquals(2, map.size());
		assertEquals("value", map.get("key"));
		assertEquals("value2", map.get("key2"));
		
		map = SHUtils.buildMap("key value|key2 value2|");
		assertEquals(2, map.size());
		assertEquals("value", map.get("key"));
		assertEquals("value2", map.get("key2"));
	}
	
	@Test
	public void testBuildEventMap()
	{
		Map<String, Object> map = SHUtils.buildEventMap("key", 1);
		assertEquals(1, map.get("key"));
		
		map = SHUtils.buildEventMap((Object[])null);
		assertNull(map);
		
		map = SHUtils.buildEventMap("key");
		assertNull(map);
		
		map = SHUtils.buildEventMap("key", 1, "key2");
		assertNull(map);
		
		map = SHUtils.buildEventMap("key", 1, "key2", 1.45f, "key3", "value");
		assertEquals(1, map.get("key"));
		assertEquals(1.45f, map.get("key2"));
		assertEquals("value", map.get("key3"));
		
	}
	
}
