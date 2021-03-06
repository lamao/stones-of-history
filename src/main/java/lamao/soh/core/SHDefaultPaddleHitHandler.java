/*
 * SHDefaultPaddleHitHandler.java 24.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core;

import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHPaddle;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;

/**
 * Default paddle hit handler. When ball hits paddle its new velocity is calculated by the formula
 * <code>angle = arccos((x - 0.5 * l) / (0.5 * l))
 * </code> where x - contact point on the paddle, l - total paddle length along X-axis.
 * @author lamao
 */
public class SHDefaultPaddleHitHandler implements ISHPaddleHitHandler {
    @Override
    public void execute(SHBall ball, SHPaddle paddle) {
        if (ball.getLocation().z < paddle.getLocation().z && ball.getVelocity().z >= 0) {
            BoundingBox paddleBound = (BoundingBox) paddle.getModel().getWorldBound();
            float ballPos = ball.getLocation().x - paddle.getLocation().x
                            + paddleBound.getXExtent();
            float paddleWidth = paddleBound.getXExtent() * 2;
            float speed = ball.getVelocity().length();
            Vector3f newVelocity = null;

            if (ballPos < 0 || ballPos > paddleWidth) {
                newVelocity = ball.getVelocity();
                newVelocity.x = -newVelocity.x;
                newVelocity.z = -newVelocity.z;
            } else {
                double newAngle = Math.acos((ballPos - 0.5f * paddleWidth) / (0.5f * paddleWidth));
                newVelocity = new Vector3f((float) (speed * Math.cos(newAngle)), 0,
                                -(float) (speed * Math.sin(newAngle)));
            }
            ball.setVelocity(newVelocity);
        }
    }

}
