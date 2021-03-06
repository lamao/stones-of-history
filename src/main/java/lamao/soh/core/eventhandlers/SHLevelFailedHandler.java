/**
 * SHLevelFailedHandler.java 04.01.2013 Copyright 2013 Stones of History All rights reserved.
 */
package lamao.soh.core.eventhandlers;

import lamao.soh.states.LevelState;
import lamao.soh.ui.controllers.SHInGameScreenController;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 */
public class SHLevelFailedHandler extends SHAbstractEventHandler {
    private SHInGameScreenController inGameScreenController;
    private LevelState levelState;

    public SHLevelFailedHandler(
                    SHEventDispatcher dispatcher,
                    LevelState levelState,
                    SHInGameScreenController inGameScreenController) {
        super(dispatcher);
        this.levelState = levelState;
        this.inGameScreenController = inGameScreenController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processEvent(SHEvent event) {
        levelState.setEnabled(false);
        inGameScreenController.showFailedMessage();
    }

}
