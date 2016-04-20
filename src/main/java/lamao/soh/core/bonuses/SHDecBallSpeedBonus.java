/*
 * SHDecBallSpeedBonus.java 27.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.bonuses;

import java.util.LinkedList;
import java.util.List;

import com.jme3.math.Vector3f;
import lamao.soh.core.EntityProperties;
import lamao.soh.core.EntityTypes;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;

import com.jme3.scene.Spatial;

/**
 * Decreases ball speed
 * @author lamao
 */
@SuppressWarnings("serial")
public class SHDecBallSpeedBonus extends SHBonus {
    public final static float DURATION = 5;
    public final static float DEC_PERCENT = 0.5f;

    private List<Spatial> _balls = new LinkedList<Spatial>();

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
        for (Spatial ball : scene.get(EntityTypes.BALL)) {
            Vector3f ballVelocity = ball.getUserData(EntityProperties.VELOCITY);
            speed = ballVelocity.length();
            angle = SHUtils.angle(ballVelocity);

            ballVelocity.x = (float) Math.cos(angle) * speed * (1 - DEC_PERCENT);
            ballVelocity.z = -(float) Math.sin(angle) * speed * (1 - DEC_PERCENT);

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

            ballVelocity.x = (float) Math.cos(angle) * speed / (1 - DEC_PERCENT);
            ballVelocity.z = -(float) Math.sin(angle) * speed / (1 - DEC_PERCENT);
        }
        _balls.clear();
    }
}
