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
import com.jme.input.InputHandler;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.shape.Box;
import com.jme.scene.shape.Sphere;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.WireframeState;
import com.jme.system.DisplaySystem;

import lamao.soh.core.SHBall;
import lamao.soh.core.SHBrick;
import lamao.soh.core.SHLevel;
import lamao.soh.core.SHMouseBallLauncher;
import lamao.soh.core.SHPaddle;
import lamao.soh.core.SHPaddleInputHandler;
import lamao.soh.core.SHPaddleSticker;
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
		level.updateDeletebleBricks();
		setupInputHandler(level);
		
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
		DisplaySystem display = DisplaySystem.getDisplaySystem();
		MaterialState superMs = display.getRenderer().createMaterialState();
		superMs.setEmissive(ColorRGBA.black);
		MaterialState defaultMs = display.getRenderer().createMaterialState();
		defaultMs.setEmissive(ColorRGBA.green);
		WireframeState glassMs = display.getRenderer().createWireframeState();
		glassMs.setEnabled(true);
		
		
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
				brick.getModel().setRenderState(superMs);
			}
			else
			{
				brick.setStrength(random.nextInt(5));
				brick.getModel().setRenderState(defaultMs);
			}
			brick.setGlass(random.nextBoolean());
			if (brick.isGlass())
			{
				brick.getModel().setRenderState(glassMs);
			}
			
			level.addBrick(brick);
		}
		
		Box box = new Box("paddle", new Vector3f(0, 0, 0), 2, 0.25f, 0.25f);
		box.setModelBound(new BoundingBox());
		box.updateModelBound();
		SHPaddle paddle = new SHPaddle(box);
		paddle.setLocation(0, -7, 0);
		level.setPaddle(paddle);
		
		SHBall ball = new SHBall(new Sphere("ball", 15, 15, 0.25f));
		ball.getModel().setModelBound(new BoundingSphere());
		ball.getModel().updateModelBound();
		ball.setLocation(-0, -6.5f, 0);
		ball.setVelocity(-3 ,3 ,0);
		ball.getModel().addController(new SHPaddleSticker(ball, paddle.getModel()));
		level.addBall(ball);
		
	}
	
	private static void setupInputHandler(SHLevel level)
	{
		InputHandler input = new SHPaddleInputHandler(level.getPaddle().getModel());
		level.setInputHandler(input);
		for (SHBall ball : level.getBalls())
		{
			level.getInputHandler().addAction(new SHMouseBallLauncher(ball, input));
		}
	}
	
}
