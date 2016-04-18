/*
 * SHBallBottomWallCollisionHandler.java 01.05.2010 Copyright 2010 Stones of History All rights
 * reserved.
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHBottomWall;
import lamao.soh.states.SHLevelState;
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
                    SHLevelState levelState) {
        super(dispatcher, levelState);
    }

    @Override
    public void processEvent(SHEvent event) {
        SHBall ball = event.getParameter("src", SHBall.class);
        SHBottomWall wall = event.getParameter("dst", SHBottomWall.class);
        SHScene scene = levelState.getScene();

        if (wall.isActive()) {
            ball.getVelocity().z = -ball.getVelocity().z;
            dispatcher.addEvent("level-wall-hit", this);
        } else {
            scene.remove(ball);
            if (scene.get(ball.getType()) == null) {
                dispatcher.addEvent("level-failed", this);
            }
        }
    }

}
