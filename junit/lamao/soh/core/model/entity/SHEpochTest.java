/* 
 * SHEpochInfoTest.java Jun 2, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core.model.entity;


import java.io.FileNotFoundException;

import lamao.soh.core.SHUtils;
import lamao.soh.core.model.entity.SHEpoch;
import lamao.soh.core.model.entity.SHLevel;

import org.testng.annotations.Test;
import static org.testng.Assert.*;
import com.thoughtworks.xstream.XStream;

/**
 * @author lamao
 *
 */
public class SHEpochTest
{
	private static String NAME = "E-E-EPOCH";
	private static String LEVEL1 = SHLevelTest.XML_LEVEL;
	private static String LEVEL2 = String.format(
			SHLevelTest.XML_LEVEL_TEMPLATE,
			SHLevelTest.MODELS + "2", SHLevelTest.INTRO + "2", 
			!SHLevelTest.COMPLETED, SHLevelTest.NAME + "2");
	
	private static String XML_EPOCH = 
		"<epoch>\n" +
		"  <name>" + NAME + "</name>\n" +
		"  <levels>\n" +
		"    " + LEVEL1 + "\n" +
		"    " + LEVEL2 + "\n" + 
		"  </levels>\n" +
		"</epoch>";
			
	@Test
	public void testToXml() throws FileNotFoundException
	{
		SHEpoch epoch = new SHEpoch();
		epoch.setName(NAME);
		
		SHLevel level = new SHLevel();
		level.setYear(SHLevelTest.YEAR);
		level.setModels(SHLevelTest.MODELS);
		level.setIntro(SHLevelTest.INTRO);
		level.setCompleted(SHLevelTest.COMPLETED);
		level.setName(SHLevelTest.NAME);		
		epoch.getLevels().add(level);
		
		level = new SHLevel();
		level.setYear(SHLevelTest.YEAR);
		level.setModels(SHLevelTest.MODELS + "2");
		level.setIntro(SHLevelTest.INTRO + "2");
		level.setCompleted(!SHLevelTest.COMPLETED);
		level.setName(SHLevelTest.NAME + "2");		
		epoch.getLevels().add(level);
		
		XStream xstream = new XStream();
        xstream.processAnnotations(SHLevel.class);
        xstream.processAnnotations(SHEpoch.class);
        
        String xml = xstream.toXML(epoch);
        assertEquals(XML_EPOCH.replaceAll(" ", ""), xml.replaceAll(" ", ""));
	}
	
	@Test
	public void testFromXml()
	{
		XStream xstream = new XStream();
        xstream.processAnnotations(SHLevel.class);
        xstream.processAnnotations(SHEpoch.class);
        
        SHEpoch epoch = (SHEpoch) xstream.fromXML(XML_EPOCH);
        
        assertEquals(NAME, epoch.getName());
        assertEquals(2, epoch.getLevels().size());
	}
	
}
