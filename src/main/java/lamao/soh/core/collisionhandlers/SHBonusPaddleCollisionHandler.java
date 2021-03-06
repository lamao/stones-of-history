/*
 * SHBonusPaddleCollisionHandler.java 01.05.2010 Copyright 2010 Stones of History All rights
 * reserved.
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.SHScene;
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.core.eventhandlers.SHBonusTimeOverEventHandler;
import lamao.soh.states.LevelState;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 */
public class SHBonusPaddleCollisionHandler extends SHAbstractCollisionHandler {

    public SHBonusPaddleCollisionHandler(
                    SHEventDispatcher dispatcher,
                    LevelState levelState) {
        super(dispatcher, levelState);
    }

    @Override
    public void processEvent(SHEvent event) {
        SHBonus bonus = event.getParameter("src", SHBonus.class);
        SHScene scene = getLevelState().getScene();

        scene.remove(bonus);
        boolean needAdd = true;
        if (bonus.isAddictive()) {
            String eventName = "bonus-over-" + bonus.getType();
            if (dispatcher.hasTimeEvent(eventName)) {
                needAdd = false;
                dispatcher.prolongTimeEvent(eventName, bonus.getDuration());
                dispatcher.addEvent("level-bonus-prolongated", this, "bonus", bonus);
            }
        }
        if (needAdd) {
            String eventType = "bonus-over-" + bonus;
            dispatcher.addEventExWithTime(eventType, this, bonus.getDuration(), "bonus", bonus);
            dispatcher.addHandler(eventType, new SHBonusTimeOverEventHandler(dispatcher, scene));
            bonus.apply(scene);
            dispatcher.addEvent("level-bonus-activated", this, "bonus", bonus);
        }
    }

}
