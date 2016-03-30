/*
 * SHDecBallSpeedBonus.java 27.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.bonuses;

import java.util.LinkedList;
import java.util.List;

import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;
import lamao.soh.core.entities.SHBall;

import com.jme3.scene.Spatial;

/**
 * Decreases ball speed
 * @author lamao
 */
@SuppressWarnings("serial")
public class SHDecBallSpeedBonus extends SHBonus {
    public final static float DURATION = 5;
    public final static float DEC_PERCENT = 0.5f;

    private List<SHBall> _balls = new LinkedList<SHBall>();

    public SHDecBallSpeedBonus(
                    Spatial model) {
        super(model);
        setDuration(DURATION);
    }

    public SHDecBallSpeedBonus() {
        this(null);
    }

    @Override
    public void apply(SHScene scene) {
        float speed;
        double angle;
        for (Spatial entity : scene.get("ball")) {
            SHBall ball = (SHBall) entity;
            speed = ball.getVelocity().length();
            angle = SHUtils.angle(ball.getVelocity());

            ball.getVelocity().x = (float) Math.cos(angle) * speed * (1 - DEC_PERCENT);
            ball.getVelocity().z = -(float) Math.sin(angle) * speed * (1 - DEC_PERCENT);

            _balls.add(ball);
        }
    }

    @Override
    public void cleanup(SHScene scene) {
        float speed;
        double angle;
        for (SHBall ball : _balls) {
            speed = ball.getVelocity().length();
            angle = Math.acos(ball.getVelocity().x / Math.abs(speed));
            if (ball.getVelocity().z > 0) {
                angle = 2 * Math.PI - angle;
            }

            ball.getVelocity().x = (float) Math.cos(angle) * speed / (1 - DEC_PERCENT);
            ball.getVelocity().z = -(float) Math.sin(angle) * speed / (1 - DEC_PERCENT);
        }
        _balls.clear();
    }
}
