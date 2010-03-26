/* 
 * SDAConsoleCommand.java 26.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

/**
 * Command for console debugging. It works like 'echo' command by printing
 * command name.
 * @author lamao
 */
public class SHEchoCommand implements ISHCommandHandler
{

	/* (non-Javadoc)
	 * @see com.sdatetris.gamestates.ISDACommandHandler#execute(java.lang.String)
	 */
	@Override
	public String execute(String[] args)
	{
		return "Command was '" + args[0] + "'";
	}

}
