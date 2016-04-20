/*
 * SHDefaultBallMover.java 24.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.controllers;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import lamao.soh.core.EntityProperties;

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
        Vector3f ballVelocity = getSpatial().getUserData(EntityProperties.VELOCITY);
        Vector3f newLocation = getSpatial().getLocalTranslation().add(ballVelocity.mult(time));
        getSpatial().setLocalTranslation(newLocation);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }

}
