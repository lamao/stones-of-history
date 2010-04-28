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
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.core.bonuses.SHIncPaddleWidthBonus;
import lamao.soh.utils.events.SHEventCounter;
import lamao.soh.utils.events.SHEventDispatcher;

import org.junit.Before;
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
	private SHEventCounter counter = new SHEventCounter();
	
	@Before
	public void setUp()
	{
		counter.reset();
		SHEventDispatcher.getInstance().reset();
		SHEventDispatcher.getInstance().addHandler("all", counter);
	}
	
	@Test
	public void testContstructors()
	{
		SHLevel level = new SHLevel();
		assertNotNull(level);
		assertNotNull(level.getBalls());
		assertNotNull(level.getBricks());
		assertNull(level.getPaddle());
		assertNotNull(level.getRootNode());
		assertEquals(3, level.getRootNode().getChildren().size());
		assertNull(level.getInputHandler());
		assertEquals(0, level.getBonuses().size());
		assertEquals(0, level.getActiveBonuses().size());
		assertEquals(0, level.getBullets().size());
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
		int children = level.getRootNode().getChildren().size();
		level.addBall(ball);
		assertEquals(children + 1, level.getRootNode().getChildren().size());
		assertEquals(1, level.getBalls().size());
		
		List<SHBall> balls = new LinkedList<SHBall>();
		children = level.getRootNode().getChildren().size();
		for (int i = 0; i < 3; i++)
		{
			balls.add(SHCoreTestHelper.createDefaultBall("ball"  + i));
		}
		level.addBalls(balls);
		assertEquals(children + 3, level.getRootNode().getChildren().size());
		assertEquals(4, level.getBalls().size());
		
		children = level.getRootNode().getChildren().size();
		level.deleteBall(ball);
		assertEquals(children - 1, level.getRootNode().getChildren().size());
		assertEquals(3, level.getBalls().size());
		
		children = level.getRootNode().getChildren().size();
		balls.remove(1);
		level.deleteBalls(balls);
		assertEquals(children - 2, level.getRootNode().getChildren().size());
		assertEquals(1, level.getBalls().size());
		
	}
	
	@Test
	public void testPaddle()
	{
		SHLevel level = new SHLevel();
		
		int children = level.getRootNode().getChildren().size();
		level.setPaddle(SHCoreTestHelper.createDefaultPaddle());
		assertNotNull(level.getPaddle());
		assertEquals(children + 1, level.getRootNode().getChildren().size());
		
		children = level.getRootNode().getChildren().size();
		level.setPaddle(SHCoreTestHelper.createDefaultPaddle());
		assertNotNull(level.getPaddle());
		assertEquals(children, level.getRootNode().getChildren().size());
	}
	
	@Test
	public void testWallSetup()
	{
		SHLevel level = new SHLevel();
		int children = level.getRootNode().getChildren().size();

		Box wall = new Box("left wall", Vector3f.ZERO.clone(), 1, 1, 1);
		level.setWall(wall, SHLevel.SHWallType.LEFT);
		assertNotNull(level.getWall(SHLevel.SHWallType.LEFT));
		assertEquals(children + 1, level.getRootNode().getChildren().size());
		
		children = level.getRootNode().getChildren().size();
		wall = new Box("right wall", Vector3f.ZERO.clone(), 1, 1, 1);
		level.setWall(wall, SHLevel.SHWallType.RIGHT);
		assertNotNull(level.getWall(SHLevel.SHWallType.RIGHT));
		assertEquals(children + 1, level.getRootNode().getChildren().size());
		
		children = level.getRootNode().getChildren().size();
		wall = new Box("top wall", Vector3f.ZERO.clone(), 1, 1, 1);
		level.setWall(wall, SHLevel.SHWallType.TOP);
		assertNotNull(level.getWall(SHLevel.SHWallType.TOP));
		assertEquals(children + 1, level.getRootNode().getChildren().size());
		
		children = level.getRootNode().getChildren().size();
		wall = new Box("bottom wall", Vector3f.ZERO.clone(), 1, 1, 1);
		level.setWall(wall, SHLevel.SHWallType.BOTTOM);
		assertNotNull(level.getWall(SHLevel.SHWallType.BOTTOM));
		assertEquals(children + 1, level.getRootNode().getChildren().size());
		
		children = level.getRootNode().getChildren().size();
		wall = new Box("other bottom wall", Vector3f.ZERO.clone(), 1, 1, 1);
		level.setWall(wall, SHLevel.SHWallType.BOTTOM);
		assertNotNull(level.getWall(SHLevel.SHWallType.BOTTOM));
		assertEquals(children, level.getRootNode().getChildren().size());
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
		
		level.getRootNode().updateGeometricState(0.51f, true);
		level.update(0.51f);
		assertTrue(SHUtils.areEqual(Vector3f.UNIT_Y, ball.getVelocity(), 
				0.001f));
		assertEquals(2, level.getBricks().size());
		assertEquals(2, ((Node)level.getRootNode().getChild(0)).getChildren().size());
	}
	
	private void testBallCollisionWithDefaultBrick(SHLevel level, SHBall ball)
	{
		ball.setLocation(-1, -5f, 0);
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
		
		level.getRootNode().updateGeometricState(0.51f, true);
		level.update(0.51f);
		assertTrue(ball.getVelocity().toString(), 
				SHUtils.areEqual(new Vector3f(-1, -1, 0), ball.getVelocity(), 
				0.001f));
		assertEquals(1, level.getBricks().size());
		assertEquals(1, ((Node)level.getRootNode().getChild(0)).getChildren().size());
	}
	
	private void testBallCollisionWithSuperBrick(SHLevel level, SHBall ball)
	{
		ball.setLocation(1, -5f, 0);
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
		
		level.getRootNode().updateGeometricState(0.51f, true);
		level.update(0.51f);
		assertTrue(ball.getVelocity().toString(),
				SHUtils.areEqual(new Vector3f(1, -1, 0), ball.getVelocity(), 
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
		ball.setLocation(-7, -5, 0);
		
		level.getRootNode().updateGeometricState(1f, true);
		level.update(1f);
		assertTrue(SHUtils.areEqual(new Vector3f(-8, -6, 0), ball.getLocation(), 
				0.001f));
		assertTrue(SHUtils.areEqual(new Vector3f(1, -1, 0), ball.getVelocity(), 
				0.001f));
		
		//|		
		//| o 
		//|
		//|-------
		level.getRootNode().updateGeometricState(1f, true);
		level.update(1f);
		assertTrue(SHUtils.areEqual(new Vector3f(-7, -7, 0), ball.getLocation(), 
				0.001f));
		assertTrue(ball.getVelocity().toString(),
				SHUtils.areEqual(new Vector3f(1, -1, 0), ball.getVelocity(), 
				0.001f));
		
		//|		
		//| o 
		//|
		//|-------
		ball.setVelocity(1, -1, 0);
		ball.setLocation(-8, -7, 0);
		
		level.getRootNode().updateGeometricState(1f, true);
		level.update(1f);
		assertTrue(ball.getLocation().toString(),
				SHUtils.areEqual(new Vector3f(-7, -8, 0), ball.getLocation(), 
				0.001f));
		assertTrue(SHUtils.areEqual(new Vector3f(1, 1, 0), ball.getVelocity(), 
				0.001f));
		
	}
	
	@Test
	public void testCollisionWithBottomWall()
	{
		SHLevel level = SHCoreTestHelper.createDefaultLevel();
		SHBall ball = level.getBalls().get(0);
		ball.setLocation(0, -8, 0);
		
		level.setBottomWallActive(true);
		level.update(1);
		assertEquals(1, level.getBalls().size());
		
		level.setBottomWallActive(false);
		level.update(1);
		assertEquals(0, level.getBalls().size());
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
		SHLevel level = SHCoreTestHelper.createLevelWithBonus();
		SHBonus bonus = SHCoreTestHelper.createDefaultBonus("bonus #2");
		level.getBonuses().put(level.getBricks().get(1), bonus);
		
		// extract first bonus
		level.getBalls().get(0).setLocation(0, -1.99f, 0);
		level.getRootNode().updateGeometricState(1f, true);
		level.update(1f);
		
		// put and extract another bonus 
		bonus = SHCoreTestHelper.createDefaultBonus("bonus #3");
		level.getBonuses().put(level.getBricks().get(1), bonus);		
		level.getBalls().get(0).setLocation(0, -1.99f, 0);
		level.getRootNode().updateGeometricState(1f, true);
		level.update(1f);
		
		// activate first extracted bonus
		level.getRootNode().updateGeometricState(6, true);
		level.update(6);
		assertEquals(1, level.getActiveBonuses().size());
		
		// clear level
		level.clear();
		assertEquals(0, level.getBalls().size());
		assertEquals(0, level.getBricks().size());
		assertNotNull(level.getPaddle());
		for (SHWallType type : SHWallType.values())
		{
			assertNotNull(level.getWall(type));
		}
		assertEquals(8, level.getRootNode().getChildren().size());
		
		assertEquals(0, level.getActiveBonuses().size());
		assertEquals(0, level.getBonuses().size());
		assertEquals(0, level.getBullets().size());
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
		
		ball.setLocation(0, -1, 0);
		level.update(1);
		assertNull(counter.numEvents.get("level-completed"));
		
		ball.setLocation(-5, -1, 0);
		level.update(1);
		assertTrue(1 == counter.numEvents.get("level-completed"));
		
		level.update(1f);
		assertTrue(1 == counter.numEvents.get("level-completed"));
		level.update(1f);
		assertTrue(1 == counter.numEvents.get("level-completed"));
	}
	
	@Test
	public void testListener()
	{
		SHLevel level = SHCoreTestHelper.createDefaultLevel();
		SHBall ball = level.getBalls().get(0);
		
		ball.setLocation(5, -1, 0);
		level.update(1);
		assertTrue(1 == counter.numEvents.get("level-brick-hit"));
		assertNull(counter.numEvents.get("level-brick-deleted"));
		
		ball.setLocation(0, -1, 0);
		level.update(1);
		assertTrue(2 == counter.numEvents.get("level-brick-hit"));
		assertTrue(1 == counter.numEvents.get("level-brick-deleted"));
		
		ball.setLocation(-8, -8, 0);
		level.update(1);
		assertTrue(2 == counter.numEvents.get("level-wall-hit"));
		
		ball.setLocation(0, 8, 0);
		level.update(1);
		assertTrue(3 == counter.numEvents.get("level-wall-hit"));
		
		ball.setLocation(8, 8, 0);
		level.update(1);
		assertTrue(5 == counter.numEvents.get("level-wall-hit"));
		
		ball.setLocation(8, 0, 0);
		level.update(1);
		assertTrue(6 == counter.numEvents.get("level-wall-hit"));
		
		ball.setLocation(0, -8, 0);
		level.setBottomWallActive(false);
		level.update(1);
		assertTrue(7 == counter.numEvents.get("level-wall-hit"));
		assertTrue(1 == counter.numEvents.get("level-failed"));
		
	}
	
	@Test
	public void testBonuses()
	{
		SHLevel level = SHCoreTestHelper.createLevelWithBonus();
		
		SHBall ball = level.getBalls().get(0);
		ball.setLocation(-5, -1.99f, 0);
		level.addBall(ball);
		
		SHBrick brick = level.getBricks().get(0);
		SHBonus bonus = SHCoreTestHelper.createDefaultBonus();
		level.getBonuses().put(brick, bonus);

		level.update(1);
		assertEquals(0, level.getBonuses().size());
//		assertEquals(9, level.getRootNode().getChildren().size());
		assertTrue(SHUtils.areEqual(new Vector3f(-5, 0, 0), bonus.getLocation(), 
				0.001f));
		assertTrue(1 == counter.numEvents.get("level-bonus-showed"));
		
		// test bonus moving
		Node bonusNode = (Node)level.getRootNode().getChild(1);
		assertEquals(1, bonusNode.getChildren().size());
		
		// test bonus activation
		level.getPaddle().setLocation(-5, -7, 0);
		level.getRootNode().updateGeometricState(7, true);
		level.update(7);
		
		assertTrue(SHUtils.areEqual(new Vector3f(-5, -7, 0), 
				bonus.getLocation(), 0.001f));
		assertEquals(0, bonusNode.getChildren().size());
		assertTrue(Math.abs(level.getPaddle().getModel().getLocalScale().x 
				- 1.0f) > 0.001f);
		assertTrue(1 == counter.numEvents.get("level-bonus-activated"));
		assertEquals(1, level.getActiveBonuses().size());
		
		//test bonus deactivation
		level.update(SHIncPaddleWidthBonus.DURATION - 1);
		assertEquals(1, level.getActiveBonuses().size());
		assertTrue(Math.abs(level.getPaddle().getModel().getLocalScale().x 
				- 1) > 0.001f);
		assertNull(counter.numEvents.get("level-bonus-deactivated"));
		
		level.update(1);
		assertEquals(0, level.getActiveBonuses().size());
		assertTrue(Math.abs(level.getPaddle().getModel().getLocalScale().x 
				- 1f) < 0.001f);
		assertTrue(1 == counter.numEvents.get("level-bonus-deactivated"));
		
	}
	
	@Test
	public void testBonusCollisionWithBottomWall()
	{
		SHLevel level = SHCoreTestHelper.createLevelWithBonus();
		
		SHBall ball = level.getBalls().get(0);
		ball.setLocation(-5, -1.99f, 0);
		level.addBall(ball);
		
		SHBrick brick = level.getBricks().get(0);
		SHBonus bonus = SHCoreTestHelper.createDefaultBonus();
		level.getBonuses().put(brick, bonus);
		
		// break brick and extract bonus
		level.update(1);
		
		// move bonus to bottom wall
		level.getRootNode().updateGeometricState(9, true);
		level.update(9);
		
		Node bonusNode = (Node)level.getRootNode().getChild(1);
		assertTrue(bonus.getLocation().toString(),
				SHUtils.areEqual(new Vector3f(-5, -9f, 0), 
				bonus.getLocation(), 0.001f));
		assertEquals(0, bonusNode.getChildren().size());
		assertTrue(Math.abs(level.getPaddle().getModel().getLocalScale().x 
				- 1) < 0.001f);
	}
	
	@Test
	public void testBulletsAddRemove()
	{
		SHLevel level = SHCoreTestHelper.createDefaultLevel();
		Node bulletsNode = (Node) level.getRootNode().getChild("bullets");
		
		SHBall bullet = SHCoreTestHelper.createDefaultBall("bullet");
		
		level.addBullet(bullet);
		assertEquals(1, level.getBullets().size());
		assertEquals(1, bulletsNode.getChildren().size());
		
		level.deleteBullet(bullet);
		assertEquals(0, level.getBullets().size());
		assertEquals(0, bulletsNode.getChildren().size());
	}
	
	@Test
	public void testBulletsInAction()
	{
		SHLevel level = SHCoreTestHelper.createDefaultLevel();
		Node bulletsNode = (Node) level.getRootNode().getChild("bullets");
		
		SHBall bullet = SHCoreTestHelper.createDefaultBall("bullet");
		bullet.setSuper(true);
		bullet.setVelocity(0, 2, 0);
		bullet.setLocation(-5, -3, 0);
		bullet.getModel().addController(new SHDefaultBallMover(bullet));
		
		level.addBullet(bullet);
		level.getRootNode().updateGeometricState(1.1f, true);
		level.update(1.1f);
		
		assertEquals(2, level.getBricks().size());
		assertEquals(0, bulletsNode.getChildren().size());
		assertEquals(0, level.getBullets().size());

		bullet.setLocation(0, 8, 0);
		level.addBullet(bullet);
		level.getRootNode().updateGeometricState(2f, true);
		level.update(2f);
		
		assertEquals(0, bulletsNode.getChildren().size());
		assertEquals(0, level.getBullets().size());
		
	}
	
}
