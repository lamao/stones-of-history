/* 
 * SHSetBonusCommand.java 27.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */

package lamao.soh.console;

import lamao.soh.core.SHBreakoutEntityFactory;
import lamao.soh.core.SHGamePack;
import lamao.soh.core.SHUtils;
import lamao.soh.core.bonuses.SHBonus;
import lamao.soh.core.entities.SHBrick;
import lamao.soh.utils.events.ISHEventHandler;
import lamao.soh.utils.events.SHEvent;

/**
 * Adds bonus to brick 
 * @author lamao
 *
 */
public class SHSetBonusCommand implements ISHEventHandler
{
	@Override
	public void processEvent(SHEvent event)
	{
		String[] args = (String[]) event.params.get("args");
		String brickName = args[1];
		String bonusName = args[2];
		SHBonus bonus = (SHBonus) new SHBreakoutEntityFactory().createEntity(SHUtils.buildMap(
				"type bonus|name " + bonusName));
		if (bonus != null)
		{
			//TODO: Change hardcoded 'brick' type to constant from brick class.
			SHBrick brick = (SHBrick) SHGamePack.scene.getEntity("brick", brickName);
			if (brick != null)
			{
				brick.setBonus(bonus);
			}
		}

	}
}
