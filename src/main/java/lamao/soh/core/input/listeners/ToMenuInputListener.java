/**
 * SHToMenuKeyAction.java 16.11.2012 Copyright 2012 Stones of History All rights reserved.
 */
package lamao.soh.core.input.listeners;

import com.jme3.input.controls.ActionListener;
import lamao.soh.states.LevelState;
import lamao.soh.ui.controllers.SHInGameScreenController;

/**
 * Show main menu
 * @author lamao
 */
public class ToMenuInputListener implements ActionListener {
    private LevelState levelState;

    private SHInGameScreenController inGameScreenController;

    public ToMenuInputListener(
        LevelState levelState,
        SHInGameScreenController inGameScreenController) {
        this.levelState = levelState;
        this.inGameScreenController = inGameScreenController;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        levelState.setEnabled(false);
        inGameScreenController.showInGameMenu();
    }
}
