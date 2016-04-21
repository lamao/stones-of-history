/*
 * SHBallPaddleCollisionHandler.java 01.05.2010 Copyright 2010 Stones of History All rights
 * reserved.
 */
package lamao.soh.core.collisionhandlers;

import com.jme3.math.Vector3f;
import lamao.soh.core.EntityProperties;
import lamao.soh.core.controllers.SHDefaultBallMover;
import lamao.soh.core.controllers.SHPaddleSticker;
import lamao.soh.states.LevelState;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

import com.jme3.scene.Spatial;

/**
 * @author lamao
 */
public class SHStickyBallPaddleCollisionHandler extends SHAbstractCollisionHandler {

    public SHStickyBallPaddleCollisionHandler(
        SHEventDispatcher dispatcher,
        LevelState levelState) {
        super(dispatcher, levelState);
    }

    @Override
    public void processEvent(SHEvent event) {
        Spatial ball = event.getParameter("src", Spatial.class);
        Spatial paddle = event.getParameter("dst", Spatial.class);

        dispatcher.addEvent("level-paddle-hit", this, null);
        processHit(ball, paddle);
    }

    private void processHit(Spatial ball, Spatial paddle) {
        Vector3f ballVelocity = ball.getUserData(EntityProperties.VELOCITY);
        if (ballVelocity.z > 0) {
            ball.removeControl(SHDefaultBallMover.class);
            ball.addControl(new SHPaddleSticker(paddle));
        }
    }

}
