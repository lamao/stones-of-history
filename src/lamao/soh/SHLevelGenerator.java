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
import com.jme.scene.SharedMesh;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Box;
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
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.core.bonuses.SHDoubleBallBonus;
import lamao.soh.core.bonuses.SHStickyPaddleBonus;
import lamao.soh.utils.SHResourceManager;

/**
 * Generates level. Designed for testing purposes. 
 * @author lamao
 *
 */
public class SHLevelGenerator
{
	public static SHResourceManager manager = SHResourceManager.getInstance();
	
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
		Spatial leftWall = (Spatial)manager.get(SHResourceManager.TYPE_MODEL, "left-wall");
		leftWall.setLocalTranslation(-10, 0, 0);
		level.setWall(leftWall, SHWallType.LEFT);
		
		Spatial rightWall = (Spatial)manager.get(SHResourceManager.TYPE_MODEL, "right-wall");
		rightWall.setLocalTranslation(10, 0, 0);
		level.setWall(rightWall, SHWallType.RIGHT);
		
		Spatial topWall = (Spatial)manager.get(SHResourceManager.TYPE_MODEL, "top-wall");
		topWall.setLocalTranslation(0, 10, 0);
		level.setWall(topWall, SHWallType.TOP);
		
		Spatial bottomWall = (Spatial)manager.get(SHResourceManager.TYPE_MODEL, "bottom-wall");
		bottomWall.setLocalTranslation(0, -10, 0);
		level.setWall(bottomWall, SHWallType.BOTTOM);
	}
	private static void createEntities(SHLevel level)
	{
		DisplaySystem display = DisplaySystem.getDisplaySystem();
		WireframeState glassMs = display.getRenderer().createWireframeState();
		glassMs.setEnabled(true);
		
		Random random = new Random();
		for (int i = 0; i < 10; i++)
		{
			SHBrick brick = new SHBrick(null);
			
			if (random.nextBoolean())
			{
				brick.setStrength(Integer.MAX_VALUE);
			}
			else
			{
				brick.setStrength(random.nextInt(5));
			}
			brick.setGlass(random.nextBoolean());
			
			if (brick.getStrength() != Integer.MAX_VALUE)
			{
				if (brick.isGlass())
				{
					level.getBonuses().put(brick, createIncBonus("bonus" + i));
				}
				else
				{
					level.getBonuses().put(brick, createDecBonus("bonus" + i));
				}
			}
			
			Spatial model = null;
			if (brick.isGlass())
			{
				model = new SharedMesh("brick" + i, (TriMesh)manager
						.get(SHResourceManager.TYPE_MODEL, "glassbrick"));
				if (brick.getStrength() != Integer.MAX_VALUE)
				{
					model.setRenderState(glassMs);
				}
			}
			else if (brick.getStrength() == Integer.MAX_VALUE)
			{
				model = new SharedMesh("brick" + i, (TriMesh)manager
						.get(SHResourceManager.TYPE_MODEL, "superbrick"));
			}
			else 
			{
				model = new SharedMesh("brick" + i, (TriMesh)manager
						.get(SHResourceManager.TYPE_MODEL, "brick"));
			}
			Vector3f location = new Vector3f(random.nextFloat() * 16 - 8, 
					 random.nextFloat() * 8, 0);
			model.setLocalTranslation(location);
			brick.setModel(model);
			
			level.addBrick(brick);
		}
		
		SHPaddle paddle = new SHPaddle((Spatial)manager
				.get(SHResourceManager.TYPE_MODEL, "paddle"));
		paddle.setLocation(0, -7, 0);
		level.setPaddle(paddle);
		
		Spatial ballModel = (Spatial)manager
				.get(SHResourceManager.TYPE_MODEL, "ball");
		ballModel = new SharedMesh("ball mesh", (TriMesh)ballModel);
		ballModel.setModelBound(new BoundingSphere());
		ballModel.updateModelBound();
		SHBall ball = new SHBall(ballModel);
		ball.setLocation(-0, -6.3f, 0);
		ball.setVelocity(-3 ,3 ,0);
		ball.getModel().addController(new SHPaddleSticker(ball, paddle.getModel()));
		level.addBall(ball);
		
	}
	
	private static void setupInputHandler(SHLevel level)
	{
		InputHandler input = new SHPaddleInputHandler(level.getPaddle().getModel());
		level.setInputHandler(input);
		level.getInputHandler().addAction(new SHMouseBallLauncher(level));
	}
	
	private static SHBonus createIncBonus(String name)
	{
		Box box = new Box(name, new Vector3f(0, 0, 0), 0.25f, 0.25f, 0.25f);
		box.setModelBound(new BoundingBox());
		box.updateModelBound();
		
		MaterialState ms = DisplaySystem.getDisplaySystem().getRenderer()
				.createMaterialState();
		ms.setEmissive(ColorRGBA.green);
		box.setRenderState(ms);
		
//		return new SHIncPaddleWidthBonus(box);
//		return new SHIncBallSpeedBonus(box);
//		return new SHBottomWallBonus(box);
//		return new SHStickyPaddleBonus(box);
		return new SHDoubleBallBonus(box);
	}
	
	private static SHBonus createDecBonus(String name)
	{
		Box box = new Box(name, new Vector3f(0, 0, 0), 0.25f, 0.25f, 0.25f);
		box.setModelBound(new BoundingBox());
		box.updateModelBound();
		
		MaterialState ms = DisplaySystem.getDisplaySystem().getRenderer()
				.createMaterialState();
		ms.setEmissive(ColorRGBA.red);
		box.setRenderState(ms);

//		return new SHDecPaddleWidthBonus(box);
//		return new SHDecBallSpeedBonus(box);
		return new SHStickyPaddleBonus(box);
	}
	
}
