/* 
 * SHGenerateCommand.java 29.03.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.console;

import com.jme.scene.Node;

import lamao.soh.SHLevelGenerator;
import lamao.soh.core.SHLevel;

/**
 * Generates new level using <code>SHLevelGenerator</code>
 * @author lamao
 *
 */
public class SHGenerateCommand implements ISHCommandHandler
{
	SHLevel _level;
	Node _rootNode;
	
	public SHGenerateCommand(SHLevel level, Node rootNode)
	{
		_level = level;
		_rootNode = rootNode;
	}
	
	/* (non-Javadoc)
	 * @see lamao.soh.console.ISHCommandHandler#execute(java.lang.String[])
	 */
	@Override
	public String execute(String[] args)
	{
		_level.clear();
		SHLevelGenerator.generate(_level);
		_rootNode.updateRenderState();
		return null;
	}

}
