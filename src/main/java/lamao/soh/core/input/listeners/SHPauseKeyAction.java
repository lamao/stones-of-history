/**
 * SHPauseKeyAction.java 16.11.2012 Copyright 2012 Stones of History All rights reserved.
 */
package lamao.soh.core.input.listeners;

import com.jme3.input.controls.ActionListener;
import lamao.soh.states.LevelState;

/**
 * Pauses game
 * @author lamao
 */
public class SHPauseKeyAction implements ActionListener {
    private LevelState levelState;

    /**
     * 
     */
    public SHPauseKeyAction(
                    LevelState levelState) {
        this.levelState = levelState;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        levelState.setEnabled(!levelState.isEnabled());
    }
}
