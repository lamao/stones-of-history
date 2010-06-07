/* 
 * SHEchoCommand.java Jun 4, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

/**
 * Echo command. Just for testing
 * @author lamao
 *
 */
public class SHEchoCommand extends SHBasicCommand
{
	public SHEchoCommand()
	{
		super(1, 2);
	}
	
	@Override
	protected void processCommand(String[] args)
	{
		if (args.length == 2 )
		{
			print(args[1], SHConsoleState.DEFAULT_COLOR);
		}
		else if (args[1].equals("info"))
		{
			info(args[2]);
		}
		else if (args[1].equals("warning"))
		{
			warning(args[2]);
		}
		else if (args[1].equals("error"))
		{
			error(args[2]);
		}
	}
	
	@Override
	protected String getHelpMessage()
	{
		return "Arguments: [info|warning|error] <message>";
	}

}
