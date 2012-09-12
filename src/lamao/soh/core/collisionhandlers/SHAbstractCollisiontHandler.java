/* 
 * SHAbstractCollisiontHandler.java 09.09.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.collisionhandlers;

import lamao.soh.core.SHScene;
import lamao.soh.utils.events.ISHEventHandler;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 *
 */
public abstract class SHAbstractCollisiontHandler implements ISHEventHandler
{
	
	protected SHEventDispatcher dispatcher;
	
	protected SHScene scene;
	
	public SHAbstractCollisiontHandler()
	{
	}

	public SHAbstractCollisiontHandler(
			SHEventDispatcher dispatcher,
			SHScene scene)
	{
		this.dispatcher = dispatcher;
		this.scene = scene;
	}
	
	public SHEventDispatcher getDispatcher()
	{
		return dispatcher;
	}

	public void setDispatcher(SHEventDispatcher dispatcher)
	{
		this.dispatcher = dispatcher;
	}

	public SHScene getScene()
	{
		return scene;
	}

	public void setScene(SHScene scene)
	{
		this.scene = scene;
	}
	
	

}
