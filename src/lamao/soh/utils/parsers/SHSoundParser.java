/* 
 * SHSoundParser.java 1.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.parsers;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.logging.Logger;

import lamao.soh.utils.SHModelLoader;
import lamao.soh.utils.SHResourceManager;

import com.jmex.audio.AudioSystem;
import com.jmex.audio.AudioTrack;

/**
 * Parser for texture configuration. Creats <code>TextureState</code> <br>
 * Supported parameters:<br>
 * <li> type = sound</li>
 * <li> label = string label</li>
 * <li> path = path to model file</li>
 * <li> stream (optional) = true or false (default). Defines where sound
 * will be played as stream directly from file or first will be loaded into
 * memory</li>
 * <li> loop (optional) = true or false (default). Loop playing (e.g. background
 * music)
 * to resource manager.
 * @author lamao
 *
 */
public class SHSoundParser implements ISHResourceParser
{
	private static Logger _logger = Logger.getLogger(SHModelLoader.class.getName());
	
	public final static String KEY_STREAM = "stream";
	public final static String KEY_LOOP = "loop";
	
	/* (non-Javadoc)
	 * @see lamao.soh.utils.parsers.ISHResourceParser#parse(java.util.Map)
	 */
	@Override
	public void parse(Map<String, String> args, SHResourceManager manager)
	{
		File file = new File(args.get(PATH_KEY));
		if (!file.exists())
		{
			_logger.warning("Can't find sound file " + file);
			return;
		}
		
		AudioTrack audio = null;
		boolean stream = false;
		if (args.get(KEY_STREAM) != null)
		{
			stream = Boolean.parseBoolean(args.get(KEY_STREAM));
		}
			
		try
		{
			audio = AudioSystem.getSystem().createAudioTrack(
					file.toURI().toURL(), stream);
		}
		catch (MalformedURLException e)
		{
			_logger.warning("Can't load sound from file " + file);
			return;
		}

		if (args.get(KEY_LOOP) != null)
		{
			boolean loop = Boolean.parseBoolean(args.get(KEY_LOOP));
			audio.setLooping(loop);
		}
		
		String label = args.get(LABEL_KEY);
		if (audio != null && label != null)
		{
			manager.add(args.get(TYPE_KEY), label, audio);
		}
		else
		{
			_logger.warning("Can't load sound from file " + file);
		}
	}
	
}
