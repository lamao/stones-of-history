/*
 * SHDecPaddleWidthBonus.java 27.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.bonuses;

import lamao.soh.core.SHScene;

import com.jme3.scene.Spatial;

/**
 * Decreases paddle width by 10%
 * @author lamao
 */
@SuppressWarnings("serial")
public class SHDecPaddleWidthBonus extends SHBonus {
    public final static float DECREASE_PERCENT = 0.2f;
    public final static float DURATION = 5;

    public SHDecPaddleWidthBonus(
                    Spatial model) {
        super(model);
        setDuration(DURATION);
    }

    public SHDecPaddleWidthBonus() {
        this(null);
    }

    @Override
    public void apply(SHScene scene) {
        Spatial paddle = scene.getEntity("paddle", "paddle");
        paddle.getLocalScale().x *= (1 - DECREASE_PERCENT);
        paddle.updateModelBound();
    }

    @Override
    public void cleanup(SHScene scene) {
        Spatial paddle = scene.getEntity("paddle", "paddle");
        paddle.getLocalScale().x /= (1 - DECREASE_PERCENT);
        paddle.updateModelBound();
    }
}
