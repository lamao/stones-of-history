/* 
 * SHPaddleGunBonus.java 12.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.bonuses;

import com.jme.bounding.BoundingBox;
import com.jme.input.MouseInput;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.MouseInputAction;
import com.jme.scene.Spatial;

import lamao.soh.SHConstants;
import lamao.soh.SHOptions;
import lamao.soh.core.SHGamePack;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;
import lamao.soh.core.controllers.SHDefaultBallMover;
import lamao.soh.core.entities.SHBall;
import lamao.soh.core.entities.SHPaddle;
import lamao.soh.utils.SHResourceManager;


/**
 * Attaches gun to paddle.
 * 
 * @author lamao
 *
 */
@SuppressWarnings("serial")
public class SHPaddleGunBonus extends SHBonus
{
	public final static float DURATION = 5; 
	public final static float FIRE_INTERVAL = 1;
	
	/** Fire action which will be added after ball activation */
	private SHMouseGunAction _action = null;
	
	public SHPaddleGunBonus(Spatial model)
	{
		super(model);
		setDuration(DURATION);
	}
	
	public SHPaddleGunBonus()
	{
		this(null);
	}
	
	
	@Override
	public void apply(SHScene scene)
	{
		SHPaddle paddle = (SHPaddle) scene.getEntity("paddle", "paddle");
		
		Spatial gunModel = (Spatial)SHGamePack.manager.get(
				SHResourceManager.TYPE_MODEL, SHConstants.PADDLE_GUN);
		
		paddle.setModel(gunModel);
		
		_action = new SHMouseGunAction(scene);
		SHGamePack.input.addAction(_action);
		
	}
	
	@Override
	public void cleanup(SHScene scene)
	{
		SHPaddle paddle = (SHPaddle) scene.getEntity("paddle", "paddle");
		
		Spatial model = (Spatial)SHGamePack.manager.get(
				SHResourceManager.TYPE_MODEL, SHConstants.PADDLE);
		
		paddle.setModel(model);
		
		SHGamePack.input.removeAction(_action);
		_action = null;
	}
	
	
	/** Class handler for fire-bullet action */
	private class SHMouseGunAction extends MouseInputAction
	{
		private SHScene _scene = null;
		
		private float _timeSinceLastFire = 1000000;
		
		
		public SHMouseGunAction(SHScene scene)
		{
			super();
			_scene = scene;
		}

		@Override
		public void performAction(InputActionEvent evt)
		{
			_timeSinceLastFire += evt.getTime();
			if (MouseInput.get().isButtonDown(SHOptions.FireButton) && 
				_timeSinceLastFire > FIRE_INTERVAL)
			{
				_timeSinceLastFire = 0;
				SHPaddle paddle = (SHPaddle) _scene.getEntity("paddle", "paddle");
				BoundingBox bound = (BoundingBox)paddle.getModel().getWorldBound();
				
				SHBall bullet = new SHBall();
				bullet.setType("bullet");
				bullet.setName("bullet" + bullet);
				bullet.setSuper(true);
				bullet.setVelocity(0, 2, 0);
				Spatial bulletModel = SHUtils.createSharedModel(
						"bullet" + bullet, 
						(Spatial)SHGamePack.manager.get(SHResourceManager.TYPE_MODEL, 
						SHConstants.BULLET));
				bullet.setModel(bulletModel);
				bullet.setLocation(paddle.getLocation().x, 
						paddle.getLocation().y + bound.yExtent, 0);
				bullet.addController(new SHDefaultBallMover(bullet));

				_scene.add(bullet);
				bullet.updateGeometricState(0, true);
			}
			
		}
	}

}
