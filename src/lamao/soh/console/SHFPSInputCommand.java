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

import lamao.soh.core.SHGamePack;
import lamao.soh.core.SHScene;

/**
 * Activates/deactivates FPS input handler in the scene. 
 * @author lamao
 *
 */
public class SHFPSInputCommand extends SHBasicCommand
{
	public SHFPSInputCommand()
	{
		super(1, 1);
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
				SHGamePack.scene.getRootNode().addController(
						new FPSInputController(SHGamePack.scene));
			}
		}
		else if (controller != null)
		{
			SHGamePack.scene.getRootNode().removeController(
					controller);
		}
	}
	
	private FPSInputController findController()
	{
		for (Controller controller : SHGamePack.scene.getRootNode().getControllers())
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
