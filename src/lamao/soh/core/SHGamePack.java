/* 
 * SHGamePack.java 03.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import lamao.soh.utils.events.SHEventDispatcher;

/**
 * Central storage for information that should be accessed from any part of
 * game (game context, options etc)
 * @author lamao
 *
 */
public class SHGamePack
{
	public static SHEventDispatcher dispatcher = null;
	public static SHScene scene = null;
	public static ISHGameContext context = null;

}
