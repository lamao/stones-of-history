/* 
 * SHConstants.java 10.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh;

/**
 * Defines all game constants.
 * @author lamao
 *
 */
public interface SHConstants
{
	public final int BUILD_NUMBER = 1;
	
	// Entity names. Defines names of resources in res manager, in
	// configuration files and obj-files
	public String LEFT_WALL = "left-wall";
	public String TOP_WALL = "top-wall";
	public String RIGHT_WALL = "right-wall";
	public String BOTTOM_WALL = "bottom-wall";
	public String BRICK = "brick";
	public String BALL = "ball";
	public String PADDLE = "paddle";
	public String PADDLE_GUN = "paddle-gun-model";
	public String DECORATION = "decoration";
	public String BULLET = "bullet";
	
	/** Directory where player's profiles are stored */
	public String PLAYERS_DIR = "data/players/";
	
	public String UI_FILE = "data/nifty/main.xml";
	public String UI_SCREEN_START = "start";
	public String UI_CURSOR_DEFAULT = "default";

}
