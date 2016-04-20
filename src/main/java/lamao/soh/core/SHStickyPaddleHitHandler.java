/*
 * SHStickyPaddleHitHandler.java 27.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import lamao.soh.core.controllers.SHDefaultBallMover;
import lamao.soh.core.controllers.SHPaddleSticker;
import lamao.soh.core.entities.SHPaddle;

/**
 * Changes ball mover to {@link SHPaddleSticker} and adds {@link lamao.soh.core.input.listeners.LaunchBallInputListener} action to
 * input handler.<br>
 * <b>NOTE:</b> if does this only if <code>ball.getVelocity().y < 0</code>
 * @author lamao
 */
public class SHStickyPaddleHitHandler implements ISHPaddleHitHandler {
    @Override
    public void execute(Spatial ball, SHPaddle paddle) {
        Vector3f ballVelocity = ball.getUserData(EntityProperties.VELOCITY);
        if (ballVelocity.z > 0) {
            ball.removeControl(SHDefaultBallMover.class);
            ball.addControl(new SHPaddleSticker(paddle));
        }
    }

}
