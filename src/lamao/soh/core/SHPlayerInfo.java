/* 
 * SHPlayerInfo.java 30.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Player info
 * @author lamao
 *
 */
@XStreamAlias("player")
public class SHPlayerInfo
{
	/** Lives remains */
	@XStreamAlias("lives")
	private int _lives;
	
	/** Player name */
	@XStreamAlias("name")
	private String _name;
	
	/** Currently played epoch */
	@XStreamAlias("current-epoch")
	private SHEpochInfo _currentEpoch;
	
	/** List of all available epochs */
	@XStreamAlias("epochs")
	private List<SHEpochInfo> _epochs = new ArrayList<SHEpochInfo>();

	public int getLives()
	{
		return _lives;
	}

	public void setLives(int lives)
	{
		_lives = lives;
	}

	public String getName()
	{
		return _name;
	}

	public void setName(String name)
	{
		_name = name;
	}

	public SHEpochInfo getCurrentEpoch()
	{
		return _currentEpoch;
	}

	public void setCurrentEpoch(SHEpochInfo currentEpoch)
	{
		_currentEpoch = currentEpoch;
	}

	public List<SHEpochInfo> getEpochs()
	{
		return _epochs;
	}

	public void setEpochs(List<SHEpochInfo> epochs)
	{
		_epochs = epochs;
	}
	
	public void save(FileOutputStream os)
	{
		XStream xstream = new XStream();
        xstream.processAnnotations(SHLevelInfo.class);
        xstream.processAnnotations(SHEpochInfo.class);
        xstream.processAnnotations(SHPlayerInfo.class);
        
		xstream.toXML(this, os);
	}
	
	public void save(File file) throws FileNotFoundException
	{
		save(new FileOutputStream(file));
	}
	
	public static SHPlayerInfo load(FileInputStream is)
	{
		XStream xstream = new XStream();
		xstream.processAnnotations(SHLevelInfo.class);
		xstream.processAnnotations(SHEpochInfo.class);
		xstream.processAnnotations(SHPlayerInfo.class);

		return (SHPlayerInfo) xstream.fromXML(is);
	}
	
	public static SHPlayerInfo load(File file) throws FileNotFoundException
	{
		return load(new FileInputStream(file));
	}
	
	/**
	 * Calculate min and max years for each epoch and sort them by these years.
	 * May be used after creating new player profile and after loading saved profile.
	 */
	public void updateEpochs()
	{
		for (SHEpochInfo epoch : _epochs)
		{
			epoch.updateYears();
		}
		Collections.sort(_epochs, new Comparator<SHEpochInfo>() 
		{
			@Override
			public int compare(SHEpochInfo o1, SHEpochInfo o2)
			{
				int result = 0;
				if (SHUtils.inRange(o1.getMinYear(), 
						o2.getMinYear() - 0.001f,
						o2.getMinYear() + 0.001f))
				{
					result = (o1.getMaxYear() < o2.getMaxYear()) ? -1 : 1;
				}
				else
				{
					result = (o1.getMinYear() < o2.getMinYear()) ? -1 : 1;
				}
				return result;
			}
		});
	}
	
	@Override
	public String toString()
	{
		return _name;
	}
}
