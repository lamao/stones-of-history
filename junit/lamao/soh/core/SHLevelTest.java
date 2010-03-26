/* 
 * SHLevelTest.java 24.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import java.util.LinkedList;
import java.util.List;

import lamao.soh.core.SHLevel.SHWallType;

import org.junit.Test;

import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.shape.Box;

import static org.junit.Assert.*;

/**
 * @author lamao
 *
 */
public class SHLevelTest
{
	/** Stub for level listener */
	private class SHTestLevelListner implements SHLevelListener
	{
		public int wallHit = 0;
		public int brickHit = 0;
		public int completed = 0;
		public int numDeletebleBricks = 0;
		public int bricksDeleted = 0;

		@Override
		public void wallHit(SHWallType wall)
		{
			wallHit++;
		}
		
		@Override
		public void completed()
		{
			completed++;
		}

		@Override
		public void brickHit(SHBrick brick)
		{
			brickHit++;
		}
		
		@Override
		public void brickDeleted(SHBrick brick)
		{
			bricksDeleted++;
		}
	}
	
	private SHTestLevelListner listener = new SHTestLevelListner();
	
	@Test
	public void testContstructors()
	{
		SHLevel level = new SHLevel();
		assertNotNull(level);
		assertNotNull(level.getBalls());
		assertNotNull(level.getBricks());
		assertNull(level.getPaddle());
		assertNotNull(level.getRootNode());
		assertEquals(1, level.getRootNode().getChildren().size());
		assertNull(level.getInputHandler());
	}
	
	@Test
	public void testBrickList()
	{
		SHLevel level = new SHLevel();
		Node brickRoot =(Node)level.getRootNode().getChild(0);
		
		SHBrick brick = SHCoreTestHelper.createDefaultBrick("brick");
		level.addBrick(brick);
		assertEquals(1, level.getBricks().size());
		assertEquals(1, brickRoot.getChildren().size());
		
		List<SHBrick> bricks = new LinkedList<SHBrick>();
		bricks.add(SHCoreTestHelper.createGlassBrick("glass"));
		bricks.add(SHCoreTestHelper.createSuperBrick("super"));
		level.addBricks(bricks);
		assertEquals(3, level.getBricks().size());
		assertEquals(3, brickRoot.getChildren().size());
		
		level.deleteBrick(brick);
		assertEquals(2, level.getBricks().size());
		assertEquals(2, brickRoot.getChildren().size());
		
		bricks.remove(0);
		level.deleteBricks(bricks);
		assertEquals(1, level.getBricks().size());
		assertEquals(1, brickRoot.getChildren().size());
	}
	
	@Test
	public void testBallList()
	{
		SHLevel level = new SHLevel();
		SHBall ball = SHCoreTestHelper.createDefaultBall("ball");
		level.addBall(ball);
		assertEquals(2, level.getRootNode().getChildren().size());
		assertEquals(1, level.getBalls().size());
		
		List<SHBall> balls = new LinkedList<SHBall>();
		for (int i = 0; i < 3; i++)
		{
			balls.add(SHCoreTestHelper.createDefaultBall("ball"  + i));
		}
		level.addBalls(balls);
		assertEquals(5, level.getRootNode().getChildren().size());
		assertEquals(4, level.getBalls().size());
		
		level.deleteBall(ball);
		assertEquals(4, level.getRootNode().getChildren().size());
		assertEquals(3, level.getBalls().size());
		
		balls.remove(1);
		level.deleteBalls(balls);
		assertEquals(2, level.getRootNode().getChildren().size());
		assertEquals(1, level.getBalls().size());
		
	}
	
	@Test
	public void testPaddle()
	{
		SHLevel level = new SHLevel();
		
		level.setPaddle(SHCoreTestHelper.createDefaultPaddle());
		assertNotNull(level.getPaddle());
		assertEquals(2, level.getRootNode().getChildren().size());
		
		level.setPaddle(SHCoreTestHelper.createDefaultPaddle());
		assertNotNull(level.getPaddle());
		assertEquals(2, level.getRootNode().getChildren().size());
	}
	
