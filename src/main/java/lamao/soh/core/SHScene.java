/* 
 * SHScene.java 29.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import java.util.ArrayList;
import java.util.List;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Game scene. Contains models and entities related to current game/level.
 * @author lamao
 *
 */
public class SHScene
{
	/** Root node of the scene */
	private Node _rootNode = new Node("scene-root");
	
	private ISHCollisionProcessor collisionProcessor;
	
	private InputHandler inGameInputHandler;
	
	public SHScene(ISHCollisionProcessor collisionProcessor,
			InputHandler inGameInputHandler)
	{
		super();
		this.collisionProcessor = collisionProcessor;
		this.inGameInputHandler = inGameInputHandler;
	}

	public void setRootNode(Node rootNode) 
	{
		_rootNode = rootNode;
	}
	
	public Node getRootNode()
	{
		return _rootNode;
	}
	
	public ISHCollisionProcessor getCollisionProcessor()
	{
		return collisionProcessor;
	}

	public void setCollisionProcessor(ISHCollisionProcessor collisionProcessor)
	{
		this.collisionProcessor = collisionProcessor;
	}
	
	public InputHandler getInGameInputHandler()
	{
		return inGameInputHandler;
	}

	public void setInGameInputHandler(InputHandler inGameInputHandler)
	{
		this.inGameInputHandler = inGameInputHandler;
	}

	/**
	 * 
	 * @return number of model groups in the scene
	 */
	public int getNumberOfTypes() 
	{
		if (_rootNode.getChildren() == null)
		{
			return 0;
		}
		return _rootNode.getChildren().size();
	}
	
	/**
	 * 
	 * @param type - user type of model group (e.g. 'brick')
	 * @return list of models of given types
	 */
	public List<Spatial> get(String type)
	{
		Spatial typeNode = _rootNode.getChild(type);
		if (typeNode instanceof Node)
		{
			return ((Node) typeNode).getChildren();
		}
		return null;
	}
	
	/**
	 * Returns only those spatials of type <code>type</type> that are subclasses
	 * of SHEntity
	 * @param type - type name
	 * @return list of entities of that type. All Spatial instances are skipped 
	 */
	public List<SHEntity> getEntities(String type)
	{
		List<Spatial> spatials = get(type);
		List<SHEntity> entities = new ArrayList<SHEntity>();
		for (Spatial spatial : spatials) 
		{
			if (spatial instanceof SHEntity) 
			{
				entities.add((SHEntity)spatial);
			}
		}
		return entities;
	}
	
	public Spatial get(String type, String name) 
	{
		Spatial typeNode = _rootNode.getChild(type);
		if (typeNode instanceof Node)
		{
			return ((Node) typeNode).getChild(name);
		}
		return null;
	}
	
	public SHEntity getEntity(String type, String name)
	{
		Spatial possibleResult = get(type, name);
		if (possibleResult instanceof SHEntity)
		{
			return (SHEntity)possibleResult;
		}
		return null;		
	}
	
	@SuppressWarnings("unchecked")
	public<T extends SHEntity> T getEntity(String type, String name, Class<T> clazz)
	{
		return (T)getEntity(type, name);
	}
	
	/**
	 * Add model of specified type. If this type is not present, it will be
	 * created.
	 * @param type
	 * @param model
	 */
	public void add(String type, Spatial model)
	{
		Node nodeGroup = (Node)_rootNode.getChild(type);
		if (nodeGroup == null)
		{
			nodeGroup = new Node(type);
			_rootNode.attachChild(nodeGroup);
		}
		nodeGroup.attachChild(model);
		nodeGroup.updateRenderState();
	}
	
	public void add(SHEntity entity) 
	{
		add(entity.getType(), entity);
	}
	
	/**
	 * Remove model of specified type. If model is the only model of this type,
	 * type will be removed. 
	 * @param type
	 * @param model
	 */
	public void remove(String type, Spatial model)
	{
		Node nodeGroup = (Node)_rootNode.getChild(type);
		if (nodeGroup != null)
		{
			nodeGroup.detachChild(model);
			if (nodeGroup.getChildren().isEmpty())
			{
				_rootNode.detachChild(nodeGroup);
			}
		}
	}
	
	/**
	 * Remove model of specified type. If model is the only model of this type,
	 * type will be removed. 
	 * @param type
	 * @param model
	 */
	public void remove(SHEntity entity)
	{
		remove(entity.getType(), entity);
	}
	
	
	
	
	
	/**
	 * Updates the scene. It includes node updates, collision detection
	 * @param tpf
	 */
	public void update(float tpf)
	{
		inGameInputHandler.update(tpf);
		collisionProcessor.processCollisions(_rootNode);
	}
	
	
	
	/** 
	 * Clears scene, but left non-entity elements (e.g. collision tasks).
	 * Main purpose of this method is cleaning the scene loading other level
	 */
	public void reset()
	{
		_rootNode.detachAllChildren();
	}
	
	/**
	 * Clears all scene to as it was just after creating default state.
	 * Designed to be used in places, when we can't create new scene because we
	 * don't want again attach scene's root node to its parent.
	 */
	public void resetAll()
	{
		this.reset();
	}
	
}
