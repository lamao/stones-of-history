/*
 * SHDefaultBallMover.java 24.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.controllers;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import lamao.soh.core.entities.SHBall;

/**
 * Moves ball taking into account its velocity
 * @author lamao
 */
@SuppressWarnings("serial")
public class SHDefaultBallMover extends AbstractControl {

    /**
     * {@inheritDoc}
     */
    @Override
    public void controlUpdate(float time) {
        if (getSpatial() != null && getSpatial() instanceof SHBall) {
            SHBall ball = (SHBall) getSpatial();
            Vector3f newLocation = getSpatial().getLocalTranslation().add(ball.getVelocity().mult(time));
            getSpatial().setLocalTranslation(newLocation);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }

}
