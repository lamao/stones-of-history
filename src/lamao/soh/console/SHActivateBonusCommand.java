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
import lamao.soh.utils.events.ISHEventHandler;
import lamao.soh.utils.events.SHEvent;

/**
 * @author lamao
 *
 */
public class SHActivateBonusCommand implements ISHEventHandler
{
	@Override
	public void processEvent(SHEvent event)
	{
		String[] args = (String[])event.params.get("args");
		SHBonus bonus = (SHBonus) new SHBreakoutEntityFactory().createEntity(
				SHUtils.buildMap("type bonus|name " + args[1]));
		if (bonus != null)
		{
			new SHBonusPaddleCollisionHandler().processEvent(new SHEvent(
					"", this, SHUtils.buildEventMap("src", bonus)));
		}
		
	}

}
