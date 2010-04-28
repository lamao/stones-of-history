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
import lamao.soh.core.SHBall;
import lamao.soh.core.SHDefaultBallMover;
import lamao.soh.core.SHLevel;
import lamao.soh.core.SHPaddle;
import lamao.soh.core.SHUtils;
import lamao.soh.utils.SHResourceManager;


/**
 * Attaches gun to paddle.
 * 
 * @author lamao
 *
 */
public class SHPaddleGunBonus extends SHBonus
{
	public final static float DURATION = 5; 
	public final static float FIRE_INTERVAL = 1;
	private final static SHResourceManager _manager = SHResourceManager.getInstance();
	
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
	
	
	/* (non-Javadoc)
	 * @see lamao.soh.core.bonuses.SHBonus#apply(lamao.soh.core.SHLevel)
	 */
	@Override
	public void apply(SHLevel level)
	{
		SHPaddle paddle = level.getPaddle();
		level.getRootNode().detachChild(paddle.getModel());		
		
		Spatial gunModel = (Spatial)_manager.get(SHResourceManager.TYPE_MODEL, 
				SHConstants.PADDLE_GUN);
		gunModel.setLocalTranslation(paddle.getModel().getLocalTranslation().clone());
		level.getPaddle().setModel(gunModel);
		
		level.getRootNode().attachChild(gunModel);
		gunModel.updateWorldVectors(true);
		level.getRootNode().updateRenderState();
		
		_action = new SHMouseGunAction(level);
		level.getInputHandler().addAction(_action);
		
	}
	
	/* (non-Javadoc)
	 * @see lamao.soh.core.bonuses.SHBonus#cleanup(lamao.soh.core.SHLevel)
	 */
	@Override
	public void cleanup(SHLevel level)
	{
		SHPaddle paddle = level.getPaddle();
		level.getRootNode().detachChild(paddle.getModel());
		
		Spatial model = (Spatial)_manager.get(SHResourceManager.TYPE_MODEL, 
				SHConstants.PADDLE);
		model.setLocalTranslation(paddle.getModel().getLocalTranslation().clone());
		level.getPaddle().setModel(model);
		
		level.getRootNode().attachChild(model);	
		model.updateWorldVectors(true);
		level.getRootNode().updateRenderState();
		
		level.getInputHandler().removeAction(_action);
		_action = null;
	}
	
	
	/** Class handler for fire-bullet action */
	private class SHMouseGunAction extends MouseInputAction
	{
		private SHLevel _level = null;
		
		private float _timeSinceLastFire = 1000000;
		
		
		public SHMouseGunAction(SHLevel level)
		{
			super();
			_level = level;
		}

		/* (non-Javadoc)
		 * @see com.jme.input.action.InputActionInterface#performAction(com.jme.input.action.InputActionEvent)
		 */
		@Override
		public void performAction(InputActionEvent evt)
		{
			_timeSinceLastFire += evt.getTime();
			if (MouseInput.get().isButtonDown(SHOptions.FireButton) && 
				_timeSinceLastFire > FIRE_INTERVAL)
			{
				_timeSinceLastFire = 0;
				SHPaddle paddle = _level.getPaddle();
				BoundingBox bound = (BoundingBox)paddle.getModel().getWorldBound();
				
				SHBall bullet = new SHBall();
				bullet.setSuper(true);
				bullet.setVelocity(0, 2, 0);
				Spatial bulletModel = SHUtils.createSharedModel(
						"bullet" + bullet, 
						(Spatial)_manager.get(SHResourceManager.TYPE_MODEL, 
						SHConstants.BULLET));
				bullet.setModel(bulletModel);
				bullet.setLocation(paddle.getLocation().x, 
						paddle.getLocation().y + bound.yExtent, 0);
				bullet.getModel().addController(new SHDefaultBallMover(bullet));

				_level.addBullet(bullet);
			}
			
		}
	}

}
