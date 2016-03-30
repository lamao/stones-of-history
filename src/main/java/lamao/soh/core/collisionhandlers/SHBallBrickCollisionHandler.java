/*
 * SHBallBrickCollisionHandler.java 01.05.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.collisionhandlers;

import com.jme3.collision.CollisionResult;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;

import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.core.controllers.SHDefaultMover;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHBrick;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 */
public class SHBallBrickCollisionHandler extends SHAbstractCollisiontHandler {
    public SHBallBrickCollisionHandler(
                    SHEventDispatcher dispatcher,
                    SHScene scene) {
        super(dispatcher, scene);
    }

    @Override
    public void processEvent(SHEvent event) {
        SHBrick brick = event.getParameter("dst", SHBrick.class);
        SHBall ball = event.getParameter("src", SHBall.class);
        CollisionResult collisionResult = event.getParameter("data", CollisionResult.class);

        dispatcher.addEventEx("level-brick-hit", this, "brick", brick);
        onHit(ball, brick, collisionResult);
        if (brick.getStrength() <= 0) {
            scene.remove(brick);
            dispatcher.addEventEx("level-brick-deleted", this, "brick", brick);

            SHBonus bonus = brick.getBonus();
            if (bonus != null) {
                bonus.addControl(new SHDefaultMover());
                Vector3f bonusLocation = brick.getLocation().clone();
                bonusLocation.y = 0;
                bonus.setLocation(bonusLocation);
                scene.add(bonus.getType(), bonus);
                dispatcher.addEventEx("level-bonus-extracted", this, "bonus", bonus);
            }
        }
    }

    /**
     * Reacts on collision with brick. If brick is glass ball does not change its velocity (e.i it
     * moves thought the brick)<br>
     * <b>NOTE:</b> velocity after repulsing is calculated based on
     * <code>getLocalTranslation()</code>. So both ball and brick models must be centered around
     * their locations.<br>
     * <b>NOTE:</b> It is supposed that bricks really intersects with ball. Method doesn't check
     * this.
     */
    public void onHit(SHBall ball, SHBrick brick, CollisionResult collisionResult) {
        if (ball.isSuper()) {
            if (brick.getStrength() != Integer.MAX_VALUE) {
                brick.setStrength(0);
                return;
            }
        }

        brick.hit();
        if (brick.isGlass()) {
            return;
        }

        Vector3f ballVelocity = ball.getVelocity();
        float velocityAngle = SHUtils.angle(ballVelocity.mult(-1));
        float normalAngle = SHUtils.angle(collisionResult.getContactNormal());
        float resultAngle = velocityAngle + 2 * (normalAngle - velocityAngle);
        float speed = ballVelocity.length();
        ballVelocity.x = FastMath.cos(resultAngle) * speed;
        ballVelocity.z = -FastMath.sin(resultAngle) * speed;

    }

}
