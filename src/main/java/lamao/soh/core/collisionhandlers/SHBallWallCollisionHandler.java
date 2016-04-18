/*
 * SHBallWallCollisionHandler.java 01.05.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.collisionhandlers;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import lamao.soh.core.SHEntity;
import lamao.soh.core.SHScene;
import lamao.soh.core.entities.SHBall;
import lamao.soh.states.SHLevelState;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 */
public class SHBallWallCollisionHandler extends SHAbstractCollisionHandler {

    public SHBallWallCollisionHandler(SHEventDispatcher dispatcher, SHLevelState levelState) {
        super(dispatcher, levelState);
    }

    @Override
    public void processEvent(SHEvent event) {
        SHBall ball = event.getParameter("src", SHBall.class);
        Geometry wall = getWall(event);


        if (wall.getName().equals("left-wall") || wall.getName().equals("right-wall")) {
            ball.getVelocity().x = -ball.getVelocity().x;
        } else if (wall.getName().equals("top-wall")) {
            ball.getVelocity().z = -ball.getVelocity().z;
        }

        dispatcher.addEventEx("level-wall-hit", this, "wall-type", wall.getName());
    }

    private Geometry getWall(SHEvent event) {
        CollisionResults collisionResults = event.getParameter("data", CollisionResults.class);
        CollisionResult firstCollision = collisionResults.iterator().next();
        return firstCollision.getGeometry();
    }

}
