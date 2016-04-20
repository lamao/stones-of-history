/*
 * SHBallBrickCollisionHandler.java 01.05.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.collisionhandlers;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.FastMath;
import com.jme3.math.Triangle;
import com.jme3.math.Vector3f;

import com.jme3.scene.Spatial;
import lamao.soh.core.EntityConstants;
import lamao.soh.core.EntityProperties;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;
import lamao.soh.core.entities.SHBall;
import lamao.soh.states.LevelState;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 */
public class SHBallBrickCollisionHandler extends SHAbstractCollisionHandler {
    public SHBallBrickCollisionHandler(
                    SHEventDispatcher dispatcher,
                    LevelState levelState) {
        super(dispatcher, levelState);
    }

    @Override
    public void processEvent(SHEvent event) {
        Spatial brick = event.getParameter("dst", Spatial.class);
        SHBall ball = event.getParameter("src", SHBall.class);
        CollisionResults collisionResults = event.getParameter("data", CollisionResults.class);
        SHScene scene = getLevelState().getScene();

        dispatcher.addEventEx("level-brick-hit", this, "brick", brick);
        onHit(ball, brick, collisionResults);
        if (SHUtils.getProperty(brick, EntityProperties.STRENGTH, Integer.class) <= 0) {
            scene.remove(brick);
            dispatcher.addEventEx("level-brick-deleted", this, "brick", brick);

//            SHBonus bonus = brick.getBonus();
//            if (bonus != null) {
//                bonus.addControl(new SHDefaultMover());
//                Vector3f bonusLocation = brick.getLocalTranslation().clone();
//                bonusLocation.y = 0;
//                bonus.setLocation(bonusLocation);
//                scene.add(bonus.getType(), bonus);
//                dispatcher.addEventEx("level-bonus-extracted", this, "bonus", bonus);
//            }
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
    public void onHit(SHBall ball, Spatial brick, CollisionResults collisionResults) {
        int brickStrength = brick.getUserData(EntityProperties.STRENGTH);

        if (ball.isSuper()) {
            if (!SHUtils.getPropertyAsBoolean(brick, EntityProperties.IS_SUPER)) {
                brick.setUserData(EntityProperties.STRENGTH, 0);
                return;
            }
        }


        if (!SHUtils.getPropertyAsBoolean(brick, EntityProperties.IS_SUPER)) {
            brick.setUserData(EntityProperties.STRENGTH, brickStrength - 1);
        }

        if (SHUtils.getPropertyAsBoolean(brick, EntityProperties.IS_GLASS)) {
            return;
        }

        Vector3f contactNormal = Vector3f.ZERO.clone();
        for (CollisionResult collisionResult : collisionResults) {
            Triangle contactTriangle = collisionResult.getTriangle(null);
            contactNormal.addLocal(contactTriangle.getNormal());
        }
        contactNormal.divideLocal(collisionResults.size()).normalizeLocal();

        Vector3f ballVelocity = ball.getVelocity();
        float velocityAngle = SHUtils.angle(ballVelocity.mult(-1));
        float normalAngle = SHUtils.angle(contactNormal);
        float resultAngle = velocityAngle + 2 * (normalAngle - velocityAngle);
        float speed = ballVelocity.length();
        ballVelocity.x = FastMath.cos(resultAngle) * speed;
        ballVelocity.z = -FastMath.sin(resultAngle) * speed;
    }

}
