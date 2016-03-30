/** 
 * SHScripts.java Jun 7, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.service;

import java.io.File;

import lamao.soh.SHConstants;
import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;
import lamao.soh.core.controllers.SHPaddleSticker;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHPaddle;
import lamao.soh.core.input.SHBreakoutInputHandler;
import lamao.soh.core.input.actions.SHMouseBallLauncher;
import lamao.soh.core.model.entity.SHEpoch;
import lamao.soh.core.model.entity.SHLevel;
import lamao.soh.utils.SHResourceManager;
import lamao.soh.utils.deled.SHSceneLoader;
import lamao.soh.utils.events.SHEventDispatcher;

import com.jme3.input.InputHandler;
import com.jme3.input.MouseInput;
import com.jme3.scene.Spatial;

/**
 * Class with script routines that should be moved to external script file. 
 * @author lamao
 *
 */
public class SHLevelService
{
	private static final String EPOCH_APPEARENCE_FILE = "appearence.txt";
	
	private SHEventDispatcher dispatcher;
	
	private SHScene scene;
	
	private SHBreakoutGameContext context;
	
	private SHResourceManager resourceManager;
	
	private SHSceneLoader sceneLoader;
	
	private SHConstants constants;
	
	private SHGameContextService gameContextService;
	
	public SHLevelService(SHEventDispatcher dispatcher, SHScene scene,
			SHBreakoutGameContext context,
			SHResourceManager resourceManager,
			SHSceneLoader sceneLoader,
			SHConstants constants,
			SHGameContextService gameContextService)
	{
		this.dispatcher = dispatcher;
		this.scene = scene;
		this.context = context;
		this.resourceManager = resourceManager;
		this.sceneLoader = sceneLoader;
		this.constants = constants;
		this.gameContextService = gameContextService;
	}
	
	public void loadEpochAppearence(String epochId)
	{
		File file = new File(constants.EPOCHS_DIR + "/" + epochId + "/" + EPOCH_APPEARENCE_FILE);
		resourceManager.clear();
		resourceManager.loadAll(file);
	}
	
	public void loadLevelScene(SHEpoch epoch, SHLevel level)
	{
		String levelSceneFile = constants.EPOCHS_DIR + "/" + epoch.getId() + "/"
				+ level.getScene();
		sceneLoader.load(new File(levelSceneFile));
		levelStartupScript();
	}
	
	private final void levelStartupScript()
	{
		SHBall ball = new SHBall();
		ball.setType("ball");
		ball.setName("ball" + ball);
		Spatial model = (Spatial)resourceManager.get(
				SHResourceManager.TYPE_MODEL, "ball");
		model = SHUtils.createSharedModel("shared-ball", model);
		ball.setModel(model);
		
		SHPaddle paddle = new SHPaddle();
		paddle.setType("paddle");
		paddle.setName("paddle");
		model = (Spatial)resourceManager.get(
				SHResourceManager.TYPE_MODEL, "paddle");
		paddle.setModel(model);
		paddle.setLocation(0, 0, 7);
		
		
		ball.setLocation(0, 0, 7);
		ball.setVelocity(constants.DEFAULT_BALL_VELOCITY.clone());
		ball.addController(new SHPaddleSticker(ball, paddle));
		
		scene.add(paddle);
		scene.add(ball);
		
		InputHandler inputHandler = new SHBreakoutInputHandler(paddle);
		inputHandler.addAction(new SHMouseBallLauncher(scene, MouseInput.get()));
		scene.setInGameInputHandler(inputHandler);
		
		scene.getRootNode().updateRenderState();
		scene.getRootNode().updateGeometricState(0, true);
		gameContextService.updateNumberOfDeletableBricks(context);
		dispatcher.deleteEvents();
	}
	
}
