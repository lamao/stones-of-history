/*
 * SHBulletWallCollisionHandler.java 01.05.2010 Copyright 2010 Stones of History All rights
 * reserved.
 */
package lamao.soh.core.collisionhandlers;

import com.jme3.scene.Spatial;
import lamao.soh.core.SHScene;
import lamao.soh.states.LevelState;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 */
public class SHBulletWallCollisionHandler extends SHAbstractCollisionHandler {

    public SHBulletWallCollisionHandler(
                    SHEventDispatcher dispatcher,
                    LevelState levelState) {
        super(dispatcher, levelState);
    }

    @Override
    public void processEvent(SHEvent event) {
        Spatial bullet = event.getParameter("src", Spatial.class);
        SHScene scene = getLevelState().getScene();
        scene.remove(bullet);
    }
}
