/* 
 * SHTextureParser.java 1.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.resparser;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.logging.Logger;

import lamao.soh.utils.SHModelLoader;
import lamao.soh.utils.SHResourceManager;

import com.jme3.image.Texture;
import com.jme3.image.Texture.MagnificationFilter;
import com.jme3.image.Texture.MinificationFilter;
import com.jme3.scene.state.TextureState;
import com.jme3.system.DisplaySystem;
import com.jme3.util.TextureManager;

/**
 * Parser for texture configuration. Creats <code>TextureState</code> <br>
 * Supported parameters:<br>
 * <li> type = texture</li>
 * <li> label = string label</li>
 * <li> path = path to model file</li>
 * <li> filtering (optional) = bilinear or trilinear. </li>
 * to resource manager.
 * @author lamao
 *
 */
public class SHTextureParser implements ISHResourceParser
{
	private static Logger _logger = Logger.getLogger(SHModelLoader.class.getName());
	
	public final static String KEY_FILTERING = "filtering"; 
	
	/* (non-Javadoc)
	 * @see lamao.soh.utils.parsers.ISHResourceParser#parse(java.util.Map)
	 */
	@Override
	public void parse(Map<String, String> args, SHResourceManager manager)
	{
		File file = new File(args.get(PATH_KEY));
		if (!file.exists())
		{
			_logger.warning("Can't find texture file " + file);
			return;
		}
		
		Texture tx = null;
		try
		{
			tx = TextureManager.loadTexture(file.toURI().toURL());
		}
		catch (MalformedURLException e)
		{
			_logger.warning("Can't load texture from file " + file);
			return;
		}
		tx.setWrap(Texture.WrapMode.Clamp);
		
		String filtering = args.get(KEY_FILTERING);
		if (filtering != null)
		{
			if (filtering.equals("bilinear"))
			{
				tx.setMinificationFilter(MinificationFilter.BilinearNearestMipMap);
				tx.setMagnificationFilter(MagnificationFilter.Bilinear);
			}
			else if (filtering.equals("trilinear"))
			{
				tx.setMinificationFilter(MinificationFilter.Trilinear);
				tx.setMagnificationFilter(MagnificationFilter.Bilinear);
			}
		}
		
		TextureState ts = DisplaySystem.getDisplaySystem().getRenderer()
				.createTextureState();
		String label = args.get(LABEL_KEY);
		if (ts != null && label != null)
		{
			manager.add(args.get(TYPE_KEY), label, ts);
		}
		else
		{
			_logger.warning("Can't load texture from file " + file);
		}
	}
}
