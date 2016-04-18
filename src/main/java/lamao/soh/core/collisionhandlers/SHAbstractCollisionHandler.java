/*
 * SHAbstractCollisiontHandler.java 09.09.2012 Copyright 2012 Stones of History All rights reserved.
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.SHScene;
import lamao.soh.core.eventhandlers.SHAbstractEventHandler;
import lamao.soh.states.SHLevelState;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 */
public abstract class SHAbstractCollisionHandler extends SHAbstractEventHandler {

    protected SHLevelState levelState;

    public SHAbstractCollisionHandler(
        SHEventDispatcher dispatcher,
        SHLevelState levelState) {
        super(dispatcher);
    }

    public SHLevelState getLevelState() {
        return levelState;
    }

    public void setLevelState(SHLevelState scene) {
        this.levelState = levelState;
    }

}
