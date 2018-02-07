/*
 * SHEntityFactory.java 03.05.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core;

import com.jme3.scene.Spatial;

import java.util.Map;

/**
 * Factory for creating entities from metadata
 * @author lamao
 */
public interface ISHEntityFactory {
    public SHEntity createEntity(Spatial metadata);
}
