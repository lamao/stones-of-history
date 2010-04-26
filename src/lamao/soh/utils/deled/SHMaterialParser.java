/* 
 * SHMaterialParser.java 25.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.deled;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

import lamao.soh.utils.xmlparser.ISHXmlParser;
import lamao.soh.utils.xmlparser.SHDocXMLParser;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.jme.image.Texture;
import com.jme.image.Texture.MagnificationFilter;
import com.jme.image.Texture.MinificationFilter;
import com.jme.renderer.ColorRGBA;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;

/**
 * Parser for 'scene.materials.category.material' subtree of DPS scene file.
 * @author lamao
 *
 */
class SHMaterialParser extends SHDocXMLParser
{
	private static Logger logger = Logger.getLogger(
			SHMaterialParser.class.getName());
	
	/** Last loaded material state */
	private SHMaterialGroup _lastMaterial = null;
	
	/** Storage for loaded materials. Injected from outside (client) */
	private Map<String, SHMaterialGroup> _materials = null;
	
	/** URL to the texture folder */
	private URL _textureLocation = null;
	
	public SHMaterialParser(Map<String, SHMaterialGroup> materials,
			URL textureLocation)
	{
		_materials = materials;
		_textureLocation = textureLocation;
		addParser("material.layer.color", new ColorParser());
		addParser("material.layer.texture", new TextureParser());
	}
	
	@Override
	public void parse(Node node)
	{
		_lastMaterial = new SHMaterialGroup();
		
		String id = ((Element)node).getAttribute("id");
		_materials.put(id, _lastMaterial);
		
		super.parse(node);
	}
	
	/** Parsers color of material */
	public class ColorParser implements ISHXmlParser
	{
		@Override
		public void parse(Node node)
		{
			Element color = (Element)node;
			_lastMaterial.m.setDiffuse(new ColorRGBA(
					Integer.parseInt(color.getAttribute("r")),
					Integer.parseInt(color.getAttribute("g")),
					Integer.parseInt(color.getAttribute("b")),
					Integer.parseInt(color.getAttribute("a"))));
			_lastMaterial.m.getDiffuse().multLocal(1.0f / 255);
			_lastMaterial.m.setShininess(96);
//			_lastMaterial.m.setAmbient(new ColorRGBA(.2f, .2f, .2f, 1));
//			_lastMaterial.m.setSpecular(ColorRGBA.white.clone());
//			_lastMaterial.m.setEnabled(true);
//			_lastMS.setSpecular(new ColorRGBA(0.5f, 0.5f, 0.5f, 1));
//			_lastMS.setAmbient(ColorRGBA.black.clone());
			if (((Element)node.getParentNode()).getAttribute("blend").equals("alphablend"))
			{
				_lastMaterial.createBlendState();
			}
		}
	}
	
	/** Parses texture material */
	public class TextureParser implements ISHXmlParser
	{
		@Override
		public void parse(Node node)
		{
			try
			{
				Element texElement = (Element)node;
				URL texURL = new URL(_textureLocation 
						+ texElement.getAttribute("file").replace("\\", "/"));
				
				Texture tx = TextureManager.loadTexture(
						texURL,
						MinificationFilter.BilinearNearestMipMap,
						MagnificationFilter.Bilinear);
				// TODO: Implement different types of wrap mode
				tx.setWrap(Texture.WrapMode.Repeat);
				
				_lastMaterial.ts = DisplaySystem.getDisplaySystem()
						.getRenderer().createTextureState();
				_lastMaterial.ts.setTexture(tx);
				
			}
			catch (MalformedURLException e)
			{
				logger.warning("Can't load textuer");
				e.printStackTrace();
			}
		}
	}
}
