/**
 * SHScripts.java Jun 7, 2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.service;

import java.io.File;

import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import lamao.soh.SHConstants;
import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.core.SHScene;
import lamao.soh.core.controllers.SHPaddleSticker;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHPaddle;
import lamao.soh.core.model.entity.SHEpoch;
import lamao.soh.core.model.entity.SHLevel;
import lamao.soh.utils.events.SHEventDispatcher;

import com.jme3.scene.Spatial;

/**
 * Class with script routines that should be moved to external script file.
 * @author lamao
 */
public class SHLevelService {
    private SHEventDispatcher dispatcher;

    private SHScene scene;

    private SHBreakoutGameContext context;

    private SHConstants constants;

    private SHGameContextService gameContextService;

    private AssetManager assetManager;

    public SHLevelService(
                    SHEventDispatcher dispatcher,
                    SHScene scene,
                    SHBreakoutGameContext context,
                    SHConstants constants,
                    SHGameContextService gameContextService,
                    AssetManager assetManager) {
        this.dispatcher = dispatcher;
        this.scene = scene;
        this.context = context;
        this.constants = constants;
        this.gameContextService = gameContextService;
        this.assetManager = assetManager;
    }

    public void loadLevelScene(SHEpoch epoch, SHLevel level) {
        String pathToEpochModels = getPathToEpoch(epoch);
        assetManager.registerLocator(pathToEpochModels, FileLocator.class);

        Spatial sceneModel = assetManager.loadModel(level.getScene());
        scene.getRootNode().attachChild(sceneModel);
        levelStartupScript(epoch);

        assetManager.unregisterLocator(pathToEpochModels, FileLocator.class);

    }

    private String getPathToEpoch(SHEpoch epoch) {
        return constants.EPOCHS_DIR + File.separator + epoch.getId() + File.separator;
    }

    private final void levelStartupScript(SHEpoch epoch) {
        SHBall ball = new SHBall();
        ball.setType("ball");
        ball.setName("ball" + ball);
        String ballLocation = epoch.getCommonResources().get("ball").getLocation();
        Spatial model = assetManager.loadModel(ballLocation);
        ball.setModel(model.clone());

        SHPaddle paddle = new SHPaddle();
        paddle.setType("paddle");
        paddle.setName("paddle");
        String paddleLocation = epoch.getCommonResources().get("paddle").getLocation();
        model = assetManager.loadModel(paddleLocation);
        paddle.setModel(model);
        paddle.setLocation(0, 0, 7);

        ball.setLocation(0, 0, 7);
        ball.setVelocity(constants.DEFAULT_BALL_VELOCITY.clone());
        ball.addControl(new SHPaddleSticker(ball, paddle));

        scene.add(paddle);
        scene.add(ball);

        gameContextService.updateNumberOfDeletableBricks(context);
        dispatcher.deleteEvents();
    }

}
