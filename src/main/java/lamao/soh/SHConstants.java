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
    public static List<String> PATHS_TO_ASSETS = Arrays.asList("data", "assets");

	/** Directory where player's profiles are stored */
	public String PLAYERS_DIR = "data/players/";

	/** Directory where epochs are located */
	public String EPOCHS_DIR = "data/epochs";

	public final String CURSOR_DEFAULT = "default";
	
	public final Vector3f DEFAULT_BALL_VELOCITY = new Vector3f(-6, 0, -6);

    public static final String SPRING_CONFIG_LOCATION = "data/spring/rootApplicationContext.xml";
    public static final String SPRING_APPLICATION_BEAN_NAME = "application";

}
