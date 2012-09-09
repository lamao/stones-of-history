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
		ball.getRoot().addController(new SHPaddleSticker(ball, paddle.getRoot()));
		
		SHGamePack.scene.addEntity(paddle);
		SHGamePack.scene.addEntity(ball);
		
		SHScene scene = SHGamePack.scene;
		scene.addCollisionTask(new SHCollisionTask("ball", "wall", false));
		scene.addCollisionTask(new SHCollisionTask("ball", "paddle", false));
		scene.addCollisionTask(new SHCollisionTask("ball", "brick", true));
		scene.addCollisionTask(new SHCollisionTask("bonus", "bottom-wall", false));
		scene.addCollisionTask(new SHCollisionTask("bonus", "paddle", false));
		scene.addCollisionTask(new SHCollisionTask("ball", "bottom-wall", false));
		scene.addCollisionTask(new SHCollisionTask("bullet", "brick", false));
		scene.addCollisionTask(new SHCollisionTask("bullet", "wall", false));
		
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
		SHGamePack.dispatcher.addHandler("all", new SHEventLogger());
		
		SHEventDispatcher dispatcher = SHGamePack.dispatcher;
		dispatcher.addHandler("scene-collision-ball-wall", new SHBallWallCollisionHandler());
		dispatcher.addHandler("scene-collision-ball-paddle", new SHBallPaddleCollisionHandler());
		dispatcher.addHandler("scene-collision-ball-brick", new SHBallBrickCollisionHandler());
		dispatcher.addHandler("level-brick-deleted", new SHBrickDeletedEventHandler());
		dispatcher.addHandler("scene-collision-bonus-bottom-wall", new SHBonusBottomWallCollisionHandler());
		dispatcher.addHandler("scene-collision-bonus-paddle", new SHBonusPaddleCollisionHandler());
		dispatcher.addHandler("scene-collision-ball-bottom-wall", 
				new SHBallBottomWallCollisionHandler(SHGamePack.dispatcher));
		dispatcher.addHandler("scene-collision-bullet-brick", new SHBulletBrickCollisionHandler());
		dispatcher.addHandler("scene-collision-bullet-wall", new SHBulletWallCollisionHandler());
		
		SHConsoleState console = new SHConsoleState(SHConsoleState.STATE_NAME);
		GameStateManager.getInstance().attachChild(console);
	}

}
