/*
 * SHBonusTimeOverEventHandler.java 15.05.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.eventhandlers;

import lamao.soh.core.SHScene;
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 */
public class SHBonusTimeOverEventHandler extends SHAbstractEventHandler {

    private SHScene scene;

    public SHBonusTimeOverEventHandler(
                    SHEventDispatcher dispatcher,
                    SHScene scene) {
        super(dispatcher);
        this.scene = scene;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processEvent(SHEvent event) {
        SHBonus bonus = event.getParameter("bonus", SHBonus.class);

        bonus.cleanup(scene);

        dispatcher.removeHandler(event.getType(), this);
    }

}
