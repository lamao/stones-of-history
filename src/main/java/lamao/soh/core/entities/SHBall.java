/*
 * SHBall.java 23.10.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.entities;

import lamao.soh.core.SHEntity;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Ball entity.
 * @author lamao
 */
@SuppressWarnings("serial")
public class SHBall extends SHEntity {
    /** Ball velocity */
    private Vector3f velocity;

    /**
     * Ball is super (e.i. it destroys all bricks which can be destroyed by on hit and does not
     * change its direction when hit them)
     */
    private boolean isSuper = false;

    public SHBall(
                    Spatial model,
                    Vector3f velocity) {
        super(model);
        this.velocity = velocity;
    }

    /** Create ball with velocity = (0, 0, -1) */
    public SHBall(
                    Spatial model) {
        this(model, new Vector3f(0, 0, -1));
    }

    /** Ball without model and same parameters as in {@link #SHBall(Spatial)} */
    public SHBall() {
        this(null);
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public void setVelocity(float x, float y, float z) {
        velocity.set(x, y, z);
    }

    public boolean isSuper() {
        return isSuper;
    }

    public void setSuper(boolean value) {
        isSuper = value;
    }

    @Override
    public SHBall clone() {
        super.clone();
        Spatial model = getModel();
        Spatial newModel = model.clone();
        newModel.setName("ball" + newModel);
        newModel.updateModelBound();

        SHBall result = new SHBall(newModel, velocity.clone());
        result.setType(getType());
        result.setName("ball" + this);
        result.setLocation(getLocation().clone());

        return result;
    }

}
