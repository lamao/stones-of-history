/**
 * SHPauseKeyAction.java 16.11.2012 Copyright 2012 Stones of History All rights reserved.
 */
package lamao.soh.core.input.actions;

import com.jme3.input.controls.ActionListener;
import lamao.soh.states.SHLevelState;

/**
 * Pauses game
 * @author lamao
 */
public class SHPauseKeyAction implements ActionListener {
    private SHLevelState levelState;

    /**
     * 
     */
    public SHPauseKeyAction(
                    SHLevelState levelState) {
        this.levelState = levelState;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        levelState.setPause(!levelState.isPause());
    }
}
