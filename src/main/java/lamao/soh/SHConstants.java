/* 
 * SHConstants.java 10.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh;

import com.jme3.math.Vector3f;

import java.util.Arrays;
import java.util.List;

/**
 * Defines all game constants.
 * @author lamao
 *
 */
public class SHConstants
{
	public static final int BUILD_NUMBER = 1;
	
	// Entity names. Defines names of resources in res manager, in
	// configuration files and obj-files
	public static final String LEFT_WALL = "left-wall";
	public static final String TOP_WALL = "top-wall";
	public static final String RIGHT_WALL = "right-wall";
	public static final String BOTTOM_WALL = "bottom-wall";
	public static final String BRICK = "brick";
	public static final String BALL = "ball";
	public static final String PADDLE = "paddle";
	public static final String PADDLE_GUN = "paddle-gun-model";
	public static final String DECORATION = "decoration";
	public static final String BULLET = "bullet";
    public static List<String> PATHS_TO_ASSETS = Arrays.asList("data", "assets");

	/** Directory where player's profiles are stored */
	public final String PLAYERS_DIR = "data/players/";

	/** Directory where epochs are located */
	public final String EPOCHS_DIR = "data/epochs";

	public final String CURSOR_DEFAULT = "default";
	


    public static final String SPRING_CONFIG_LOCATION = "data/spring/rootApplicationContext.xml";
    public static final String SPRING_APPLICATION_BEAN_NAME = "application";

}
