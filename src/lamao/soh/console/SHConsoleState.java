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
// TODO: Write unit tests for console
public class SHConsoleState extends BasicGameState implements KeyInputListener
{
	/** Name of this game state */
	public final static String STATE_NAME = "console";

	/** Key for argument in event map */
	public final static String ARGS_KEY = "args";
	
	/** Key for console */
	public final static String CONSOLE_KEY = "console";
	
	/** Default history size */
	public final static int DEFAULT_HISTORY_SIZE = 10;
	
	/** Default number of lines in console */
	public final static int DEFAULT_LINES_NUMBER = 10;
		
	/** Characters used for splitting command into arguments */
	private final static String SPLITTERS = "[ =]";
	
	/** Promt for console */
	private final static String PROMT = "> ";
	
	/** Registered commands */
	private SHEventDispatcher _commands = new SHEventDispatcher();

	/** Visual text components for displaying commands */
	private Text[] _console = null;
	
	/** Key which switches console on/off */
	private int _switchKey = KeyInput.KEY_GRAVE;

	/** Command history. _history[0] - the latest command */
	private SHCapacityList<String> _history = null;
	
	/** Index of currently selected command from history */
	private int _historyIndex = -1;
	
	public SHConsoleState(String name)
	{
		this(KeyInput.KEY_GRAVE, DEFAULT_HISTORY_SIZE, DEFAULT_LINES_NUMBER);
		setName(name);
	}
	
	public SHConsoleState(int switchKey, int historySize, int numLines)
	{
		super(STATE_NAME);
		_switchKey = switchKey;
		_history = new SHCapacityList<String>(historySize);
		
		initGUI(numLines);
		bindKeys();
		
		add("exit", new SHExitHandler());
		
		rootNode.updateRenderState();
	}
	
	/** Initializes all gui */
	private void initGUI(int numLines)
	{
		DisplaySystem display = DisplaySystem.getDisplaySystem();
		
		_console = new Text[numLines];
		for (int i = 0; i < numLines; i++)
		{
			_console[i] = Text.createDefaultTextLabel("console" + i, "");
			_console[i].setLocalTranslation(0, display.getHeight() 
					- _console[i].getHeight() * (numLines - i), 0);
			rootNode.attachChild(_console[i]);
		}
		_console[0].print(PROMT);
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
	public void execute(String cmd)
	{
		int spaceIndex = cmd.indexOf(' ');
		String name = cmd.substring(0, spaceIndex != -1 ? spaceIndex : cmd.length());
		
		saveCommand(cmd);
		
		if (!_commands.hasHandler(name))
		{
			print("Command <" + name +"> is not supported");
		}
		else
		{
			String[] arguments = cmd.substring(PROMT.length(), cmd.length())
					.split(SPLITTERS);
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("args", arguments);
			args.put("console", this);
			_commands.addEvent(name, this, args);
		}
	}
	
	
	/** Saves command to history */
	private void saveCommand(String cmd)
	{
		_history.add(0, cmd);
	}
	
	@Override
	public void onKey(char character, int keyCode, boolean pressed)
	{
		if (!pressed)
		{
			return;
		}
		
		if (keyCode == _switchKey)
		{
			setActive(!isActive());
			return;
		}
		
		if (!isActive())
		{
			return;
		}
		
		if (keyCode == KeyInput.KEY_RETURN)
		{
			String cmd = _console[0].getText().toString();
			cmd = cmd.substring(PROMT.length(), cmd.length());
			scrollUp();			
			execute(cmd);
			_historyIndex = -1;
		}
		else if (keyCode == KeyInput.KEY_BACK)
		{
			if (KeyInput.get().isControlDown())
			{
				print("");
			}
			else
			{
				StringBuffer s = _console[0].getText();
				if (s.length() > PROMT.length())
				{
					s.deleteCharAt(s.length() - 1);
				}
			}
		}
		else if (keyCode == KeyInput.KEY_UP)
		{
			_historyIndex++;
			if (_historyIndex >= _history.size())
			{
				_historyIndex = _history.size() - 1;
				print("Command {" + _historyIndex + "} is the oldest saved command");
			}
			else
			{
				setText(_history.get(_historyIndex));
			}
		}
		else if (keyCode == KeyInput.KEY_DOWN)
		{
			_historyIndex--;
			if (_historyIndex < 0)
			{
				_historyIndex = 0;
				print("Command {" + _historyIndex + "} is the latest saved command");
			}
			else
			{
				setText(_history.get(_historyIndex));
			}
		}
		else if (isValidCharacter(character))
		{
			_console[0].getText().append(character);
		}
		
	}
	
	private boolean isValidCharacter(char ch)
	{
		return true;
		//return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || 
//				(ch >= '0' && ch <= '9') || (ch == '.') || (ch == '=') || 
//				(ch == '-') || (ch == ' ') || (ch == '/');
	}

	@Override
	public void setActive(boolean active)
	{
		if (active)
		{
			print("");
		}
		super.setActive(active);
	}
	
	/** Print message and scroll console one line up */
	public void print(String message)
	{
		setText(message);
		scrollUp();
		_console[0].print(PROMT);
	}
	
	/** Prints text and does not scroll console */
	public void setText(String message)
	{
		if (message == null)
		{
			message = "";
		}
		_console[0].print(PROMT + message);
	}
	
	/** Scrolls all lines up one line */ 
	private void scrollUp()
	{
		if (_console[0].getText().indexOf(PROMT) == 0)
		{
			_console[0].getText().replace(0, PROMT.length(), "");
		}
		for (int i = _console.length - 1; i > 0; i--)
		{
			_console[i].print(_console[i - 1].getText());
		}
		_console[0].print(PROMT);
	}
	
	/** Default handler of 'exit' command */ 
	private class SHExitHandler extends SHBasicCommand
	{
		@Override
		public void processCommand(String[] args)
		{
			setActive(false);
		}
	}

}
