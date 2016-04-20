/**
 * SHScripts.java Jun 7, 2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.service;

import java.io.File;

import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.bounding.BoundingSphere;
import com.jme3.scene.Node;
import lamao.soh.SHConstants;
import lamao.soh.core.EntityConstants;
import lamao.soh.core.EntityProperties;
import lamao.soh.core.EntityTypes;
import lamao.soh.core.ISHEntityFactory;
import lamao.soh.core.SHBreakoutEntityFactory;
import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.core.SHEntity;
import lamao.soh.core.SHScene;
import lamao.soh.core.controllers.SHPaddleSticker;
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

    private ISHEntityFactory entityFactory;

    public SHLevelService(
                    SHEventDispatcher dispatcher,
                    SHScene scene,
                    SHBreakoutGameContext context,
                    SHConstants constants,
                    SHGameContextService gameContextService,
                    AssetManager assetManager,
                    ISHEntityFactory entityFactory) {
        this.dispatcher = dispatcher;
        this.scene = scene;
        this.context = context;
        this.constants = constants;
        this.gameContextService = gameContextService;
        this.assetManager = assetManager;
        this.entityFactory = entityFactory;
    }

    public void loadLevelScene(SHEpoch epoch, SHLevel level) {
        String pathToEpochModels = getPathToEpoch(epoch);
        assetManager.registerLocator(pathToEpochModels, FileLocator.class);

        Node scene = (Node) assetManager.loadModel(level.getScene());
        createGameEntities(scene);

        this.scene.setRootNode(scene);
        levelStartupScript(epoch);

        assetManager.unregisterLocator(pathToEpochModels, FileLocator.class);

    }

    private String getPathToEpoch(SHEpoch epoch) {
        return constants.EPOCHS_DIR + File.separator + epoch.getId() + File.separator;
    }

    private void createGameEntities(Node scene) {

        for (Spatial group : scene.getChildren()) {
            if (group instanceof Node) {
                Node groupAsNode = (Node) group;
                for (Spatial entityModel : groupAsNode.getChildren()) {
                    SHEntity entity = entityFactory.createEntity(entityModel);
                    if (entity != null) {
                        entity.setModel(entityModel);
                        groupAsNode.detachChild(entityModel);
                        groupAsNode.attachChild(entity);
                    }
                }
            }
        }
    }

    private final void levelStartupScript(SHEpoch epoch) {
        String ballLocation = epoch.getCommonResources().get("ball").getLocation();
        Spatial ball = assetManager.loadModel(ballLocation);
        ball.setUserData(EntityProperties.TYPE, EntityTypes.BALL);
        ball.setName("ball" + ball);
        ball.setLocalTranslation(0, 0, 6);
        ball.setUserData(EntityProperties.VELOCITY, EntityConstants.BALL_DEFAULT_VELOCITY.clone());


        SHPaddle paddle = new SHPaddle();
        paddle.setType("paddle");
        paddle.setName("paddle");
        String paddleLocation = epoch.getCommonResources().get("paddle").getLocation();
        paddle.setModel(assetManager.loadModel(paddleLocation));
        paddle.setLocation(0, 0, 7);

        ball.addControl(new SHPaddleSticker(paddle));

        scene.add(paddle);
        scene.add(ball);

        gameContextService.updateNumberOfDeletableBricks(context);
        dispatcher.deleteEvents();
    }

}
