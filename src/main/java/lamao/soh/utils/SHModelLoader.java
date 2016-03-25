/* 
 * SHModelLoader.java 1.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import lamao.soh.utils.deled.SHDpsToJme;

import com.jme.scene.Spatial;
import com.jme.util.export.binary.BinaryImporter;
import com.jmex.model.converters.ObjToJme;

/**
 * Imports models from different file formats
 * @author lamao
 *
 */
public class SHModelLoader
{
	public static Spatial load(File file)
	{
		Spatial result = null;
		try 
		{
			result = load(file.toURI().toURL());
		}
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public static Spatial load(URL modelURL)
	{
		String extension = modelURL.getPath();
		extension = extension.substring(extension.lastIndexOf('.') + 1, extension.length());
		extension = extension.toUpperCase();
		
		Spatial result = null;
		if (extension.equals("JME"))
		{
			result = loadJME(modelURL);
		}
		else if (extension.equals("OBJ"))
		{
			result = loadObj(modelURL);
		}
		else if (extension.equals("DPS"))
		{
			result = loadDps(modelURL);
		}
		return result;
	}
	
	public static Spatial loadJME(File file)
	{
		Spatial result = null;
		try 
		{
			result = loadJME(file.toURI().toURL());
		}
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public static Spatial loadJME(URL modelRL) 
	{
		Spatial model = null;
        try 
        {
            BinaryImporter importer = new BinaryImporter();
            model = (Spatial)importer.load(modelRL.openStream());
        } 
        catch (IOException e)
        {
           System.err.println("ERROR: Failed to import jme model from URL: " + modelRL);
           e.printStackTrace();
        }
        return model;
	}
	
	public static Spatial loadObj(File file)
	{
		Spatial result = null;
		try 
		{
			result = loadObj(file.toURI().toURL());
		}
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public static Spatial loadObj(URL modelURL) 
	{
		Spatial model = null;
        ObjToJme converter=new ObjToJme();
        try 
        {
            converter.setProperty("mtllib",modelURL);
            converter.setProperty("texdir", modelURL);
            ByteArrayOutputStream BO=new ByteArrayOutputStream();   
            converter.convert(modelURL.openStream(),BO);
            model = (Spatial)BinaryImporter.getInstance().load(new ByteArrayInputStream(BO.toByteArray()));
        }
        catch (IOException e) 
        {
            System.err.println("Failed to load Obj file" + e);
        }
        return model;
    }
	
	public static Spatial loadDps(File file)
	{
		Spatial model = null;
		SHDpsToJme converter = new SHDpsToJme();
		converter.load(file);
		model = converter.getResult();
		return model;
	}
	
	public static Spatial loadDps(URL modelURL)
	{
		Spatial result = null;
		try
		{
			result = loadDps(new File(modelURL.toURI()));
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
}
