/*
 * SHBallPaddleCollisionHandler.java 01.05.2010 Copyright 2010 Stones of History All rights
 * reserved.
 */
package lamao.soh.core.collisionhandlers;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;
import lamao.soh.core.EntityProperties;
import lamao.soh.states.LevelState;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

import com.jme3.scene.Spatial;

/**
 * @author lamao
 */
public class SHDefaultBallPaddleCollisionHandler extends SHAbstractCollisionHandler {

    public SHDefaultBallPaddleCollisionHandler(
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

        if (ball.getLocalTranslation().z < paddle.getLocalTranslation().z && ballVelocity.z >= 0) {
            BoundingBox paddleBound = (BoundingBox) paddle.getWorldBound();
            float ballPos = ball.getLocalTranslation().x - paddle.getLocalTranslation().x
                + paddleBound.getXExtent();
            float paddleWidth = paddleBound.getXExtent() * 2;
            float speed = ballVelocity.length();
            Vector3f newVelocity = null;

            if (ballPos < 0 || ballPos > paddleWidth) {
                newVelocity = ballVelocity;
                newVelocity.x = -newVelocity.x;
                newVelocity.z = -newVelocity.z;
            } else {
                double newAngle = Math.acos((ballPos - 0.5f * paddleWidth) / (0.5f * paddleWidth));
                newVelocity = new Vector3f((float) (speed * Math.cos(newAngle)), 0,
                    -(float) (speed * Math.sin(newAngle)));
            }
            ball.setUserData(EntityProperties.VELOCITY, newVelocity);
        }
    }


}
