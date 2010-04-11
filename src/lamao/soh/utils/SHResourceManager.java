/* 
 * SHResourceManager.java 1.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import lamao.soh.utils.resparser.ISHResourceParser;
import lamao.soh.utils.resparser.SHBmFontParser;
import lamao.soh.utils.resparser.SHModelParser;
import lamao.soh.utils.resparser.SHSoundParser;
import lamao.soh.utils.resparser.SHTextureParser;
import lamao.soh.utils.resparser.SHTtFontParser;

/**
 * Class for managing and loading all game resources (sounds, textures).
 * It is implemented as Singleton pattern.
 * 
 * @author lamao
 *
 */
public class SHResourceManager
{
	/** Types of supported resources */
	public final static String TYPE_MODEL = "model";
	public final static String TYPE_TEXTURE = "texture";
	public final static String TYPE_SOUND = "sound";
	public final static  String TYPE_BMFONT = "bm_font";
	public final static String TYPE_TTFONT = "tt_font";
	
	/** Leading string of comment line */
	private static String COMMENT_PREFIX = "#";
	
	/** Separators for statements */
	private static String SEPARATORS = "[\t =]";
	
	/** Characters that defines string */
	private static String STRING_CHARS = "\"\'";
	
	/** Instance of this class */
	private static SHResourceManager _instance = null;
	
	/** Logger fot this class */
	private static Logger _logger = Logger.getLogger(SHResourceManager.class.getName());
	
	/** List of supported by this manager types */
	private List<String> _supportedTypes = new LinkedList<String>();;
	
	/** Map of loader for each supported type. There may be no parser for
	 * particular type. So it can be only stored but not loaded.
	 */
	private Map<String, ISHResourceParser> _parsers = 
			new HashMap<String, ISHResourceParser>();
	
	private Map<String, Map<String, Object>> _resources = 
			new HashMap<String, Map<String, Object>>();
	
	/** List which represents resource file to parse */
	private List<String> _config = new LinkedList<String>();
	
	protected SHResourceManager()
	{
		setParser(TYPE_MODEL, new SHModelParser());
		setParser(TYPE_TEXTURE, new SHTextureParser());
		setParser(TYPE_SOUND, new SHSoundParser());
		setParser(TYPE_BMFONT, new SHBmFontParser());
		setParser(TYPE_TTFONT, new SHTtFontParser());
	}
	
	/**
	 * Returns instance of class
	 */
	public static SHResourceManager getInstance()
	{
		if (_instance == null)
		{
			_instance = new SHResourceManager();
		}
		return _instance;
	}
	
	public List<String> getSupportedTypes()
	{
		return _supportedTypes;
	}
	
	public boolean isSupported(String type)
	{
		return _supportedTypes.contains(type);
	}
	
	public ISHResourceParser getParser(String type)
	{
		return _parsers.get(type);
	}
	
	/**
	 * Sets given parser for specified type. If there is parser for such type
	 * it will be overridden. If such type is not yet supported it will be
	 * added to supported types 
	 * @param type - resource type
	 * @param parser - parser for specified resource type
	 */
	public void setParser(String type, ISHResourceParser parser)
	{
		_parsers.put(type, parser);
		if (!isSupported(type))
		{
			_supportedTypes.add(type);
		}
	}
	
	public void removeParser(String type)
	{
		_parsers.remove(type);
	}
	
	public Map<String, ISHResourceParser> getParsers()
	{
		return _parsers;
	}
	
	/**
	 * Adds given resource to storage. If there is no storage for specified type
	 * it will be created.
	 * @param type
	 * @param label
	 * @param resource
	 */
	public void add(String type, String label, Object resource)
	{
		Map<String, Object> _map = _resources.get(type);
		if (_map == null)
		{
			_map = new HashMap<String, Object>();
			_resources.put(type, _map);
		}
		_map.put(label, resource);
		
		if (!isSupported(type))
		{
			_supportedTypes.add(type);
		}
		
	}
	
	/**
	 * Removes specified resource. Does not add/remove type to/from supported 
	 * types.
	 * @param type
	 * @param label
	 */
	public void remove(String type, String label)
	{
		if (get(type) != null)
		{
			get(type).remove(label);
		}
	}
	
	/**
	 * Removes all resource of specified type. Does not remove type from
	 * supported type
	 * @param type
	 */
	public void remove(String type)
	{
		if (get(type) != null)
		{
			get(type).clear();
		}
	}
	
