/* 
 * SHBrekoutEntityFactoryTest.java 04.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.core.bonuses.SHDoubleBallBonus;
import lamao.soh.core.bonuses.SHIncPaddleWidthBonus;
import lamao.soh.core.entities.SHBottomWall;
import lamao.soh.core.entities.SHBrick;
//import lamao.soh.utils.SHResourceManager;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import com.jme3.scene.Node;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;



/**
 * @author lamao
 *
 */
public class SHBreakoutEntityFactoryTest
{
//	@Mock
//	private SHResourceManager manager;
//
//	private SHBreakoutEntityFactory _factory;
//
//
//	@BeforeMethod
//	public void setUp()
//	{
//		initMocks(this);
//
//		_factory = new SHBreakoutEntityFactory(manager);
//	}
//
//	@Test
//	public void testBricks()
//	{
//
//		SHBrick brick = (SHBrick)_factory.createEntity(SHUtils.buildMap(
//				"type brick"));
//		assertEquals(1, brick.getStrength());
//		assertFalse(brick.isGlass());
//
//		brick = (SHBrick)_factory.createEntity(SHUtils.buildMap(
//		"type brick|strength 3|glass true"));
//		assertEquals(3, brick.getStrength());
//		assertTrue(brick.isGlass());
//
//		brick = (SHBrick)_factory.createEntity(SHUtils.buildMap(
//		"type brick|strength super|glass false"));
//		assertEquals(Integer.MAX_VALUE, brick.getStrength());
//		assertFalse(brick.isGlass());
//	}
//
//	@Test
//	public void testBricksWithBonus()
//	{
//		when(manager.get(SHResourceManager.TYPE_MODEL, "double-ball"))
//			.thenReturn(new Node("double-ball"));
//
//		SHBrick brick = (SHBrick)_factory.createEntity(SHUtils.buildMap(
//				"type brick|bonus double-ball"));
//		assertNotNull(brick.getBonus());
//	}
//
//	@Test
//	public void testWalls()
//	{
//		SHEntity wall = _factory.createEntity(SHUtils.buildMap("type wall"));
//		assertNotNull(wall);
//
//		wall = _factory.createEntity(SHUtils.buildMap("type bottom-wall"));
//		assertTrue(wall instanceof SHBottomWall);
//
//	}
//
//	@Test
//	public void testNull()
//	{
//		assertNull(_factory.createEntity(SHUtils.buildMap("type some-type")));
//		assertNull(_factory.createEntity(null));
//	}
//
//	@Test
//	public void testBonus()
//	{
//		when(manager.get(SHResourceManager.TYPE_MODEL, "double-ball"))
//			.thenReturn(new Node("double-ball"));
//
//		SHBonus bonus = (SHBonus)_factory.createEntity(SHUtils
//				.buildMap("type bonus|name double-ball"));
//		assertTrue(bonus instanceof SHDoubleBallBonus);
//
//		bonus = (SHBonus)_factory.createEntity(SHUtils
//				.buildMap("type bonus|name inc-paddle-width"));
//		assertTrue(bonus instanceof SHIncPaddleWidthBonus);
//
//		bonus = (SHBonus)_factory.createEntity(SHUtils
//				.buildMap("type bonus|name non-existent"));
//		assertNull(bonus);
//	}

}
