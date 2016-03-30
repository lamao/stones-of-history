/*
 * SHBrick.java 23.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.entities;

import lamao.soh.core.SHEntity;
import lamao.soh.core.bonuses.SHBonus;

import com.jme3.scene.Spatial;

/**
 * Brick game entity.<br>
 * <b>NOTE:</b> Model bound for brick must be only <code>BoundingBox</code>
 * @author lamao
 */
@SuppressWarnings("serial")
public class SHBrick extends SHEntity {
    public static final int SUPER_STRENGTH = Integer.MAX_VALUE;

    /** Strength (or lives) of the brick */
    private int strength;

    /** true if ball can move thought the brick */
    private boolean glass;

    /** name of bonus. Null if brick doesn't have associated bonus */
    private SHBonus bonus = null;

    public SHBrick(
                    Spatial model,
                    int strength,
                    boolean glass) {
        super(model);
        this.strength = strength;
        this.glass = glass;
    }

    /** Creates default brick with strength 1 */
    public SHBrick(
                    Spatial model) {
        this(model, 1, false);
    }

    /** Creates default brick with strength 1 and without model */
    public SHBrick() {
        this(null);
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public boolean isGlass() {
        return glass;
    }

    public void setGlass(boolean glass) {
        this.glass = glass;
    }

    public SHBonus getBonus() {
        return bonus;
    }

    public void setBonus(SHBonus bonus) {
        this.bonus = bonus;
    }

    /**
     * Invoked when brick is hit. Decreases its strength by one if it is not Integer.MAX_VALUE
     * (super brick).
     */
    public void hit() {
        if (strength != SUPER_STRENGTH) {
            strength--;
        }
    }
}
