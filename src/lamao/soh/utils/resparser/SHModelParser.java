/* 
 * SHModelParser.java 1.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.resparser;

import java.io.File;
import java.util.Map;
import java.util.logging.Logger;

import com.jme.scene.Spatial;

import lamao.soh.utils.SHModelLoader;
import lamao.soh.utils.SHResourceManager;

/**
 * Parses and creates model from configuration line.<br>
 * Supported parameters:<br>
 * <li> type = MODEL</li>
 * <li> label = string label</li>
 * <li> path = path to model file</li>
 * to resource manager.
 * 
 * @author lamao
 *
 */
public class SHModelParser implements ISHResourceParser
{
	private static Logger _logger = Logger.getLogger(SHModelLoader.class.getName());
	
	/* (non-Javadoc)
	 * @see lamao.soh.utils.parsers.ISHResourceParser#parse(java.util.Map)
	 */
	@Override
	public void parse(Map<String, String> args, SHResourceManager manager)
	{
		File file = new File(args.get(PATH_KEY));
		Spatial model = SHModelLoader.load(file);
		String label = args.get(LABEL_KEY);
		if (model != null && label != null)
		{
			model.setName(label);
			manager.add(args.get(TYPE_KEY), label, model);
		}
		else
		{
			_logger.warning("Can't load model from file " + file);
		}
	}
}
