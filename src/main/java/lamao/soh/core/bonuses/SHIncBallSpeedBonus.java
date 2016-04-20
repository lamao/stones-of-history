/*
 * SHIncBallSpeedBonus.java 27.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.bonuses;

import java.util.LinkedList;
import java.util.List;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

import lamao.soh.core.EntityProperties;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;

/**
 * Increases ball speed.
 * @author lamao
 */
@SuppressWarnings("serial")
public class SHIncBallSpeedBonus extends SHBonus {
    public final static float DURATION = 5;
    public final static float INC_PERCENT = 0.5f;

    private List<Spatial> _balls = new LinkedList<Spatial>();

    public SHIncBallSpeedBonus(
                    Spatial model) {
        super(model);
        setDuration(DURATION);
    }

    public SHIncBallSpeedBonus() {
        this(null);
    }

    @Override
    public void apply(SHScene scene) {
        float speed;
        double angle;
        for (Spatial ball : scene.get("ball")) {
            Vector3f ballVelocity = ball.getUserData(EntityProperties.VELOCITY);
            speed = ballVelocity.length();
            angle = SHUtils.angle(ballVelocity);

            ballVelocity.x = (float) Math.cos(angle) * speed * (1 + INC_PERCENT);
            ballVelocity.z = -(float) Math.sin(angle) * speed * (1 + INC_PERCENT);

            _balls.add(ball);
        }
    }

    @Override
    public void cleanup(SHScene scene) {
        float speed;
        double angle;
        for (Spatial ball : _balls) {
            Vector3f ballVelocity = ball.getUserData(EntityProperties.VELOCITY);
            speed = ballVelocity.length();
            angle = Math.acos(ballVelocity.x / Math.abs(speed));
            if (ballVelocity.z > 0) {
                angle = 2 * Math.PI - angle;
            }

            ballVelocity.x = (float) Math.cos(angle) * speed / (1 + INC_PERCENT);
            ballVelocity.z = (float) Math.sin(angle) * speed / (1 + INC_PERCENT);
        }
        _balls.clear();
    }

}
