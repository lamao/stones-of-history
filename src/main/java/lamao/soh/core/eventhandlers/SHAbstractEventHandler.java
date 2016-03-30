/*
 * SHAbstractEventHandler.java 16.09.2012 Copyright 2012 Stones of History All rights reserved.
 */
package lamao.soh.core.eventhandlers;

import lamao.soh.utils.events.ISHEventHandler;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 */
public abstract class SHAbstractEventHandler implements ISHEventHandler {

    protected SHEventDispatcher dispatcher;

    public SHAbstractEventHandler() {}

    public SHAbstractEventHandler(
                    SHEventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public SHEventDispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(SHEventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
}
