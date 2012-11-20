/** 
 * SHScripts.java Jun 7, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import java.io.File;
import lamao.soh.core.controllers.SHPaddleSticker;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHPaddle;
import lamao.soh.core.input.SHBreakoutInputHandler;
import lamao.soh.core.input.SHMouseBallLauncher;
import lamao.soh.core.service.SHGameContextService;
import lamao.soh.utils.SHResourceManager;
import lamao.soh.utils.deled.SHSceneLoader;
import lamao.soh.utils.events.SHEventDispatcher;

import com.jme.input.InputHandler;
import com.jme.input.MouseInput;
import com.jme.scene.Spatial;

/**
 * Class with script routines that should be moved to external script file. 
 * @author lamao
 *
 */
public class SHScripts
{
	private SHEventDispatcher dispatcher;
	
	private SHScene scene;
	
	private SHBreakoutGameContext context;
	
	private SHResourceManager resourceManager;
	
	private SHSceneLoader sceneLoader;
	
	public SHScripts(SHEventDispatcher dispatcher, SHScene scene,
			SHBreakoutGameContext context,
			SHResourceManager resourceManager,
			SHSceneLoader sceneLoader)
	{
		this.dispatcher = dispatcher;
		this.scene = scene;
		this.context = context;
		this.resourceManager = resourceManager;
		this.sceneLoader = sceneLoader;
	}
	
	public final void levelStartupScript()
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
		paddle.setLocation(0, -7, 0);
		
		
		ball.setLocation(0, -6.3f, 0);
		ball.setVelocity(-3 ,3 ,0);
		ball.addController(new SHPaddleSticker(ball, paddle));
		
		scene.add(paddle);
		scene.add(ball);
		
		InputHandler inputHandler = new SHBreakoutInputHandler(paddle);
		inputHandler.addAction(new SHMouseBallLauncher(scene, MouseInput.get()));
		scene.setInGameInputHandler(inputHandler);
		
		scene.getRootNode().updateRenderState();
		scene.getRootNode().updateGeometricState(0, true);
		SHGameContextService contextService = new SHGameContextService(scene);
		contextService.updateNumberOfDeletableBricks(context);
		dispatcher.deleteEvents();
	}
	
	public final void loadEpochScript(String file)
	{
		resourceManager.loadAll(new File(file));
	}
	
	public final void loadLevelScript(String file)
	{
		sceneLoader.load(new File(file));
		levelStartupScript();
	}
	
}
