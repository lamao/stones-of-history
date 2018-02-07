/**
 * SHMarkLevelCompletedEventHandler.java 18.12.2012 Copyright 2012 Stones of History All rights
 * reserved.
 */
package lamao.soh.core.eventhandlers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.core.model.entity.SHUser;
import lamao.soh.core.service.SHUserService;
import lamao.soh.ui.controllers.SHEpochsScreenController;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * Handler that marks completed level as completed for current user profile.
 * @author lamao
 */
public class SHMarkLevelCompletedEventHandler extends SHAbstractEventHandler {
    private static final Logger LOGGER = Logger
                    .getLogger(SHMarkLevelCompletedEventHandler.class.getCanonicalName());

    private SHEpochsScreenController epochsScreenController;
    private SHBreakoutGameContext gameContext;
    private SHUserService userService;

    public SHMarkLevelCompletedEventHandler(
                    SHEventDispatcher dispatcher,
                    SHEpochsScreenController epochsScreenController,
                    SHBreakoutGameContext gameContext,
                    SHUserService userService) {
        super(dispatcher);
        this.epochsScreenController = epochsScreenController;
        this.gameContext = gameContext;
        this.userService = userService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processEvent(SHEvent event) {
        try {
            String currentEpochId = epochsScreenController.getSelectedEpoch().getId();
            String currentLevelId = epochsScreenController.getSelectedLevel().getId();
            SHUser user = gameContext.getPlayer();

            user.addCompletedLevel(currentEpochId, currentLevelId);

            userService.save(user);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Can not save profile ", e);
        }
    }

}
