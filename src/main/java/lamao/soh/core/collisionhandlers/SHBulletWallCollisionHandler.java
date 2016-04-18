/*
 * SHBulletWallCollisionHandler.java 01.05.2010 Copyright 2010 Stones of History All rights
 * reserved.
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.SHScene;
import lamao.soh.core.entities.SHBall;
import lamao.soh.states.SHLevelState;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 */
public class SHBulletWallCollisionHandler extends SHAbstractCollisionHandler {

    public SHBulletWallCollisionHandler(
                    SHEventDispatcher dispatcher,
                    SHLevelState levelState) {
        super(dispatcher, levelState);
    }

    @Override
    public void processEvent(SHEvent event) {
        SHBall bullet = event.getParameter("src", SHBall.class);
        SHScene scene = levelState.getScene();
        scene.remove(bullet);
    }
}
