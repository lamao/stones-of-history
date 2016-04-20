/*
 * SHBallWallCollisionHandler.java 01.05.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.collisionhandlers;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import lamao.soh.core.EntityProperties;
import lamao.soh.states.LevelState;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 */
public class SHBallWallCollisionHandler extends SHAbstractCollisionHandler {

    public SHBallWallCollisionHandler(SHEventDispatcher dispatcher, LevelState levelState) {
        super(dispatcher, levelState);
    }

    @Override
    public void processEvent(SHEvent event) {
        Spatial ball = event.getParameter("src", Spatial.class);
        Geometry wall = getWall(event);

        Vector3f ballVelocity = ball.getUserData(EntityProperties.VELOCITY);
        if (wall.getName().equals("left-wall") || wall.getName().equals("right-wall")) {
            ballVelocity.x = -ballVelocity.x;
        } else if (wall.getName().equals("top-wall")) {
            ballVelocity.z = -ballVelocity.z;
        }

        dispatcher.addEventEx("level-wall-hit", this, "wall-type", wall.getName());
    }

    private Geometry getWall(SHEvent event) {
        CollisionResults collisionResults = event.getParameter("data", CollisionResults.class);
        CollisionResult firstCollision = collisionResults.iterator().next();
        return firstCollision.getGeometry();
    }

}
