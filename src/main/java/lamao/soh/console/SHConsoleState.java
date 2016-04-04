/* 
 * SHConsoleCommand.java 26.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.ColorRGBA;
import lamao.soh.utils.events.ISHEventHandler;
import lamao.soh.utils.events.SHEventDispatcher;

import com.jme3.input.KeyInput;

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
public class SHConsoleState extends AbstractAppState implements ActionListener
{
	/** Promt for console */
	public  final static String PROMT = "> ";

	/** Key for argument in event map */
	public final static String ARGS_KEY = "args";
	
	/** Key for console */
	public final static String CONSOLE_KEY = "console";
	
	/** Default history size */
	public final static int DEFAULT_HISTORY_SIZE = 10;
	
	/** Default number of lines in console */
	public final static int DEFAULT_LINES_NUMBER = 10;
	
	/** Default console color (used for commands */
	public final static ColorRGBA DEFAULT_COLOR = ColorRGBA.White.clone();
	
	/** Default color for messages */
	public final static ColorRGBA INFO_COLOR = ColorRGBA.Gray.clone();
	
	/** Default color for warnings */
	public final static ColorRGBA WARNING_COLOR = ColorRGBA.Yellow.clone();
	
	/** Default color for errors */
	public final static ColorRGBA ERROR_COLOR = ColorRGBA.Red.clone();


    /** Characters used for splitting command into arguments */
	private final static String SPLITTERS = "[ =]";

    /** Registered commands */
	private SHEventDispatcher commands = new SHEventDispatcher();

	/** Visual text components for displaying commands */
	private BitmapText[] console = null;
	
	/** Key which switches console on/off */
	private int switchKey = KeyInput.KEY_GRAVE;

	/** Command history. history[0] - the latest command */
	private SHCapacityList<String> history = null;
	
	/** Index of currently selected command from history */
	private int _historyIndex = -1;

    private SimpleApplication application;

	public SHConsoleState(SimpleApplication application)
	{
		this(application, KeyInput.KEY_GRAVE, DEFAULT_HISTORY_SIZE, DEFAULT_LINES_NUMBER);
	}
	
	public SHConsoleState(SimpleApplication application, int switchKey, int historySize, int numLines)
	{
        this.application = application;
		this.switchKey = switchKey;
		history = new SHCapacityList<String>(historySize);
        setEnabled(false);
		
		initGUI(numLines);
        bindKeys();
        initDefaultCommands();
    }
	
	private void initDefaultCommands()
	{
		add("exit", new SHExitCommand());
		add("echo", new SHEchoCommand());
	}
	
	/** Initializes all gui */
	private void initGUI(int numLines)
	{

        BitmapFont guiFont = application.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
		console = new BitmapText[numLines];
		for (int i = 0; i < numLines; i++)
		{
			console[i] = new BitmapText(guiFont);
            console[i].setSize(guiFont.getCharSet().getRenderedSize());
			console[i].setLocalTranslation(0, console[i].getHeight() * (numLines - i), 0);

            application.getGuiNode().attachChild(console[i]);
		}
		console[0].setText(PROMT);
	}
	
	private void bindKeys()
	{
	}
	
	public void add(String command, ISHEventHandler handler)
	{
		commands.addHandler(command, handler);
	}
	
	/**
	 * Sets given console commands and their handlers. Replaces current commands,
	 * but lives default commands (echo, exit)
	 * @param commands - map of commands and handlers
	 */
	public void setCommands(Map<String, ISHEventHandler> commands)
	{
		this.commands.reset();
		initDefaultCommands();
		for (Map.Entry<String, ISHEventHandler> command : commands.entrySet())
		{
			add(command.getKey(), command.getValue());
		}
	}
	
	public Set<String> getSupportedCommands()
	{
		return commands.getHandledEvents();
	}
	
	/** Returns text, currently showed in console */
	public String[] getContents()
	{
		String[] result = new String[console.length];
		for (int i = 0; i < console.length; i++)
		{
			result[i] = console[i].getText().toString();
		}
		return result;
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
		
		if (!commands.hasHandler(name))
		{
			warning("Command <" + name +"> is not supported");
		}
		else
		{
			String[] arguments = cmd.split(SPLITTERS);
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("args", arguments);
			args.put("console", this);
			commands.addEvent(name, this, args);
		}
	}
	
	
	/** Saves command to history */
	private void saveCommand(String cmd)
	{
		history.add(0, cmd);
	}

    @Override
    public void onAction(String name, boolean isPressed, float tpf)
    {
		if (!isPressed)
		{
			return;
		}
		
		if (name.equals(InputMapping.SWITCH.getName()))
		{
			setEnabled(!isEnabled());
			return;
		}
		
		if (!isEnabled())
		{
			return;
		}
		
		if (name.equals(InputMapping.EXECUTE.getName()))
		{
			String cmd = console[0].getText().toString();
			cmd = cmd.substring(PROMT.length(), cmd.length());
			scrollUp();			
			execute(cmd);
			_historyIndex = -1;
		}
		else if (name.equals(InputMapping.DELETE_LAST_CHARACTER.getName()))
		{
//			if (KeyInput.get().isControlDown())
//			{
//				setText("");
//			}
//			else
			{
				String s = console[0].getText();
				if (s.length() > PROMT.length())
				{
					console[0].setText(s.substring(0, s.length() - 1));
				}
			}
		}
		else if (name.equals(InputMapping.PREVIOUS_COMMAND.getName()))
		{
			if (history.isEmpty())
			{
				info("Command history is empty");
			}
			else
			{
				_historyIndex++;
				if (_historyIndex >= history.size())
				{
					_historyIndex = history.size() - 1;
					info("Command {" + _historyIndex + "} is the oldest saved command");
				}
				setText(history.get(_historyIndex));
			}
		}
		else if (name.equals(InputMapping.NEXT_COMMAND.getName()))
		{
			if (history.isEmpty())
			{
				info("Command history is empty");
			}
			else
			{
				_historyIndex--;
				if (_historyIndex < 0)
				{
					_historyIndex = 0;
					info("Command {" + _historyIndex + "} is the latest saved command");
				}
				setText(history.get(_historyIndex));
			}
		}
//		else if (isValidCharacter(character) && !Character.isISOControl(character))
//		{
//			console[0].setText(console[0].getText() + character);
//		}
		
	}
	
	private boolean isValidCharacter(char ch)
	{
		return true;
		//return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || 
//				(ch >= '0' && ch <= '9') || (ch == '.') || (ch == '=') || 
//				(ch == '-') || (ch == ' ') || (ch == '/');
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		if (enabled)
		{
			setText("");
		}
		super.setEnabled(enabled);
	}
	
	/** Print message and scroll console one line up */
	public void print(String message, ColorRGBA color)
	{
		setText(message, color);
		scrollUp();
		console[0].setText(PROMT);
	}
	
	public void info(String message)
	{
		print(message, INFO_COLOR);
	}
	
	public void warning(String message)
	{
		print(message, WARNING_COLOR);
	}
	
	public void error(String message)
	{
		print(message, ERROR_COLOR);
	}
	
	/** Prints text and does not scroll console */
	private void setText(String message, ColorRGBA color)
	{
		if (message == null)
		{
			message = "";
		}
		console[0].setColor(color);
		console[0].setText(PROMT + message);
	}
	
	private void setText(String message)
	{
		setText(message, DEFAULT_COLOR);
	}
	
	/** Scrolls all lines up one line */ 
	private void scrollUp()
	{
		if (console[0].getText().indexOf(PROMT) == 0)
		{
			console[0].setText(console[0].getText().substring(PROMT.length()));
		}
		for (int i = console.length - 1; i > 0; i--)
		{
			console[i].setColor(console[i - 1].getColor());
			console[i].setText(console[i - 1].getText());
		}
		console[0].setColor(DEFAULT_COLOR);
		console[0].setText(PROMT);
	}
	
	/** 'exit' command */ 
	private class SHExitCommand extends SHBasicCommand
	{
		@Override
		public void processCommand(String[] args)
		{
			setEnabled(false);
		}
	}

}
