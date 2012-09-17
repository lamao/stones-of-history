/* 
 * SHScripts.java Jun 7, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import lamao.soh.console.SHConsoleState;
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
import lamao.soh.utils.SHResourceManager;
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
	public final static void levelStartupScript()
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
		
		SHGamePack.scene.add(paddle);
		SHGamePack.scene.add(ball);
		
		SHScene scene = SHGamePack.scene;
		
		SHCollisionProcessor collisionProcessor = new SHCollisionProcessor(SHGamePack.dispatcher);
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
		SHGamePack.input.addAction(new SHMouseBallLauncher(SHGamePack.scene));
		
		scene.getRootNode().updateRenderState();
		scene.getRootNode().updateGeometricState(0, true);
		SHGamePack.context = new SHBreakoutGameContext();
		SHGamePack.dispatcher.deleteEvents();
	}
	
	public final static void gameStartupScript()
	{
		SHGamePack.initDefaults();
		
		SHEventDispatcher dispatcher = SHGamePack.dispatcher;
		dispatcher.addHandler("all", new SHEventLogger(dispatcher));
		dispatcher.addHandler("scene-collision-ball-wall", 
				new SHBallWallCollisionHandler(dispatcher, SHGamePack.scene));
		dispatcher.addHandler("scene-collision-ball-paddle", 
				new SHBallPaddleCollisionHandler(dispatcher, SHGamePack.scene));
		dispatcher.addHandler("scene-collision-ball-brick", 
				new SHBallBrickCollisionHandler(dispatcher, SHGamePack.scene));
		dispatcher.addHandler("level-brick-deleted", 
				new SHBrickDeletedEventHandler(dispatcher, SHGamePack.context));
		dispatcher.addHandler("scene-collision-bonus-bottom-wall", 
				new SHBonusBottomWallCollisionHandler(dispatcher, SHGamePack.scene));
		dispatcher.addHandler("scene-collision-bonus-paddle", 
				new SHBonusPaddleCollisionHandler(dispatcher, SHGamePack.scene));
		dispatcher.addHandler("scene-collision-ball-bottom-wall", 
				new SHBallBottomWallCollisionHandler(dispatcher, SHGamePack.scene));
		dispatcher.addHandler("scene-collision-bullet-brick", 
				new SHBulletBrickCollisionHandler(dispatcher, SHGamePack.scene));
		dispatcher.addHandler("scene-collision-bullet-wall", 
				new SHBulletWallCollisionHandler(dispatcher, SHGamePack.scene));
		
		SHConsoleState console = new SHConsoleState(SHConsoleState.STATE_NAME);
		GameStateManager.getInstance().attachChild(console);
	}

}
