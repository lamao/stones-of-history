/*
 * SHBonusBottomWallCollisionHandler.java 01.05.2010 Copyright 2010 Stones of History All rights
 * reserved.
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.SHScene;
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.states.LevelState;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 */
public class SHBonusBottomWallCollisionHandler extends SHAbstractCollisionHandler {

    public SHBonusBottomWallCollisionHandler(
                    SHEventDispatcher dispatcher,
                    LevelState levelState) {
        super(dispatcher, levelState);
    }

    @Override
    public void processEvent(SHEvent event) {
        SHBonus bonus = event.getParameter("src", SHBonus.class);
        SHScene scene = getLevelState().getScene();
        scene.remove(bonus);
        dispatcher.addEvent("level-bonus-destroyed", this, "bonus", bonus);
    }

}
