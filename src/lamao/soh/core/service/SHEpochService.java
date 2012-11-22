/** 
 * SHEpochServie.java 16.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.service;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.thoughtworks.xstream.XStream;

import lamao.soh.SHConstants;
import lamao.soh.core.model.entity.SHEpoch;
import lamao.soh.core.model.entity.SHLevel;

/**
 * @author lamao
 *
 */
public class SHEpochService
{
	private static final Logger LOGGER = Logger.getLogger(SHUserService.class.getCanonicalName());
	
	private static final String DEFAULT_EPOCH_CONFIGURATION_FILE = "epoch.xml";
	
	private SHConstants constants;
	
	private String epochConfigurationFile = DEFAULT_EPOCH_CONFIGURATION_FILE;
	
	public SHEpochService(SHConstants constants)
	{
		this.constants = constants;
	}
	
	public String getEpochConfigurationFile()
	{
		return epochConfigurationFile;
	}

	public void setEpochConfigurationFile(String epochConfigurationFile)
	{
		this.epochConfigurationFile = epochConfigurationFile;
	}

	/**
	 * Find all available epochs and load them.
	 * @return list of all available epochs
	 */
	public List<SHEpoch> getAll()
	{
		List<SHEpoch> epochs = new LinkedList<SHEpoch>();
		
		File file = new File(constants.EPOCHS_DIR);
		// build filter for *.xml files
		File[] files = file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file)
			{
				File configurationFile = getConfigurationFile(file.getAbsolutePath());				
				return file.isDirectory() && configurationFile.exists();
			}
		});
		
		if (files != null)
		{
			// load profiles
			SHEpoch epoch = null;
			for (int i = 0; i < files.length; i++)
			{
				try
				{
					epoch = load(getConfigurationFile(files[i].getAbsolutePath()));
					if (epoch != null)
					{
						epochs.add(epoch);
					}
				}
				catch (FileNotFoundException e)
				{
					LOGGER.warning("Can't load epoch from " + files[i]);
					e.printStackTrace();
				}
			}

		}
		
		sortEpochs(epochs);
		return epochs;
	}
	
	/**
	 * Loads epoch info from file
	 * @param file epoch configuration file
	 * @return epoch info
	 * @throws FileNotFoundException if file was not found
	 */
	private SHEpoch load(File file) throws FileNotFoundException
	{
		XStream xstream = new XStream();
		xstream.processAnnotations(SHLevel.class);
		xstream.processAnnotations(SHEpoch.class);

		return (SHEpoch) xstream.fromXML(new FileInputStream(file));
	}
	
	/**
	 * Sort epochs by their order from lowest to highest
	 * 
	 * @param epochs list of epochs to sort
	 */
	private void sortEpochs(List<SHEpoch> epochs)
	{
		Collections.sort(epochs, new Comparator<SHEpoch>() 
		{
			@Override
			public int compare(SHEpoch o1, SHEpoch o2)
			{
				int result = o1.getOrder() < o2.getOrder() ? -1 : 1;
				return result;
			}
		});
	}
	
	private File getConfigurationFile(String epochDirectory)
	{
		File result = new File(epochDirectory + "/" + epochConfigurationFile);
		return result;
	}
	
}