	@Test
	public void testWallSetup()
	{
		SHLevel level = new SHLevel();

		Box wall = new Box("left wall", Vector3f.ZERO.clone(), 1, 1, 1);
		level.setWall(wall, SHLevel.SHWallType.LEFT);
		assertNotNull(level.getWall(SHLevel.SHWallType.LEFT));
		assertEquals(2, level.getRootNode().getChildren().size());
		
		wall = new Box("right wall", Vector3f.ZERO.clone(), 1, 1, 1);
		level.setWall(wall, SHLevel.SHWallType.RIGHT);
		assertNotNull(level.getWall(SHLevel.SHWallType.RIGHT));
		assertEquals(3, level.getRootNode().getChildren().size());
		
		wall = new Box("top wall", Vector3f.ZERO.clone(), 1, 1, 1);
		level.setWall(wall, SHLevel.SHWallType.TOP);
		assertNotNull(level.getWall(SHLevel.SHWallType.TOP));
		assertEquals(4, level.getRootNode().getChildren().size());
		
		wall = new Box("bottom wall", Vector3f.ZERO.clone(), 1, 1, 1);
		level.setWall(wall, SHLevel.SHWallType.BOTTOM);
		assertNotNull(level.getWall(SHLevel.SHWallType.BOTTOM));
		assertEquals(5, level.getRootNode().getChildren().size());
		
		wall = new Box("other bottom wall", Vector3f.ZERO.clone(), 1, 1, 1);
		level.setWall(wall, SHLevel.SHWallType.BOTTOM);
		assertNotNull(level.getWall(SHLevel.SHWallType.BOTTOM));
		assertEquals(5, level.getRootNode().getChildren().size());
	}
	
	@Test 
	public void testBallCollisionWithBricks()
	{
		SHLevel level = SHCoreTestHelper.createDefaultLevel();
		SHBall ball = level.getBalls().get(0);
		ball.getModel().addController(new SHDefaultBallMover(ball));
		
		testBallCollisionWithGlassBrick(level, ball);
		testBallCollisionWithDefaultBrick(level, ball);
		testBallCollisionWithSuperBrick(level, ball);
		
	}
	
	 
	private void testBallCollisionWithGlassBrick(SHLevel level, SHBall ball)
	{
		// move to brick
		for (int i = 0; i < 5; i++)
		{
			level.getRootNode().updateGeometricState(0.5f, true);
			level.update(0.5f);
			assertTrue("i = " + i, SHUtils.areEqual(Vector3f.UNIT_Y, 
					ball.getVelocity(), 0.001f));
			assertEquals("i = " + i, 3, level.getBricks().size());
		}
		
		level.getRootNode().updateGeometricState(0.5f, true);
		level.update(0.5f);
		assertTrue(SHUtils.areEqual(Vector3f.UNIT_Y, ball.getVelocity(), 
				0.001f));
		assertEquals(2, level.getBricks().size());
		assertEquals(2, ((Node)level.getRootNode().getChild(0)).getChildren().size());
	}
	
	private void testBallCollisionWithDefaultBrick(SHLevel level, SHBall ball)
	{
		ball.setLocation(0, -5, 0);
		Vector3f velocity = new Vector3f(-1, 1, 0);
		ball.setVelocity(velocity.clone());
		
		// move to brick
		for (int i = 0; i < 5; i++)
		{
			level.getRootNode().updateGeometricState(0.5f, true);
			level.update(0.5f);
			assertTrue("i = " + i, SHUtils.areEqual(velocity, 
					ball.getVelocity(), 0.001f));
			assertEquals("i = " + i, 2, level.getBricks().size());
		}
		
		level.getRootNode().updateGeometricState(0.5f, true);
		level.update(0.5f);
		assertTrue(SHUtils.areEqual(new Vector3f(-1, -1, 0), ball.getVelocity(), 
				0.001f));
		assertEquals(1, level.getBricks().size());
		assertEquals(1, ((Node)level.getRootNode().getChild(0)).getChildren().size());
	}
	
