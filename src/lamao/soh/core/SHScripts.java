/* 
 * SHScripts.java Jun 7, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import java.io.File;
import java.util.logging.Logger;

import lamao.soh.SHOptions;
import lamao.soh.console.SHActivateBonusCommand;
import lamao.soh.console.SHBasicCommand;
import lamao.soh.console.SHConsoleState;
import lamao.soh.console.SHDrawBoundsCommand;
import lamao.soh.console.SHDrawNormalsCommand;
import lamao.soh.console.SHFPSInputCommand;
import lamao.soh.console.SHLoadLevelCommand;
import lamao.soh.console.SHPauseLevelCommand;
import lamao.soh.console.SHSetBonusCommand;
import lamao.soh.console.SHWireFrameCommand;
import lamao.soh.core.collisionhandlers.SHBallBottomWallCollisionHandler;
import lamao.soh.core.collisionhandlers.SHBallBrickCollisionHandler;
import lamao.soh.core.collisionhandlers.SHBallPaddleCollisionHandler;
import lamao.soh.core.collisionhandlers.SHBallWallCollisionHandler;
import lamao.soh.core.collisionhandlers.SHBonusBottomWallCollisionHandler;
import lamao.soh.core.collisionhandlers.SHBonusPaddleCollisionHandler;
import lamao.soh.core.collisionhandlers.SHBulletBrickCollisionHandler;
import lamao.soh.core.collisionhandlers.SHBulletWallCollisionHandler;
import lamao.soh.core.controllers.SHPaddleSticker;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHPaddle;
import lamao.soh.core.eventhandlers.SHBrickDeletedEventHandler;
import lamao.soh.core.input.SHBreakoutInputHandler;
import lamao.soh.core.input.SHMouseBallLauncher;
import lamao.soh.states.SHLevelState;
import lamao.soh.utils.SHResourceManager;
import lamao.soh.utils.deled.SHSceneLoader;
import lamao.soh.utils.events.SHEventDispatcher;
import lamao.soh.utils.events.SHEventLogger;

import com.jme.scene.Spatial;
import com.jmex.game.state.GameStateManager;

/**
 * Class with script routines that should be moved to external script file. 
 * @author lamao
 *
 */
public class SHScripts
{
	private SHEventDispatcher dispatcher;
	
	private SHScene scene;
	
	public SHScripts(SHEventDispatcher dispatcher, SHScene scene)
	{
		this.dispatcher = dispatcher;
		this.scene = scene;
	}
	
	public final void levelStartupScript()
	{
		SHBall ball = new SHBall();
		ball.setType("ball");
		ball.setName("ball" + ball);
		Spatial model = (Spatial)SHGamePack.manager.get(
				SHResourceManager.TYPE_MODEL, "ball");
		model = SHUtils.createSharedModel("shared-ball", model);
		ball.setModel(model);
		
		SHPaddle paddle = new SHPaddle();
		paddle.setType("paddle");
		paddle.setName("paddle");
		model = (Spatial)SHGamePack.manager.get(
				SHResourceManager.TYPE_MODEL, "paddle");
		paddle.setModel(model);
		paddle.setLocation(0, -7, 0);
		
		
		ball.setLocation(0, -6.3f, 0);
		ball.setVelocity(-3 ,3 ,0);
		ball.addController(new SHPaddleSticker(ball, paddle));
		
		scene.add(paddle);
		scene.add(ball);
		
		SHCollisionProcessor collisionProcessor = new SHCollisionProcessor(dispatcher);
		collisionProcessor.addCollisionTask(new SHCollisionTask("ball", "wall", false));
		collisionProcessor.addCollisionTask(new SHCollisionTask("ball", "paddle", false));
		collisionProcessor.addCollisionTask(new SHCollisionTask("ball", "brick", true));
		collisionProcessor.addCollisionTask(new SHCollisionTask("bonus", "bottom-wall", false));
		collisionProcessor.addCollisionTask(new SHCollisionTask("bonus", "paddle", false));
		collisionProcessor.addCollisionTask(new SHCollisionTask("ball", "bottom-wall", false));
		collisionProcessor.addCollisionTask(new SHCollisionTask("bullet", "brick", false));
		collisionProcessor.addCollisionTask(new SHCollisionTask("bullet", "wall", false));
		scene.setCollisionProcessor(collisionProcessor);
		
		SHGamePack.input = new SHBreakoutInputHandler(paddle);
		SHGamePack.input.addAction(new SHMouseBallLauncher(scene));
		
		scene.getRootNode().updateRenderState();
		scene.getRootNode().updateGeometricState(0, true);
		((SHBreakoutGameContext)SHGamePack.context).updateNumDeletableBricks();
		dispatcher.deleteEvents();
	}
	
	public final void gameStartupScript()
	{
		Logger.getLogger("").setLevel(SHOptions.LogLevel);
		SHGamePack.initDefaults();		
		SHGamePack.context = new SHBreakoutGameContext(scene);
		
		dispatcher.addHandler("all", new SHEventLogger(dispatcher));
		dispatcher.addHandler("scene-collision-ball-wall", 
				new SHBallWallCollisionHandler(dispatcher, scene));
		dispatcher.addHandler("scene-collision-ball-paddle", 
				new SHBallPaddleCollisionHandler(dispatcher, scene));
		dispatcher.addHandler("scene-collision-ball-brick", 
				new SHBallBrickCollisionHandler(dispatcher, scene));
		dispatcher.addHandler("level-brick-deleted", 
				new SHBrickDeletedEventHandler(dispatcher, SHGamePack.context));
		dispatcher.addHandler("scene-collision-bonus-bottom-wall", 
				new SHBonusBottomWallCollisionHandler(dispatcher, scene));
		dispatcher.addHandler("scene-collision-bonus-paddle", 
				new SHBonusPaddleCollisionHandler(dispatcher, scene));
		dispatcher.addHandler("scene-collision-ball-bottom-wall", 
				new SHBallBottomWallCollisionHandler(dispatcher, scene));
		dispatcher.addHandler("scene-collision-bullet-brick", 
				new SHBulletBrickCollisionHandler(dispatcher, scene));
		dispatcher.addHandler("scene-collision-bullet-wall", 
				new SHBulletWallCollisionHandler(dispatcher, scene));
		
		SHConsoleState console = new SHConsoleState(SHConsoleState.STATE_NAME);
		GameStateManager.getInstance().attachChild(console);
	}
	
	public final void loadEpochScript(String file)
	{
		SHGamePack.manager.loadAll(new File(file));
	}
	
	public final void loadLevelScript(String file)
	{
		SHSceneLoader loader = new SHSceneLoader(scene);
		loader.load(new File(file));
		levelStartupScript();
	}
	
	public final void initializeConsole(SHLevelState levelState) 
	{
		SHConsoleState console = (SHConsoleState)GameStateManager.getInstance()
			.getChild(SHConsoleState.STATE_NAME);
		SHScene scene = levelState.getScene();
		console.add("wired", new SHWireFrameCommand(levelState.getRootNode()));
		console.add("bounds", new SHDrawBoundsCommand(levelState));
		console.add("normals", new SHDrawNormalsCommand(levelState));
		console.add("set-bonus", new SHSetBonusCommand(scene));
		console.add("load-level", new SHLoadLevelCommand(scene, this));
		console.add("free-camera", new SHFPSInputCommand(scene));
		console.add("activate-bonus", new SHActivateBonusCommand(
				dispatcher, scene));
		console.add("pause", new SHPauseLevelCommand(levelState));
	}

}
