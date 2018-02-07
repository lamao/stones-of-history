/*
 * SHAbstractCollisiontHandler.java 09.09.2012 Copyright 2012 Stones of History All rights reserved.
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.eventhandlers.SHAbstractEventHandler;
import lamao.soh.states.LevelState;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 */
public abstract class SHAbstractCollisionHandler extends SHAbstractEventHandler {

    private LevelState levelState;

    public SHAbstractCollisionHandler(
        SHEventDispatcher dispatcher,
        LevelState levelState) {
        super(dispatcher);
        this.levelState = levelState;
    }

    public LevelState getLevelState() {
        return levelState;
    }

    public void setLevelState(LevelState scene) {
        this.levelState = levelState;
    }

}
