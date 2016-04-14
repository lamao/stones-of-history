/*
 * SHPaddleInputHandler.java 25.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.input;

import com.jme3.input.controls.AnalogListener;
import lamao.soh.SHOptions;
import lamao.soh.core.SHEntity;
import lamao.soh.core.SHScene;

import java.util.logging.Logger;

/**
 * Input handler for moving paddle along X-axis.
 * @author lamao
 */
public class SHBreakoutInputHandler implements AnalogListener {

    private SHScene scene;
    private float leftConstraint = -7;
    private float rightConstraint = 7;

    public SHBreakoutInputHandler(SHScene scene) {
        this.scene = scene;
    }

    public void setConstraints(float left, float right) {
        leftConstraint = left;
        rightConstraint = right;
    }

    public SHScene getScene() {
        return scene;
    }

    public void setScene(SHScene scene) {
        this.scene = scene;
    }

    @Override
    public void onAnalog(String name, float value, float tpf) {

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
