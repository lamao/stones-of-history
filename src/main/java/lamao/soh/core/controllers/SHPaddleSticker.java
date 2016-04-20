/*
 * SHPaddleSticker.java 26.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.controllers;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Follows the given target (assumed it is paddle model).
 * @author lamao
 */
@SuppressWarnings("serial")
public class SHPaddleSticker extends AbstractControl {
    private Spatial target = null;

    /** Distance to target */
    private Vector3f distance = null;

    public SHPaddleSticker(
                    Spatial target) {
        this.target = target;
    }

    public Spatial getTarget() {
        return target;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        if (spatial != null) {
            distance = target.getLocalTranslation().subtract(spatial.getLocalTranslation());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void controlUpdate(float time) {
        getSpatial().setLocalTranslation(target.getLocalTranslation().subtract(distance));
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }

}
