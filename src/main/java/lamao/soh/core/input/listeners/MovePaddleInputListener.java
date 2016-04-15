/*
 * SHPaddleInputHandler.java 25.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.input.listeners;

import com.jme3.input.controls.AnalogListener;
import lamao.soh.SHOptions;
import lamao.soh.core.SHEntity;
import lamao.soh.core.SHScene;
import lamao.soh.states.SHLevelState;

/**
 * Input handler for moving paddle along X-axis.
 * @author lamao
 */
public class MovePaddleInputListener implements AnalogListener {

    private SHLevelState levelState;
    private float leftConstraint = -7;
    private float rightConstraint = 7;

    public MovePaddleInputListener(SHLevelState levelAppState) {
        this.levelState = levelAppState;
    }

    public void setConstraints(float left, float right) {
        leftConstraint = left;
        rightConstraint = right;
    }

    @Override
    public void onAnalog(String name, float value, float tpf) {

        SHScene scene = levelState.getScene();
        SHEntity entity = scene.getEntity("paddle", "paddle");

        float dispacement = 0;
        if ("paddle-left".equals(name)) {
            dispacement = -value;
        } else if ("paddle-right".equals(name)) {
            dispacement = value;
        }

        float newX = entity.getLocation().x + dispacement * SHOptions.PaddleMouseSensitivity;
        if (newX > rightConstraint) {
            newX = rightConstraint;
        } else if (newX < leftConstraint) {
            newX = leftConstraint;
        }

        entity.setLocation(newX, entity.getLocation().y, entity.getLocation().z);
    }

}
