/* 
 * SHBmFontParser.java 1.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.resparser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.logging.Logger;

import lamao.soh.utils.SHModelLoader;
import lamao.soh.utils.SHResourceManager;

import com.jmex.angelfont.BitmapFont;
import com.jmex.angelfont.BitmapFontLoader;

/**
 * Parsers BitmapFont<br>
 * Supported parameters:<br>
 * <li> type = texture</li>
 * <li> label = string label</li>
 * <li> path = path to font file</li>
 * <li> texture = path to font texture. </li>
 * @author lamao
 *
 */
public class SHBmFontParser implements ISHResourceParser
{
	
	private static Logger _logger = Logger.getLogger(SHModelLoader.class.getName());
	
	public final static String KEY_TEXTURE = "texture"; 
	
	/* (non-Javadoc)
	 * @see lamao.soh.utils.parsers.ISHResourceParser#parse(java.util.Map)
	 */
	@Override
	public void parse(Map<String, String> args, SHResourceManager manager)
	{
		File fontFile = new File(args.get(PATH_KEY));
		File textureFile = new File(args.get(KEY_TEXTURE));
		
		BitmapFont font = null;
		try
		{
			font = BitmapFontLoader.load(fontFile.toURI().toURL(),
					textureFile.toURI().toURL());
		}
		catch (MalformedURLException e)
		{
			_logger.warning("Malformed URL for font");
		}
		catch (IOException e)
		{
			_logger.warning("Can't load bitmap font. Font: " + fontFile + 
					". Texture: " + textureFile);
		}
		
		
		String label = args.get(LABEL_KEY);
		if (font != null && label != null)
		{
			manager.add(args.get(TYPE_KEY), label, font);
		}
		else
		{
			_logger.warning("Can't load font from file " + fontFile);
		}
	}

}
