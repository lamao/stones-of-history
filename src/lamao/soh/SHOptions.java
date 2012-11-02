/* 
 * SHOptions.java 25.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh;

import java.util.logging.Level;

import com.jme.input.KeyInput;

/**
 * Options for game
 * @author lamao
 *
 */
public class SHOptions
{
	public static float PaddleMouseSensitivity = 0.05f;
	public static float PaddleKeyboardSensitivity = 20f;
	
	public static int PaddleLeftKey = KeyInput.KEY_LEFT;
	public static int PaddleRightKey = KeyInput.KEY_RIGHT;
	
	/** Mouse button for releasing ball when paddle is stick or at the begin
	 * of the level
	 */
	public static int ReleaseBallButton = 0;
	
	/** Mouse button for firing from gun */
	public static int FireButton = 0;
	
	/** Log level. Indicates how verbose will be log output */
	public static Level LogLevel = Level.FINE;
}
