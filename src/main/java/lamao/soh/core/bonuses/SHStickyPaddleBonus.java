/*
 * SHStickyPaddleBonus.java 27.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.bonuses;

import lamao.soh.core.SHDefaultPaddleHitHandler;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHStickyPaddleHitHandler;
import lamao.soh.core.controllers.SHDefaultBallMover;
import lamao.soh.core.controllers.SHPaddleSticker;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHPaddle;

import com.jme3.scene.Spatial;

/**
 * Makes paddle sticky.
 * @author lamao
 */
@SuppressWarnings("serial")
public class SHStickyPaddleBonus extends SHBonus {
    public final static float DURATION = 5;

    public SHStickyPaddleBonus(
                    Spatial model) {
        super(model);
        setDuration(DURATION);
        setAddictive(true);
    }

    public SHStickyPaddleBonus() {
        this(null);
    }

    @Override
    public void apply(SHScene scene) {
        SHPaddle paddle = scene.getEntity("paddle", "paddle", SHPaddle.class);
        paddle.setHitHandler(new SHStickyPaddleHitHandler());
    }

    @Override
    public void cleanup(SHScene scene) {
        SHPaddle paddle = scene.getEntity("paddle", "paddle", SHPaddle.class);
        paddle.setHitHandler(new SHDefaultPaddleHitHandler());

        for (Spatial entity : scene.get("ball")) {
            SHBall ball = (SHBall) entity;
            ball.removeControl(SHPaddleSticker.class);
            ball.addControl(new SHDefaultBallMover());
        }
    }

}
