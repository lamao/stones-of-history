/*
 * SHBonus.java 26.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.bonuses;

import com.jme3.scene.Spatial;

import lamao.soh.core.SHScene;

/**
 * Bonus game entity.
 * @author lamao
 */
@SuppressWarnings("serial")
public abstract class SHBonus {
    /** Duration of bonus. NaN for persistent bonus (e.g. additional life) */
    private float duration = 0;

    /**
     * Defines whether bonus duration is added or new bonus should be created.
     */
    private boolean addictive = false;

    public SHBonus(
                    Spatial model) {
        throw new UnsupportedOperationException();
//        super(model);
    }

    public SHBonus() {
        this(null);
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void decreaseDuration(float time) {
        duration -= time;
    }

    public void increaseDuration(float time) {
        duration += time;
    }

    public boolean isAddictive() {
        return addictive;
    }

    public void setAddictive(boolean addictive) {
        this.addictive = addictive;
    }

    /** Apply (activate) this bonus */
    public abstract void apply(SHScene scene);

    /** Cleanup (deactivate) this bonus */
    public abstract void cleanup(SHScene scene);

}
