/*
 * SHBallBottomWallCollisionHandler.java 01.05.2010 Copyright 2010 Stones of History All rights
 * reserved.
 */
package lamao.soh.core.collisionhandlers;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import lamao.soh.core.EntityProperties;
import lamao.soh.core.SHUtils;
import lamao.soh.states.LevelState;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;
import lamao.soh.core.SHScene;

/**
 * Handler for ball collision with bottom wall
 * @author lamao
 */
public class SHBallBottomWallCollisionHandler extends SHAbstractCollisionHandler {
    /**
     * @param dispatcher
     * @param levelState
     */
    public SHBallBottomWallCollisionHandler(
                    SHEventDispatcher dispatcher,
                    LevelState levelState) {
        super(dispatcher, levelState);
    }

    @Override
    public void processEvent(SHEvent event) {
        Spatial ball = event.getParameter("src", Spatial.class);
        Spatial wall = event.getParameter("dst", Spatial.class);
        SHScene scene = getLevelState().getScene();

        if (wall.getUserData(EntityProperties.IS_ACTIVE)) {
            Vector3f ballVelocity = ball.getUserData(EntityProperties.VELOCITY);
            ballVelocity.z = -ballVelocity.z;
            dispatcher.addEvent("level-wall-hit", this);
        } else {
            scene.remove(ball);
            if (scene.get(SHUtils.getProperty(ball, EntityProperties.TYPE, String.class)) == null) {
                dispatcher.addEvent("level-failed", this);
            }
        }
    }

}
