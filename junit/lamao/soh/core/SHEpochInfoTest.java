/* 
 * SHEpochInfoTest.java Jun 2, 2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;


import java.io.FileNotFoundException;

import org.testng.annotations.Test;
import static org.testng.Assert.*;
import com.thoughtworks.xstream.XStream;

/**
 * @author lamao
 *
 */
public class SHEpochInfoTest
{
	private static String NAME = "E-E-EPOCH";
	private static String LEVEL1 = SHLevelInfoTest.XML_LEVEL;
	private static String LEVEL2 = String.format(
			SHLevelInfoTest.XML_LEVEL_TEMPLATE,
			SHLevelInfoTest.MODELS + "2", SHLevelInfoTest.INTRO + "2", 
			!SHLevelInfoTest.COMPLETED, SHLevelInfoTest.NAME + "2");
	
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
		SHEpochInfo epoch = new SHEpochInfo();
		epoch.setName(NAME);
		
		SHLevelInfo level = new SHLevelInfo();
		level.setYear(SHLevelInfoTest.YEAR);
		level.setModels(SHLevelInfoTest.MODELS);
		level.setIntro(SHLevelInfoTest.INTRO);
		level.setCompleted(SHLevelInfoTest.COMPLETED);
		level.setName(SHLevelInfoTest.NAME);		
		epoch.getLevels().add(level);
		
		level = new SHLevelInfo();
		level.setYear(SHLevelInfoTest.YEAR);
		level.setModels(SHLevelInfoTest.MODELS + "2");
		level.setIntro(SHLevelInfoTest.INTRO + "2");
		level.setCompleted(!SHLevelInfoTest.COMPLETED);
		level.setName(SHLevelInfoTest.NAME + "2");		
		epoch.getLevels().add(level);
		
		XStream xstream = new XStream();
        xstream.processAnnotations(SHLevelInfo.class);
        xstream.processAnnotations(SHEpochInfo.class);
        
        String xml = xstream.toXML(epoch);
        assertEquals(XML_EPOCH.replaceAll(" ", ""), xml.replaceAll(" ", ""));
	}
	
	@Test
	public void testFromXml()
	{
		XStream xstream = new XStream();
        xstream.processAnnotations(SHLevelInfo.class);
        xstream.processAnnotations(SHEpochInfo.class);
        
        SHEpochInfo epoch = (SHEpochInfo) xstream.fromXML(XML_EPOCH);
        
        assertEquals(NAME, epoch.getName());
        assertEquals(2, epoch.getLevels().size());
	}
	
	@Test
	public void testUpdateYears()
	{
		SHEpochInfo epoch = new SHEpochInfo();
		
		SHLevelInfo level = new SHLevelInfo();
		level.setYear(1);
		epoch.getLevels().add(level);
		
		level = new SHLevelInfo();
		level.setYear(123);
		epoch.getLevels().add(level);
		
		level = new SHLevelInfo();
		level.setYear(12);
		epoch.getLevels().add(level);
		
		epoch.updateYears();
		assertTrue(SHUtils.inRange(epoch.getMinYear(), 0.9999999f, 1.00000001f));
		assertTrue(SHUtils.inRange(epoch.getMaxYear(), 122.999999f, 123.0000001f));
		
	}

	@Test
	public void testSortLevels()
	{
		SHLevelInfo l1 = new SHLevelInfo();
		l1.setYear(2);		
		SHLevelInfo l2 = new SHLevelInfo();
		l2.setYear(1);		
		SHLevelInfo l3 = new SHLevelInfo();
		l3.setYear(1.1f);
		
		SHEpochInfo epoch = new SHEpochInfo();
		epoch.getLevels().add(l1);
		epoch.getLevels().add(l2);
		epoch.getLevels().add(l3);
		
		epoch.sortLevels();
		
		assertSame(l2, epoch.getLevels().get(0));
		assertSame(l3, epoch.getLevels().get(1));
		assertSame(l1, epoch.getLevels().get(2));
	}
}
