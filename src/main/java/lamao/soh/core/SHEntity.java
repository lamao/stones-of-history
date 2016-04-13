/*
 * SHEntity.java 24.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Game entity (e.g. ball or box). Has a model as member variable.
 * @author lamao
 */
@SuppressWarnings("serial")
public class SHEntity extends Node {
    public static final String ENTITY_TYPE_DEFAULT = "default";

    /** Type of the entity */
    private String type = ENTITY_TYPE_DEFAULT;

    /**
     * Collidable model for this entity. Other spatials attached to the root node are treated as
     * decoration, effect etc.
     */
    private Spatial model = null;

    public SHEntity(
                    String type,
                    String name,
                    Spatial model) {
        this(model);
        this.type = type;
        setName(name);
    }

    public SHEntity(
                    Spatial model) {
        this();
        this.model = model;
        if (model != null) {
            attachChild(model);
        }
    }

    public SHEntity() {}

    public Spatial getModel() {
        return model;
    }

    public void setModel(Spatial model) {
        if (this.model != null) {
            detachChild(this.model);
        }
        this.model = model;
        attachChild(model);
    }

    /**
     * Changes model local translation. <br>
     * <b>NOTE: </b>Model must be not null
     */
    public void setLocation(Vector3f location) {
        setLocalTranslation(location);
        updateModelBound();
    }

    /**
     * @see #setLocation(Vector3f)
     */
    public void setLocation(float x, float y, float z) {
        setLocalTranslation(x, y, z);
        updateModelBound();
    }

    public Vector3f getLocation() {
        return getLocalTranslation();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
