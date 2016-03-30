/*
 * SHPaddleSticker.java 26.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.controllers;

import lamao.soh.core.entities.SHBall;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Follows the given target (assumed it is paddle model).
 * @author lamao
 */
@SuppressWarnings("serial")
public class SHPaddleSticker extends SHBallMover {
    private Spatial target = null;

    /** Distance to target */
    private Vector3f distance = null;

    public SHPaddleSticker(
                    SHBall ball,
                    Spatial target) {
        super(ball);
        this.target = target;
        distance = target.getLocalTranslation().subtract(ball.getLocation());
    }

    public Spatial getTarget() {
        return target;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void controlUpdate(float time) {
        getBall().setLocation(target.getLocalTranslation().subtract(distance));
    }

}
