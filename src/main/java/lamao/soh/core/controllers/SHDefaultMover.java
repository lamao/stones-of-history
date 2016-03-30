/*
 * SHDefaultMover.java 27.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.controllers;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 * Moves node according given velocity
 * @author lamao
 */
@SuppressWarnings("serial")
public class SHDefaultMover extends AbstractControl {
    private Vector3f velocity;

    public SHDefaultMover() {
        velocity = new Vector3f(0, 0, 1);
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    @Override
    protected void controlUpdate(float tpf) {
        getSpatial().getLocalTranslation().addLocal(velocity.mult(tpf));
        getSpatial().updateModelBound();
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }

}
