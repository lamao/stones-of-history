/* 
 * SHMain.java 25.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh;

import java.io.File;

import lamao.soh.console.SHConsoleState;
import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.core.SHCollisionTask;
import lamao.soh.core.SHGamePack;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;
import lamao.soh.core.collisionhandlers.SHBallBrickCollisionHandler;
import lamao.soh.core.collisionhandlers.SHBallPaddleCollisionHandler;
import lamao.soh.core.collisionhandlers.SHBallWallCollisionHandler;
import lamao.soh.core.collisionhandlers.SHBonusBottomWallCollisionHandler;
import lamao.soh.core.collisionhandlers.SHBonusPaddleCollisionHandler;
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

import com.jme.app.AbstractGame.ConfigShowMode;
import com.jme.scene.Spatial;
import com.jmex.game.StandardGame;
import com.jmex.game.state.GameStateManager;

/**
 * Enter point into the program.
 * @author lamao
 *
 */
public class SHMain
{
	private static StandardGame GAME = null;

	public static void main(String args[])
	{
		GAME = new StandardGame("Stones of History");
		GAME.setConfigShowMode(ConfigShowMode.AlwaysShow);
		GAME.getSettings().setFramerate(2000);
		GAME.start();
		
		SHGamePack.initDefaults();
		SHGamePack.context = new SHBreakoutGameContext();
		SHGamePack.dispatcher.addHandler("all", new SHEventLogger());
		
		SHGamePack.manager.loadAll(new File(
				"data/epochs/test_epoch/appearence.txt"));
		SHSceneLoader loader = new SHSceneLoader(SHGamePack.scene);
		loader.load(new File("data/test/test-level.dps"));
		levelStartupScript();
		SHGamePack.input = new SHBreakoutInputHandler(SHGamePack.scene
				.getEntity("paddle", "paddle"));
		SHGamePack.input.addAction(new SHMouseBallLauncher(SHGamePack.scene));
		
		SHConsoleState console = new SHConsoleState(SHConsoleState.STATE_NAME);
		GameStateManager.getInstance().attachChild(console);
		
		SHLevelState levelState = new SHLevelState();
		levelState.setScene(SHGamePack.scene);
		
		GameStateManager.getInstance().attachChild(levelState);
		levelState.setActive(true);
	}
	
	public static void levelStartupScript()
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
		SHEventDispatcher dispatcher = SHGamePack.dispatcher;
		scene.addCollisionTask(new SHCollisionTask("ball", "wall", false));
		scene.addCollisionTask(new SHCollisionTask("ball", "paddle", false));
		scene.addCollisionTask(new SHCollisionTask("ball", "brick", true));
		scene.addCollisionTask(new SHCollisionTask("bonus", "bottom-wall", false));
		scene.addCollisionTask(new SHCollisionTask("bonus", "paddle", false));
		
		dispatcher.addHandler("scene-collision-ball-wall", new SHBallWallCollisionHandler());
		dispatcher.addHandler("scene-collision-ball-paddle", new SHBallPaddleCollisionHandler());
		dispatcher.addHandler("scene-collision-ball-brick", new SHBallBrickCollisionHandler());
		dispatcher.addHandler("level-brick-deleted", new SHBrickDeletedEventHandler());
		dispatcher.addHandler("scene-collision-bonus-bottom-wall", new SHBonusBottomWallCollisionHandler());
		dispatcher.addHandler("scene-collision-bonus-paddle", new SHBonusPaddleCollisionHandler());
	}
}
