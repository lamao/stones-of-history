/* 
 * SHActivateBonusCommand.java Jun 3, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

import lamao.soh.core.ISHEntityFactory;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.core.collisionhandlers.SHBonusPaddleCollisionHandler;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * @author lamao
 *
 */
public class SHActivateBonusCommand extends SHBasicCommand
{
	private SHScene scene;
	private SHEventDispatcher dispatcher;
	private ISHEntityFactory entityFactory;

	
	
	public SHActivateBonusCommand(SHEventDispatcher dispatcher, SHScene scene, 
			ISHEntityFactory entityFactory)
	{
		super(1, 1);
		this.scene = scene;
		this.dispatcher = dispatcher;
		this.entityFactory = entityFactory;
	}
	

	public SHScene getScene()
	{
		return scene;
	}

	public void setScene(SHScene scene)
	{
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

	@Override
	public void processCommand(String[] args)
	{
		SHBonus bonus = (SHBonus) entityFactory.createEntity(
				SHUtils.buildMap("type bonus|name " + args[1]));
		if (bonus == null)
		{
			warning("Can't create bonus <" + args[1] + ">");
		}
		else			
		{
			new SHBonusPaddleCollisionHandler(dispatcher, scene)
				.processEvent(new SHEvent("", this, SHUtils.buildEventMap("src", bonus)));
		}
		
	}
	
	@Override
	protected String getHelpMessage()
	{
		return "Arguments: <bonus name>";
	}

}
