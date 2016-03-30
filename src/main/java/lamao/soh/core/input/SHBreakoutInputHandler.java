/*
 * SHPaddleInputHandler.java 25.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.input;

import com.jme3.input.controls.AnalogListener;
import lamao.soh.SHOptions;
import lamao.soh.core.SHEntity;

/**
 * Input handler for moving paddle along X-axis.
 * @author lamao
 */
public class SHBreakoutInputHandler implements AnalogListener {
    /** Controlled spatial */
    private SHEntity entity;

    private float leftConstraint = -7;
    private float rightConstraint = 7;

    public SHBreakoutInputHandler(
                    SHEntity entity) {
        this.entity = entity;
    }

    public SHEntity getModel() {
        return entity;
    }

    public void setModel(SHEntity entity) {
        this.entity = entity;
    }

    public void setConstraints(float left, float right) {
        leftConstraint = left;
        rightConstraint = right;
    }

    @Override
    public void onAnalog(String name, float value, float tpf) {
        float newX = entity.getLocation().x + value * tpf * SHOptions.PaddleMouseSensitivity;
        if (newX > rightConstraint) {
            newX = rightConstraint;
        } else if (newX < leftConstraint) {
            newX = leftConstraint;
        }

        entity.getLocation().x = newX;
    }

}
