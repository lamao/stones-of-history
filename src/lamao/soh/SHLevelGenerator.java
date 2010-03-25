/* 
 * SHLevelGenerator.java 25.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh;

import java.util.Random;

import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.math.Vector3f;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;

import lamao.soh.core.SHBall;
import lamao.soh.core.SHBrick;
import lamao.soh.core.SHDefaultBallMover;
import lamao.soh.core.SHLevel;
import lamao.soh.core.SHPaddle;
import lamao.soh.core.SHLevel.SHWallType;

/**
 * Generates level. Designed for testing purposes. 
 * @author lamao
 *
 */
public class SHLevelGenerator
{
	public static void generate(SHLevel level)
	{
		createWalls(level);
		createEntities(level);
		level.setBottomWallActive(true);
	}
	
	private static void createWalls(SHLevel level)
	{
		Box leftWall = new Box("left wall", new Vector3f(-10, 0, 0), 1, 10, 1);
		leftWall.setModelBound(new BoundingBox());
		leftWall.updateModelBound();
		level.setWall(leftWall, SHWallType.LEFT);
		
		Box rightWall = new Box("right wall", new Vector3f(10, 0, 0), 1, 10, 1);
		rightWall.setModelBound(new BoundingBox());
		rightWall.updateModelBound();
		level.setWall(rightWall, SHWallType.RIGHT);
		
		Box topWall = new Box("top wall", new Vector3f(0, 10, 0), 10, 1, 1);
		topWall.setModelBound(new BoundingBox());
		topWall.updateModelBound();
		level.setWall(topWall, SHWallType.TOP);
		
		Box bottomWall = new Box("bottom wall", new Vector3f(0, -10, 0), 10, 1, 1);
		bottomWall.setModelBound(new BoundingBox());
		bottomWall.updateModelBound();
		level.setWall(bottomWall, SHWallType.BOTTOM);
	}
	private static void createEntities(SHLevel level)
	{
		Random random = new Random();
		for (int i = 0; i < 10; i++)
		{
			Box box = new Box("brick" + i, 
					new Vector3f(random.nextFloat() * 16 - 8, 
								 random.nextFloat() * 8, 
								 0), 
					1, 0.5f, 0.5f);
			box.setModelBound(new BoundingBox());
			box.updateModelBound();
			SHBrick brick = new SHBrick(box);
			
			if (random.nextBoolean())
			{
				brick.setStrength(Integer.MAX_VALUE);
			}
			else
			{
				brick.setStrength(random.nextInt(5));
			}
			brick.setGlass(random.nextBoolean());
			
			level.addBrick(brick);
		}
		
		SHBall ball = new SHBall(new Sphere("ball", 15, 15, 0.25f));
		ball.getModel().setModelBound(new BoundingSphere());
		ball.getModel().updateModelBound();
		ball.setLocation(-2, 0, 0);
		ball.setVelocity(-3 ,3 ,0);
		ball.getModel().addController(new SHDefaultBallMover(ball));
		level.addBall(ball);
		
		Box box = new Box("paddle", new Vector3f(0, 0, 0), 1, 0.5f, 0.5f);
		box.setModelBound(new BoundingBox());
		box.updateModelBound();
		SHPaddle paddle = new SHPaddle(box);
		paddle.setLocation(0, -7, 0);
		level.setPaddle(paddle);
		
	}
	
}
