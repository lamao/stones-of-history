/* 
 * SHFPSInputCommand.java Jun 3, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

import com.jme.input.FirstPersonHandler;
import com.jme.input.InputHandler;
import com.jme.renderer.Camera;
import com.jme.scene.Controller;
import com.jme.system.DisplaySystem;

import lamao.soh.core.SHScene;

/**
 * Activates/deactivates FPS input handler in the scene. 
 * @author lamao
 *
 */
public class SHFPSInputCommand extends SHBasicCommand
{
	private SHScene scene;
	
	public SHFPSInputCommand(SHScene scene)
	{
		super(1, 1);
		this.scene = scene;
	}
	
	@Override
	public void processCommand(String[] args)
	{
		Boolean enable = Boolean.valueOf(args[1]);
		FPSInputController controller = findController();
		if (enable) 
		{
			if (controller == null)
			{
				scene.getRootNode().addController(new FPSInputController(scene));
			}
		}
		else if (controller != null)
		{
			scene.getRootNode().removeController(controller);
		}
	}
	
	private FPSInputController findController()
	{
		for (Controller controller : scene.getRootNode().getControllers())
		{
			if (controller instanceof FPSInputController)
			{
				return (FPSInputController) controller;
			}
		}
		return null;
	}
	
	@SuppressWarnings("serial")
	public class FPSInputController extends Controller
	{
		private InputHandler input = null;
		
		public FPSInputController(SHScene scene)
		{
			DisplaySystem display = DisplaySystem.getDisplaySystem();
			Camera cam = display.getRenderer().getCamera();
			input = new FirstPersonHandler(cam);
		}
		@Override
		public void update(float time)
		{
			input.update(time);
		}
	}
	
	@Override
	protected String getHelpMessage()
	{
		return "Arguments: true|false";
	}
	

}