	private void testBallCollisionWithSuperBrick(SHLevel level, SHBall ball)
	{
		ball.setLocation(0, -5, 0);
		Vector3f velocity = new Vector3f(1, 1, 0);
		ball.setVelocity(velocity.clone());
		
		// move to brick
		for (int i = 0; i < 5; i++)
		{
			level.getRootNode().updateGeometricState(0.5f, true);
			level.update(0.5f);
			assertTrue("i = " + i, SHUtils.areEqual(velocity, 
					ball.getVelocity(), 0.001f));
			assertEquals("i = " + i, 1, level.getBricks().size());
		}
		
		level.getRootNode().updateGeometricState(0.5f, true);
		level.update(0.5f);
		assertTrue(SHUtils.areEqual(new Vector3f(1, -1, 0), ball.getVelocity(), 
				0.001f));
		assertEquals(1, level.getBricks().size());
		assertEquals(1, ((Node)level.getRootNode().getChild(0)).getChildren().size());
	}
	
	@Test
	public void testCollisionsWithWalls()
	{
		SHLevel level = SHCoreTestHelper.createDefaultLevel();
		SHBall ball = level.getBalls().get(0);
		ball.getModel().addController(new SHDefaultBallMover(ball));
		
		// -----
		//		|
		//		|
		// 	o   |
		ball.setVelocity(1, 1, 0);
		ball.setLocation(7, 6, 0);
		
		level.getRootNode().updateGeometricState(1f, true);
		level.update(1f);
		assertTrue(SHUtils.areEqual(new Vector3f(8, 7, 0), ball.getLocation(), 
				0.001f));
		assertTrue(SHUtils.areEqual(new Vector3f(-1, 1, 0), ball.getVelocity(), 
				0.001f));
		// -----
		//		|
		//	   o|
		//  	|
		
		level.getRootNode().updateGeometricState(1f, true);
		level.update(1f);
		assertTrue(SHUtils.areEqual(new Vector3f(7, 8, 0), ball.getLocation(), 
				0.001f));
		assertTrue(SHUtils.areEqual(new Vector3f(-1, -1, 0), ball.getVelocity(), 
				0.001f));
		
		// -----
		//	 o	|
		//	    |
		//  	|
		
		level.getRootNode().updateGeometricState(1f, true);
		level.update(1f);
		assertTrue(SHUtils.areEqual(new Vector3f(6, 7, 0), ball.getLocation(), 
				0.001f));
		assertTrue(SHUtils.areEqual(new Vector3f(-1, -1, 0), ball.getVelocity(), 
				0.001f));
		// -----
		//	 	|
		// o    |
		//  	|
		

		//|	 o	
		//|  
		//|
		//|-------
		ball.setVelocity(-1, -1, 0);
		ball.setLocation(-7, -6, 0);
		
		level.getRootNode().updateGeometricState(1f, true);
		level.update(1f);
		assertTrue(SHUtils.areEqual(new Vector3f(-8, -7, 0), ball.getLocation(), 
				0.001f));
		assertTrue(SHUtils.areEqual(new Vector3f(1, -1, 0), ball.getVelocity(), 
				0.001f));
		
		//|		
		//| o 
		//|
		//|-------
		level.getRootNode().updateGeometricState(1f, true);
		level.update(1f);
		assertTrue(SHUtils.areEqual(new Vector3f(-7, -8, 0), ball.getLocation(), 
				0.001f));
		assertTrue(SHUtils.areEqual(new Vector3f(1, -1, 0), ball.getVelocity(), 
				0.001f));
		
		//|		
		//| o 
		//|
		//|-------
		level.setBottomWallActive(true);
		ball.setVelocity(1, -1, 0);
		ball.setLocation(-8, -7, 0);
		
		level.getRootNode().updateGeometricState(1f, true);
		level.update(1f);
		assertTrue(SHUtils.areEqual(new Vector3f(-7, -8, 0), ball.getLocation(), 
				0.001f));
		assertTrue(SHUtils.areEqual(new Vector3f(1, 1, 0), ball.getVelocity(), 
				0.001f));
		
	}
	
