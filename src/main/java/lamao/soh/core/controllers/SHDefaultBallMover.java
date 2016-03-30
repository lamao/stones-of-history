/*
 * SHDefaultBallMover.java 24.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.controllers;

import com.jme3.math.Vector3f;
import lamao.soh.core.entities.SHBall;

/**
 * Moves ball taking into account its velocity
 * @author lamao
 */
@SuppressWarnings("serial")
public class SHDefaultBallMover extends SHBallMover {

    public SHDefaultBallMover(SHBall ball) {
        super(ball);
    }

    public SHDefaultBallMover() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void controlUpdate(float time) {
        if (getBall() != null) {
            Vector3f newLocation = getBall().getLocalTranslation().add(getBall().getVelocity().mult(time));
            getBall().setLocalTranslation(newLocation);
            getBall().updateModelBound();
        }
    }

}
