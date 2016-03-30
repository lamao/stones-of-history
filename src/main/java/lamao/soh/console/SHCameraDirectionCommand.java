/** 
 * SHCameraDirectionCommand.java 23.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.system.DisplaySystem;

/**
 * Console command to setup camera location
 * @author lamao
 *
 */
public class SHCameraDirectionCommand extends SHBasicCommand
{
	private DisplaySystem displaySystem;
	
	
	
	public SHCameraDirectionCommand(DisplaySystem displaySystem)
	{
		super(3, 3);
		this.displaySystem = displaySystem;
	}
	
	@Override
	public void processCommand(String[] args)
	{
		try
		{
    		Camera camera = displaySystem.getRenderer().getCamera();
    		camera.lookAt(new Vector3f(
    				Float.parseFloat(args[1]),
    				Float.parseFloat(args[2]),
    				Float.parseFloat(args[3])),
    				Vector3f.UNIT_Y);
    		camera.update();
		} 
		catch (NumberFormatException e)
		{
			error("Coordinates are float numbers");
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getHelpMessage()
	{
		return "Arguments: x y z";
	}

}
