/* 
 * SHPrimitiveEntityParser.java 03.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.deled;

import java.util.Map;

import lamao.soh.core.ISHEntityFactory;
import lamao.soh.core.SHEntity;
import lamao.soh.core.SHScene;
import lamao.soh.core.SHUtils;
import lamao.soh.utils.xmlparser.ISHXmlParser;

import org.w3c.dom.Node;

import com.jme3.scene.Spatial;

/**
 * Treats given spatial as model for entity. Creates entity based on 
 * 'scene.primitives.primitive.tag' information, which should be set
 * in DeleD editor. If entity can't be created spatial is treated as
 * simple model and also is added to the scene.
 * @author lamao
 *
 */
class SHPrimitiveEntityParser extends SHPrimitiveParser
{
	/** Scene to add entities */
	private SHScene _scene = null;
	
	/** Parameters of current entity stored in 'primitive.tag' */
	private Map<String, String> _entityParams = null;
	
	private ISHEntityFactory _factory = null;
	
	public SHPrimitiveEntityParser(Map<String, SHMaterialGroup> materials,
			SHScene scene, ISHEntityFactory factory)
	{
		super(materials);
		addParser("primitive.tag", new SHTagParser());
		_scene = scene;
		_factory = factory;
	}
	
	@Override
	public void parse(Node node)
	{
		_entityParams = null;
		super.parse(node);
	}
	@Override
	public void spatialConstructed(Node node, Spatial spatial)
	{
		SHEntity entity = _factory.createEntity(_entityParams);
		if (entity == null)
		{
			_scene.add("decoration", spatial);
		}
		else
		{
			entity.setModel(spatial);
			entity.setName(spatial.getName());
			entity.setLocation(spatial.getLocalTranslation().clone());
			spatial.setLocalTranslation(0, 0, 0);
			_scene.add(entity);
		}
	}
	
	private class SHTagParser implements ISHXmlParser
	{
		@Override
		public void parse(Node node)
		{
			_entityParams = SHUtils.buildMap(node.getFirstChild().getNodeValue());
		}
		
	}

}
