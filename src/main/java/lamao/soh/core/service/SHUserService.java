/** 
 * SHUserService.java 16.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import lamao.soh.SHConstants;
import lamao.soh.core.model.entity.SHUser;

import com.thoughtworks.xstream.XStream;

/**
 * Service for user info related operations
 * @author lamao
 *
 */
public class SHUserService
{
	private static final Logger LOGGER = Logger.getLogger(SHUserService.class.getCanonicalName());
	
	private static final String FILE_EXTENSION_XML = ".xml";
	
	private SHConstants constants;
	
	public SHUserService(SHConstants constants)
	{
		this.constants = constants;
	}

	public void save(SHUser user) throws IOException
	{
		XStream xstream = new XStream();
        xstream.processAnnotations(SHUser.class);

        File file = getProfileFile(user.getName());
        if (!file.exists())
        {
        	file.createNewFile();
        }
		xstream.toXML(user, new FileOutputStream(file));
	}
	
	public SHUser load(String username) throws FileNotFoundException
	{
		return load(getProfileFile(username));
	}
	
	private SHUser load(File file) throws FileNotFoundException
	{
		XStream xstream = new XStream();
		xstream.processAnnotations(SHUser.class);

		return (SHUser) xstream.fromXML(new FileInputStream(file));
	}
	
	/**
	 * Find player's profiles in specified directory and load them if possibly.
	 * @param baseDir - base directory to search profiles
	 * @return array of correct profiles.
	 */
	public List<SHUser> getAll()
	{
		List<SHUser> players = new LinkedList<SHUser>();
		
		File file = new File(constants.PLAYERS_DIR);
		// build filter for *.xml files
		File[] files = file.listFiles(new XmlFilenameFilter());
		
		if (files != null)
		{
			// load profiles
			SHUser player = null;
			for (int i = 0; i < files.length; i++)
			{
				try
				{
					player = load(files[i]);
					if (player != null)
					{
						players.add(player);
					}
				}
				catch (FileNotFoundException e)
				{
					LOGGER.warning("Can't load player profile from " + files[i]);
					e.printStackTrace();
				}
			}

		}
		
		return players;
	}
	
	/**
	 * Deleted saved profiles from disk
	 * @param username username
	 */
	public boolean delete(String username)
	{
		File file = getProfileFile(username);
		if (!file.exists())
		{
			return false;
		}
		else
		{
			file.delete();
			return true;
		}
	}
	
	public boolean isExists(String username)
	{
		File file = getProfileFile(username);
		return file.exists();
	}

	/**
	 * Builds filename from username and return {@link File} instanse
	 * @param username username
	 * @return file with profile for specified username
	 */
	private File getProfileFile(String username)
	{
		return new File(constants.PLAYERS_DIR + username + FILE_EXTENSION_XML);
	}
}
