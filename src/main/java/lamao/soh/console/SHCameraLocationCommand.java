/** 
 * SHCameraLocationCommand.java 23.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

/**
 * @author lamao
 *
 */
public class SHCameraLocationCommand extends SHBasicCommand
{
	private SimpleApplication simpleApplication;
	
	public SHCameraLocationCommand(SimpleApplication simpleApplication)
	{
		super(3, 3);
		this.simpleApplication = simpleApplication;
	}
	
	@Override
	public void processCommand(String[] args)
	{
		try
		{
    		Camera camera = simpleApplication.getCamera();
    		camera.setLocation(new Vector3f(
    				Float.parseFloat(args[1]),
    				Float.parseFloat(args[2]),
    				Float.parseFloat(args[3])));
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
