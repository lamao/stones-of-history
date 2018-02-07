/*
 * SHPaddleGunBonus.java 12.04.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.core.bonuses;

import com.jme3.asset.AssetManager;
import com.jme3.bounding.BoundingBox;
import com.jme3.input.controls.ActionListener;
import com.jme3.scene.Spatial;

import lamao.soh.SHConstants;
import lamao.soh.core.SHScene;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHPaddle;

/**
 * Attaches gun to paddle.
 * @author lamao
 */
@SuppressWarnings("serial")
public class SHPaddleGunBonus extends SHBonus {
    public final static float DEFAULT_DURATION = 5;
    public final static float FIRE_INTERVAL = 1;

    /** Fire action which will be added after ball activation */
    private SHMouseGunAction action = null;

    private AssetManager manager;

    public SHPaddleGunBonus(
                    Spatial model,
                    AssetManager assetManager) {
        super(model);
        this.manager = assetManager;
        setDuration(DEFAULT_DURATION);
    }

    @Override
    public void apply(SHScene scene) {
        SHPaddle paddle = scene.getEntity("paddle", "paddle", SHPaddle.class);

        throw new UnsupportedOperationException();
//        Spatial gunModel = (Spatial) manager.get(SHResourceManager.TYPE_MODEL,
//                        SHConstants.PADDLE_GUN);
//
//        paddle.setModel(gunModel);

//        action = new SHMouseGunAction(scene);

    }

    @Override
    public void cleanup(SHScene scene) {
        SHPaddle paddle = scene.getEntity("paddle", "paddle", SHPaddle.class);

        throw new UnsupportedOperationException();
//        Spatial model = (Spatial) manager.get(SHResourceManager.TYPE_MODEL, SHConstants.PADDLE);
//
//        paddle.setModel(model);
//
//        action = null;
    }

    /** Class handler for fire-bullet action */
    private class SHMouseGunAction implements ActionListener {
        private SHScene _scene = null;

        private float _timeSinceLastFire = 1000000;

        public SHMouseGunAction(
                        SHScene scene) {
            super();
            _scene = scene;
        }

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            _timeSinceLastFire += tpf;
            if (_timeSinceLastFire > FIRE_INTERVAL) {
                _timeSinceLastFire = 0;
                SHPaddle paddle = _scene.getEntity("paddle", "paddle", SHPaddle.class);
                BoundingBox bound = (BoundingBox) paddle.getModel().getWorldBound();

                SHBall bullet = new SHBall();
                bullet.setType("bullet");
                bullet.setName("bullet" + bullet);
                bullet.setSuper(true);
                bullet.setVelocity(0, 2, 0);
                Spatial bulletModel = null;
                throw new UnsupportedOperationException();
//                SHUtils.createSharedModel("bullet" + bullet, (Spatial) manager
//                                .get(SHResourceManager.TYPE_MODEL, SHConstants.BULLET));
//                bullet.setModel(bulletModel);
//                bullet.setLocation(paddle.getLocation().x, paddle.getLocation().y + bound.getYExtent(),
//                                0);
//                bullet.addControl(new SHDefaultBallMover(bullet));
//
//                _scene.add(bullet);
            }

        }
    }

}
