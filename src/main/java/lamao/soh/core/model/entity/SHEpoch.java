/*
 * SHEpochInfo.java 30.05.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Class that contains information about single epoch
 * @author lamao
 */
@XStreamAlias("epoch")
public class SHEpoch {
    /**
     * ID of epochs. Is set from outer code. Typically the name of folder with epoch
     */
    @XStreamOmitField
    private String id;

    /** Displayed name of the epoch */
    @XStreamAlias("name")
    private String name;

    /** Order of the epoch. The lowest order the earlier is epoch */
    @XStreamAlias("order")
    private float order = 0;

    /** List of levels in epoch */
    @XStreamAlias("levels")
    private List<SHLevel> _levels = new ArrayList<SHLevel>();

    public SHEpoch() {}

    public SHEpoch(
                    String id,
                    String name,
                    float order) {
        super();
        this.id = id;
        this.name = name;
        this.order = order;
    }

    public SHEpoch(
                    String id) {
        super();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getOrder() {
        return order;
    }

    public void setOrder(float order) {
        this.order = order;
    }

    public List<SHLevel> getLevels() {
        return _levels;
    }

    public void setLevels(List<SHLevel> levels) {
        _levels = levels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return name;
    }

}
