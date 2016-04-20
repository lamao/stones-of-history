/**
 * GameContextService.java 19.11.2012 Copyright 2012 Stones of History All rights reserved.
 */
package lamao.soh.core.service;

import java.util.List;

import lamao.soh.core.EntityConstants;
import lamao.soh.core.EntityProperties;
import lamao.soh.core.EntityTypes;
import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.core.SHScene;

import com.jme3.scene.Spatial;
import lamao.soh.core.SHUtils;

/**
 * @author lamao
 */
public class SHGameContextService {
    private SHScene scene;

    public SHGameContextService(
                    SHScene scene) {
        super();
        this.scene = scene;
    }

    public void updateNumberOfDeletableBricks(SHBreakoutGameContext context) {
        int numberOfDeletableBricks = 0;
        List<Spatial> bricks = scene.get(EntityTypes.BRICK);
        if (bricks != null) {
            for (Spatial brick : bricks) {
                if (SHUtils.getProperty(brick, EntityProperties.STRENGTH, Integer.class) != EntityConstants.BRICK_SUPER_STRENGTH) {
                    numberOfDeletableBricks++;
                }
            }
        }

        context.setNumberOfDeletableBricks(numberOfDeletableBricks);
    }

}
