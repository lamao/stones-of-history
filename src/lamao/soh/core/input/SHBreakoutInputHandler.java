/* 
 * SHPaddleInputHandler.java 25.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.input;

import lamao.soh.SHOptions;
import lamao.soh.core.SHEntity;

import com.jme.input.InputHandler;
import com.jme.input.RelativeMouse;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.MouseInputAction;

/**
 * Input handler for moving paddle along X-axis.
 * @author lamao
 *
 */
public class SHBreakoutInputHandler extends InputHandler
{
	/** Controlled spatial */
	private SHEntity _entity;
	
	/** Mouse used for handling events */
	private RelativeMouse _mouse = null;
	
	private float _leftConstraint = -7;
	private float _rightConstraint = 7;
	
	public SHBreakoutInputHandler(SHEntity entity)
	{
		_entity = entity;
		_mouse = new RelativeMouse("rel mouse");
		_mouse.registerWithInputHandler(this);
		
		SHMousePaddleMove mouseAction = new SHMousePaddleMove();
		mouseAction.setMouse(_mouse);
		addAction(mouseAction);
		
//		addAction(new KeyNodeStrafeRightAction(entity, 
//				SHOptions.PaddleKeyboardSensitivity),
//				"paddle left", SHOptions.PaddleLeftKey, true);
//		addAction(new KeyNodeStrafeLeftAction(entity, 
//				SHOptions.PaddleKeyboardSensitivity), 
//				"paddle right", SHOptions.PaddleRightKey, true);
	}

	public SHEntity getModel()
	{
		return _entity;
	}

	public void setModel(SHEntity entity)
	{
		_entity = entity;
	}
	
	
	
	public RelativeMouse getMouse()
	{
		return _mouse;
	}

	public void setConstraints(float left, float right)
	{
		_leftConstraint = left;
		_rightConstraint = right;
	}
	
	/** Action for moving paddle by mouse */
	private class SHMousePaddleMove extends MouseInputAction
	{
		@Override
		public void performAction(InputActionEvent evt)
		{
			float newX = _entity.getLocation().x 
							+ mouse.getLocalTranslation().x 
							* SHOptions.PaddleMouseSensitivity;
			if (newX > _rightConstraint)
			{
				newX = _rightConstraint;
			} 
			else if (newX < _leftConstraint)
			{
				newX = _leftConstraint;
			}
			
			_entity.getLocation().x = newX;

		}
	}
	
}
