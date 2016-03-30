/*
 * SHBallWallCollisionHandler.java 01.05.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.SHEntity;
import lamao.soh.core.SHScene;
import lamao.soh.core.entities.SHBall;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 */
public class SHBallWallCollisionHandler extends SHAbstractCollisiontHandler {
    public SHBallWallCollisionHandler(
                    SHEventDispatcher dispatcher,
                    SHScene scene) {
        super(dispatcher, scene);
    }

    @Override
    public void processEvent(SHEvent event) {
        SHBall ball = event.getParameter("src", SHBall.class);
        SHEntity wall = event.getParameter("dst", SHEntity.class);

        if (wall.getName().equals("left-wall") || wall.getName().equals("right-wall")) {
            ball.getVelocity().x = -ball.getVelocity().x;
        } else if (wall.getName().equals("top-wall")) {
            ball.getVelocity().z = -ball.getVelocity().z;
        }

        dispatcher.addEventEx("level-wall-hit", this, "wall-type", wall.getName());
    }

}
