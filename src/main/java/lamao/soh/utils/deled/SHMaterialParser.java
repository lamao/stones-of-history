/* 
 * SHMaterialParser.java 25.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.deled;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

import com.jme3.asset.AssetManager;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;
import lamao.soh.utils.xmlparser.ISHXmlParser;
import lamao.soh.utils.xmlparser.SHDocXMLParser;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

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

    // TODO: Initialize it
    private AssetManager assetManager;
	
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
			Element colorNode = (Element)node;
            ColorRGBA materialColor = new ColorRGBA(
                Integer.parseInt(colorNode.getAttribute("r")),
                Integer.parseInt(colorNode.getAttribute("g")),
                Integer.parseInt(colorNode.getAttribute("b")),
                Integer.parseInt(colorNode.getAttribute("a")));
            materialColor.multLocal(1.0f / 255);
			_lastMaterial.m.setColor(SHMaterialGroup.MATERIAL_COLOR_DIFFUSE, materialColor);
			_lastMaterial.m.setFloat(SHMaterialGroup.MATERIAL_ATTR_SHININESS, 96);
			if (((Element)node.getParentNode()).getAttribute("blend").equals("alphablend"))
			{
				_lastMaterial.m.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
			}
		}
	}
	
	/** Parses texture material */
	public class TextureParser implements ISHXmlParser
	{
		@Override
		public void parse(Node node)
		{
            Element texElement = (Element)node;
            String texturePath = _textureLocation + texElement.getAttribute("file").replace("\\", File.separator);
            Texture tx = assetManager.loadTexture(texturePath);
            // TODO: Implement different types of wrap mode
            tx.setWrap(Texture.WrapMode.Repeat);

            _lastMaterial.t = tx;
		}
	}
}