	public Object get(String type, String label)
	{
		Object result = null;
		Map<String, Object> _map = _resources.get(type);
		if (_map != null)
		{
			result = _map.get(label);
		}
		return result;
	}
	
	public Map<String, Object> get(String type)
	{
		return _resources.get(type);
	}
	
	public void readConfig(File file)
	{
		try
		{
			readConfig(new FileInputStream(file));
		}
		catch (FileNotFoundException e)
		{
			_logger.warning("Can't find resource description file " + file);
			return;
		}
	}
	
	public void readConfig(InputStream is)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = null;
			_config.clear();
			do
			{
				line = reader.readLine();
				if (line != null)
				{
					_config.add(line);
				}
			}
			while (line != null); 
			reader.close();
			refine(_config);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public List<String> getConfig()
	{
		return _config;
	}
	
	/** 
	 * Loads all resources denoted in file
	 * 
	 * @param file - file where resources are described.
	 * @return number of resources has been loaded.
	 */
	public int loadAll(File file)
	{
		try
		{
			return loadAll(new FileInputStream(file));
		}
		catch (FileNotFoundException e)
		{
			_logger.warning("Can't find resource description file " + file);
			return 0;
		}
	}
	
	/** 
	 * Loads all resources denoted in stream
	 * @param is - stream where resources are desribed
	 * @return number of resources has been loaded
	 */
	public int loadAll(InputStream is)
	{
		readConfig(is);
		return parseConfig(_config);
	}
	
	public int parseConfig(List<String> config)
	{
		int loaded = 0;
		for (String line : config)
		{
			loaded += parseOneLine(line);
		}
		
		return loaded;
	}
	
	/**
	 * Returns number of parsed lines.
	 * @param line
	 * @return 0 if it is comment line. <br>
	 * 		   1 otherwise
	 */
	public int parseOneLine(String line)
	{
		int result = 0;
		if (line != null && line.length() > 0 
				&& !line.startsWith(COMMENT_PREFIX))
		{
			Map<String, String> args = split(line);
			String type = args.get(ISHResourceParser.TYPE_KEY);
			if (!isSupported(type) || getParser(type) == null)
			{
				_logger.warning("Type <" + args.get(type) + 
						"> is not supported");
			}
			else
			{
				getParser(type).parse(args, this);
			}
			result = 1;
		}
		return result;
	}

	/**
	 * Splits line that describes resource into tokens. Line must consist of
	 * pairs <code>key=value</code>. Value depends on key, but if it is two 
	 * (or more) word string it must be in quotes (like <code>"some value"</code>)
     *
	 * @param string - line that describes resource
	 * @return map of <parameter, value> pairs
	 */
	private Map<String, String> split(String string)
	{
		int i = 0;
		Map<String, String> result = new HashMap<String, String>();
		boolean readString = false;
		int end = 0;
		String item = null;
		boolean isKey = true;
		String key = null;
		
		
		while (i < string.length())
		{
			// skip separators
			while (i < string.length() && SEPARATORS.indexOf(string.charAt(i)) != -1)
			{
				i++;
			}
			if (i >= string.length())
			{
				break;
			}
			// check for string
			if (isString(string.charAt(i)))
			{
				readString = true;
				i++;
			}
			end = i + 1;
			while ((!isSeparator(string.charAt(end)) && !readString) || 
					(!isString(string.charAt(end)) && readString))
			{
				end++;
			}
			item = string.substring(i, end);
			if (!readString)
			{
				item.toLowerCase();
			}
			if (isKey)
			{
				key = string.substring(i, end);
				isKey = false;
			}
			else
			{
				result.put(key, string.substring(i, end));
				isKey = true;
			}
			i = end + 1;
			readString = false;
		}
		
		return result;
	}
	
	private boolean isSeparator(char ch)
	{
		return SEPARATORS.indexOf(ch) != -1;
	}
	
	private boolean isString(char ch)
	{
		return STRING_CHARS.indexOf(ch) != -1;
	}
	
	/**
	 * Removes all comment and empty (without characters) lines
	 * @param config
	 */
	private void refine(List<String> config)
	{
		String line = null;

		for (int i = 0; i < config.size(); i++)
		{
			line = config.get(i);
			if (line.length() == 0 || line.startsWith(COMMENT_PREFIX))
			{
				config.remove(i--);
			}
		}
	}
}