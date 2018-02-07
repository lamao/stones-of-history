/*
 * SHLevelInfo.java 30.05.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.model.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Class that contains information about one level.
 * @author lamao
 */

@XStreamAlias("level")
public class SHLevel {
    /** Unique (within epoch) ID of this level */
    @XStreamAlias("id")
    private String id;

    /** Level contents (bricks, walls etc) */
    @XStreamAlias("scene")
    private String scene;

    /** Level introduction - historical overview */
    @XStreamAlias("intro")
    private String intro;

    /** Name of the displayed level name */
    @XStreamAlias("name")
    private String name;

    public SHLevel() {}

    public SHLevel(
                    String id,
                    String name,
                    String intro,
                    String scene) {
        this.id = id;
        this.name = name;
        this.intro = intro;
        this.scene = scene;
    }

    public SHLevel(
                    String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return name;
    }

}
