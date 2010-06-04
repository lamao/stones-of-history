/* 
 * SHActivateBonusCommand.java Jun 3, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

import lamao.soh.core.SHBreakoutEntityFactory;
import lamao.soh.core.SHUtils;
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.core.collisionhandlers.SHBonusPaddleCollisionHandler;
import lamao.soh.utils.events.SHEvent;

/**
 * @author lamao
 *
 */
public class SHActivateBonusCommand extends SHBasicCommand
{
	public SHActivateBonusCommand()
	{
		super(1, 1);
	}
	
	@Override
	public void processCommand(String[] args)
	{
		SHBonus bonus = (SHBonus) new SHBreakoutEntityFactory().createEntity(
				SHUtils.buildMap("type bonus|name " + args[1]));
		if (bonus == null)
		{
			warning("Can't create bonus <" + args[1] + ">");
		}
		else			
		{
			new SHBonusPaddleCollisionHandler().processEvent(new SHEvent(
					"", this, SHUtils.buildEventMap("src", bonus)));
		}
		
	}

}