	@Test
	public void testCollisionWithPaddle()
	{
		SHLevel level = SHCoreTestHelper.createDefaultLevel();
		SHBall ball = level.getBalls().get(0);
		ball.getModel().addController(new SHDefaultBallMover(ball));
		
		ball.setLocation(-1, -4, 0);
		ball.setVelocity(1, -1, 0);
		level.getRootNode().updateGeometricState(1, true);
		level.update(1);
		
		assertTrue(SHUtils.areEqual(new Vector3f(0, -5, 0), ball.getLocation(), 
				0.001f));
		assertTrue(SHUtils.areEqual(new Vector3f(0, (float)Math.sqrt(2), 0), 
				ball.getVelocity(), 0.001f));
	}
	
	@Test
	public void testClearLevel()
	{
		SHLevel level = SHCoreTestHelper.createDefaultLevel();
		level.clear();
		assertEquals(0, level.getBalls().size());
		assertEquals(0, level.getBricks().size());
		assertNotNull(level.getPaddle());
		for (SHWallType type : SHWallType.values())
		{
			assertNotNull(level.getWall(type));
		}
		assertEquals(6, level.getRootNode().getChildren().size());
	}
	
	@Test
	public void testBricksNode()
	{
		SHLevel level = SHCoreTestHelper.createDefaultLevel();
		Node bricksRoot = (Node)level.getRootNode().getChild(0);
		assertEquals(3, bricksRoot.getChildren().size());
	}
	
	@Test
	public void testUpdateDeletebleBricks()
	{
		SHLevel level = SHCoreTestHelper.createDefaultLevel();
		assertEquals(2, level.getNumDeletebleBricks());
		
		level.getBricks().remove(0);
		assertEquals(2, level.getNumDeletebleBricks());
		
		level.updateDeletebleBricks();
		assertEquals(1, level.getNumDeletebleBricks());
		
		level.getBricks().remove(1);
		level.updateDeletebleBricks();
		assertEquals(1, level.getNumDeletebleBricks());
		
		level.addBrick(SHCoreTestHelper.createDefaultBrick("brick"));
		level.updateDeletebleBricks();
		assertEquals(2, level.getNumDeletebleBricks());
		
		level.addBrick(SHCoreTestHelper.createSuperBrick("super"));
		level.updateDeletebleBricks();
		assertEquals(2, level.getNumDeletebleBricks());
	}
	
	@Test
	public void testInGameBrickAddRemove()
	{
		SHLevel level = SHCoreTestHelper.createDefaultLevel();
		SHBall ball = level.getBalls().get(0);
		level.addListener(listener);
		listener.completed = 0;
		
		ball.setLocation(0, -1, 0);
		level.update(1);
		assertEquals(0, listener.completed);
		
		ball.setLocation(-5, -1, 0);
		level.update(1);
		assertEquals(1, listener.completed);
		
		level.update(1f);
		assertEquals(1, listener.completed);
		level.update(1f);
		assertEquals(1, listener.completed);
	}
	
	@Test
	public void testListener()
	{
		SHLevel level = SHCoreTestHelper.createDefaultLevel();
		SHBall ball = level.getBalls().get(0);
		level.addListener(listener);
		listener.brickHit = 0;
		listener.wallHit = 0;
		listener.bricksDeleted = 0;
		
		ball.setLocation(5, -1, 0);
		level.update(1);
		assertEquals(1, listener.brickHit);
		assertEquals(0, listener.bricksDeleted);
		
		ball.setLocation(0, -1, 0);
		level.update(1);
		assertEquals(2, listener.brickHit);
		assertEquals(1, listener.bricksDeleted);
		
		ball.setLocation(-8, -8, 0);
		level.update(1);
		assertEquals(2, listener.wallHit);
		
		ball.setLocation(0, 8, 0);
		level.update(1);
		assertEquals(3, listener.wallHit);
		
		ball.setLocation(8, 8, 0);
		level.update(1);
		assertEquals(5, listener.wallHit);
		
		ball.setLocation(8, 0, 0);
		level.update(1);
		assertEquals(6, listener.wallHit);
		
	}
}
