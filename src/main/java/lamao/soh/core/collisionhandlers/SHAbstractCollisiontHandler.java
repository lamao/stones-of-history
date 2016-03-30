/*
 * SHAbstractCollisiontHandler.java 09.09.2012 Copyright 2012 Stones of History All rights reserved.
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.SHScene;
import lamao.soh.core.eventhandlers.SHAbstractEventHandler;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 */
public abstract class SHAbstractCollisiontHandler extends SHAbstractEventHandler {

    protected SHScene scene;

    public SHAbstractCollisiontHandler() {}

    public SHAbstractCollisiontHandler(
                    SHEventDispatcher dispatcher,
                    SHScene scene) {
        super(dispatcher);
        this.scene = scene;
    }

    public SHScene getScene() {
        return scene;
    }

    public void setScene(SHScene scene) {
        this.scene = scene;
    }

}
