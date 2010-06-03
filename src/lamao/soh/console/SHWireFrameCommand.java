/* 
 * SDAConsoleCommand.java 26.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

import com.jme.scene.Spatial;
import com.jme.scene.state.WireframeState;
import com.jme.system.DisplaySystem;

/**
 * Command for switching between normal view and wireframe.
 * @author lamao
 */
public class SHWireFrameCommand extends SHBasicCommand
{
	/** Spatial for applying command */
	private Spatial _spatial = null;
	
	
	public SHWireFrameCommand(Spatial spatial)
	{
		super(1, 1);
		_spatial = spatial;
	}
	
	public Spatial getSpatial()
	{
		return _spatial;
	}

	public void setSpatial(Spatial spatial)
	{
		_spatial = spatial;
	}
	
	@Override
	protected void processCommand(String[] args)
	{
		if (_spatial != null)
		{
			boolean wired = Boolean.parseBoolean(args[1]);
			WireframeState ws = DisplaySystem.getDisplaySystem().getRenderer()
					.createWireframeState();
			ws.setEnabled(wired);
			_spatial.setRenderState(ws);
			_spatial.updateRenderState();
		}
		
	}
	
}
