/* 
 * SHGamePack.java 03.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import com.jme.input.InputHandler;

import lamao.soh.utils.SHResourceManager;
import lamao.soh.utils.events.SHEventDispatcher;

/**
 * Central storage for information that should be accessed from any part of
 * game (game context, options etc)
 * @author lamao
 *
 */
public class SHGamePack
{
//	public static SHEventDispatcher dispatcher = null;
//	public static SHScene scene = null;
	public static SHBreakoutGameContext context = null;
	public static SHResourceManager manager = null;
	public static InputHandler input = null;
	
	public static void initDefaults()
	{
//		dispatcher = new SHEventDispatcher();
//		scene = new SHScene();
		manager = new SHResourceManager();
	}

}
