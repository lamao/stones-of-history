/*
 * SHBreakoutEntityFactory.java 03.05.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.core.entities.SHBottomWall;
import lamao.soh.core.entities.SHBrick;

/**
 * Entity factory for stones of history.
 * @author lamao
 */
public class SHBreakoutEntityFactory implements ISHEntityFactory {
    private AssetManager assetManager;

    public SHBreakoutEntityFactory(AssetManager assetManager)
	{
		super();
		this.assetManager = assetManager;
	}

    @Override
    public SHEntity createEntity(Spatial model) {
        String type = model.getUserData("type");

        SHEntity entity = null;
        if (type != null) {
            if (type.equals("brick")) {
                SHBrick brick = new SHBrick();
                String value = model.getUserData("strength");
                if (value != null) {
                    if (value.equals("super")) {
                        brick.setStrength(Integer.MAX_VALUE);
                    } else {
                        brick.setStrength(Integer.parseInt(value));
                    }
                }
                value = model.getUserData("glass");
                if (value != null) {
                    brick.setGlass(Boolean.parseBoolean(value));
                }

                value = model.getUserData("bonus");
                if (value != null) {
                    throw new UnsupportedOperationException();
//                    SHBonus bonus = (SHBonus) createEntity(
//                                    SHUtils.buildMap("type bonus|name " + value));
//                    brick.setBonus(bonus);
                }

                entity = brick;
            } else if (type.equals("bottom-wall")) {
                entity = new SHBottomWall();
            } else if (type.equals("wall")) {
                entity = new SHEntity();
            } else if (type.equals("bonus")) {
                try {
                    String bonusName = model.getUserData("name");
                    String className = SHUtils.getClassName("lamao.soh.core.bonuses.SH", bonusName,
                                    "Bonus");
                    Class<?> klass = Class.forName(className);
                    entity = (SHBonus) klass.newInstance();
                    throw new UnsupportedOperationException();
//                    Spatial model = (Spatial) manager.get(SHResourceManager.TYPE_MODEL, bonusName);
//                    entity.setModel(model.clone());
                } catch (ClassNotFoundException e) {
                } catch (InstantiationException e) {
                } catch (IllegalAccessException e) {
                }
            }
            if (entity != null) {
                entity.setType(type);
            }
        }
        return entity;
    }

}
