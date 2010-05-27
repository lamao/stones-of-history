/* 
 * SHConsoleCommand.java 26.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

import java.util.HashMap;
import java.util.Map;

import lamao.soh.utils.events.ISHEventHandler;
import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

import com.jme.input.KeyInput;
import com.jme.input.KeyInputListener;
import com.jme.scene.Text;
import com.jme.system.DisplaySystem;
import com.jmex.game.state.BasicGameState;

/**
 * Game console. Can be used for debugging or any other purposes.
 * Command name should be separated by space from its arguments. The syntax of
 * rest command depends on concrete command.<br>
 * Now it supports just one line.<br>
 * Default controls are:<br>
 * <li><b>Backspace</b> removes one preceding character </li>
 * <li><b>CTRL+Backspace</b> removes whole line</li>
 * <li><b>ENTER</b> executes command</li>
 * <li><b>'exit'</b> command is for console disabling</li><br>
 * <br>
 * <b>NOTE: </b> Console is not modal. That is keys intersection is not 
 * maintained. You should do it youself.
 * @author lamao
 */
public class SHConsoleState extends BasicGameState implements KeyInputListener
{
	/** Name of this game state */
	public final static String STATE_NAME = "console";
	
	/** Characters used for splitting command into arguments */
	public final static String SPLITTERS = "[ =]";
	
	/** Promt for console */
	private final static String PROMT = "> ";
	
	/** Registered commands */
	private SHEventDispatcher _commands = new SHEventDispatcher();

	/** Visual text components for displaying commands */
	Text _console = null;
	
	public SHConsoleState(String name)
	{
		super(name);
		
		initGUI();
		bindKeys();
		
		add("exit", new SHExitHandler());
		
		rootNode.updateRenderState();
	}
	
	/** Initializes all gui */
	private void initGUI()
	{
		DisplaySystem display = DisplaySystem.getDisplaySystem();
		
		_console = Text.createDefaultTextLabel("console", PROMT);
		_console.setLocalTranslation(0, display.getHeight() 
				- _console.getHeight(), 0);
		rootNode.attachChild(_console);
	}
	
	private void bindKeys()
	{
		KeyInput.get().addListener(this);
	}
	
	public void add(String command, ISHEventHandler handler)
	{
		_commands.addHandler(command, handler);
	}
	
	/**
	 * Find handler for given command and call its <code>execute</code>
	 * method. 
	 * @param cmd - full command
	 * @return message from handler or error message if command is not supported
	 */
	public String execute(String cmd)
	{
		int spaceIndex = cmd.indexOf(' ', PROMT.length());
		String name = cmd.substring(PROMT.length(), 
				spaceIndex != -1 ? spaceIndex : cmd.length()); 
			
		String result = null;
		if (!_commands.hasHandler(name))
		{
			result = "Command <" + name +"> is not supported";
		}
		else
		{
			String[] arguments = cmd.substring(PROMT.length(), cmd.length())
					.split(SPLITTERS);
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("args", arguments);
			_commands.addEvent(name, this, args);
		}
		return result;
	}
	
	@Override
	public void onKey(char character, int keyCode, boolean pressed)
	{
		if (!pressed || !isActive())
		{
			return;
		}
		
		if (keyCode == KeyInput.KEY_RETURN)
		{
			String result = execute(_console.getText().toString());
			_console.print(PROMT + (result == null ? "" : result));
		}
		else if (keyCode == KeyInput.KEY_BACK)
		{
			if (KeyInput.get().isControlDown())
			{
				_console.print(PROMT);
			}
			else
			{
				StringBuffer s = _console.getText();
				if (s.length() > PROMT.length())
				{
					s.deleteCharAt(s.length() - 1);
				}
			}
		}
		else if (isValidCharacter(character))
		{
			_console.getText().append(character);
		}
		
	}
	
	private boolean isValidCharacter(char ch)
	{
		return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || 
				(ch >= '0' && ch <= '9') || (ch == '.') || (ch == '=') || 
				(ch == '-') || (ch == ' ');
	}

	@Override
	public void setActive(boolean active)
	{
		if (active)
		{
			_console.print(PROMT);
		}
		super.setActive(active);
	}
	
	/** Default handler of 'exit' command */ 
	private class SHExitHandler implements ISHEventHandler
	{
		@Override
		public void processEvent(SHEvent event)
		{
			setActive(false);
		}
	}

}
