/*
 * SHDoubleBallBonus.java 29.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.bonuses;

import java.util.List;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

import lamao.soh.core.EntityProperties;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;
import lamao.soh.core.controllers.SHDefaultBallMover;

/**
 * Doubles number of balls
 * @author lamao
 */
@SuppressWarnings("serial")
public class SHDoubleBallBonus extends SHBonus {
    public final static float DURATION = 10;

    public SHDoubleBallBonus(
                    Spatial model) {
        super(model);
        setDuration(DURATION);
    }

    public SHDoubleBallBonus() {
        this(null);
    }

    @Override
    public void apply(SHScene scene) {
        List<Spatial> balls = scene.get("ball");
        Spatial ball;
        Spatial newBall;
        float angle;
        for (int i = 0; i < balls.size(); i++) {
            ball = balls.get(i);
            Vector3f ballVelocity = ball.getUserData(EntityProperties.VELOCITY);
            newBall = ball.clone();
            newBall.addControl(new SHDefaultBallMover());

            angle = SHUtils.angle(ballVelocity);
            setVelocityAngle(ball, angle + (float) Math.PI / 8);
            setVelocityAngle(newBall, angle - (float) Math.PI / 8);
            scene.add(newBall);
        }
    }

    /**
     * Changes velocity of ball to specified angle. Speed is saved.
     * @param ball - ball
     * @param angle - new velocity
     */
    private void setVelocityAngle(Spatial ball, float angle) {
        float speed = SHUtils.getProperty(ball, EntityProperties.VELOCITY, Vector3f.class).length();
        Vector3f newVelocity = new Vector3f((float) Math.cos(angle) * speed, 0, -(float) Math.sin(angle) * speed);
        ball.setUserData(EntityProperties.VELOCITY, newVelocity);
    }

    @Override
    public void cleanup(SHScene scene) {
        List<Spatial> balls = scene.get("ball");
        int n = balls.size() / 2;
        for (int i = 0; i < n; i++) {
            scene.remove("ball", balls.get(n - 1 - i));
        }
//        scene.getRootNode().updateRenderState();
    }

}
