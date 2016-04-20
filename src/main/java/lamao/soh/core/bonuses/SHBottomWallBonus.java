/*
 * SHBottomWallBonus.java 27.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.bonuses;

import com.jme3.scene.Spatial;

import lamao.soh.core.EntityProperties;
import lamao.soh.core.SHScene;

/**
 * Activates bottom wall (e.i. player is not failed when ball contact with bottom wall
 * @author lamao
 */
@SuppressWarnings("serial")
public class SHBottomWallBonus extends SHBonus {
    public final static float DURATION = 5;

    public SHBottomWallBonus(
                    Spatial model) {
        super(model);
        setDuration(DURATION);
        setAddictive(true);
    }

    public SHBottomWallBonus() {
        this(null);
    }

    @Override
    public void apply(SHScene scene) {
        Spatial wall = scene.getEntity("bottom-wall", "bottom-wall", Spatial.class);
        wall.setUserData(EntityProperties.IS_ACTIVE, true);
    }

    @Override
    public void cleanup(SHScene scene) {
        Spatial wall = scene.getEntity("bottom-wall", "bottom-wall", Spatial.class);
        wall.setUserData(EntityProperties.IS_ACTIVE, false);
    }

}
