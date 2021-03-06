/*
 * SHBottomWall.java 01.05.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.entities;

import lamao.soh.core.SHEntity;

import com.jme3.scene.Spatial;

/**
 * Entity for bottom wall
 * @author lamao
 */
@SuppressWarnings("serial")
public class SHBottomWall extends SHEntity {

    /**
     * Is bottom wall active or not. If it is active ball is not destroyed after collision
     */
    private boolean active = false;

    public SHBottomWall() {
        super();
    }

    public SHBottomWall(
                    String type,
                    String name,
                    Spatial model) {
        super(type, name, model);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
