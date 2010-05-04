/* 
 * SHScene.java 29.04.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;
import lamao.soh.utils.events.SHEventLogger;

import com.jme.app.SimpleGame;
import com.jme.app.AbstractGame.ConfigShowMode;
import com.jme.bounding.BoundingBox;
import com.jme.input.KeyInput;
import com.jme.intersection.BoundingCollisionResults;
import com.jme.intersection.CollisionData;
import com.jme.intersection.CollisionResults;
import com.jme.intersection.TriangleCollisionResults;
import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.shape.Box;

/**
 * Game scene. Contains models and entities related to current game/level.
 * @author lamao
 *
 */
public class SHScene
{
	/** Root node of the scene */
	private Node _rootNode = new Node("scene-root");
	
	/** Entities (objects) of the scene. Grouped by the 
	 * <code>SHEntity#getType()</code>
	 */
	private Map<String, List<SHEntity>> _entities = 
			new TreeMap<String, List<SHEntity>>();

	/** 3D models for the scene. Can be entity models or free models (such as
	 * decorations). Grouped by the type (entity type if it's entity model or
	 * by user specified type if it's free model) 
	 */
	private Map<String, List<Spatial>> _models = 
			new TreeMap<String, List<Spatial>>();
	
	/**
	 * Pairs of entity types to check for collision.
	 */
	private List<SHCollisionTask> _collisionTasks = 
			new LinkedList<SHCollisionTask>();
	
	/** Map for fast searching entities for models */
	private Map<Spatial, SHEntity> _searchMap = new HashMap<Spatial, SHEntity>();
	
	
	public Node getRootNode()
	{
		return _rootNode;
	}
	
	public Map<String, List<SHEntity>> getEntities()
	{
		return _entities;
	}
	
	public List<SHEntity> getEntities(String type)
	{
		return _entities.get(type);
	}
	
	public SHEntity getEntity(String type, String name)
	{
		List<SHEntity> entities = getEntities(type);
		if (entities != null)
		{
			for (SHEntity entity : entities)
			{
				if (entity.getName().equals(name))
				{
					return entity;
				}
			}
		}
		return null;
	}

	public Map<String, List<Spatial>> getModels()
	{
		return _models;
	}
	
	/**
	 * Adds entity to the scene. Entities are grouped by their type in the same 
	 * way to models. Also method adds entity model to the scene models. The type
	 * of  model will be <code>entity.getType()</code>  
	 * @param type
	 * @param model
	 */
	public void addEntity(SHEntity entity)
	{
		List<SHEntity> entityGroup = getEntities(entity.getType());
		if (entityGroup == null)
		{
			entityGroup = new LinkedList<SHEntity>();
			_entities.put(entity.getType(), entityGroup);
		}
		entityGroup.add(entity);
		addModel(entity.getType(), entity.getRoot());
		_searchMap.put(entity.getRoot(), entity);
	}
	
	/**
	 * Remove entity and its model from the scene. If entity is the only entity 
	 * of this type, type will be removed. 
	 * @param type
	 * @param model
	 */
	public void removeEntity(SHEntity entity)
	{
		List<SHEntity> group = getEntities(entity.getType());
		if (group != null)
		{
			group.remove(entity);
			if (group.isEmpty())
			{
				_entities.remove(entity.getType());
			}
			removeModel(entity.getType(), entity.getRoot());
			_searchMap.remove(entity.getRoot());
		}
	}
	
	
	
	public List<Spatial> getModels(String type)
	{
		return _models.get(type);
	}
	
	/**
	 * Add model of specified type. If this type is not present, it will be
	 * created.
	 * @param type
	 * @param model
	 */
	public void addModel(String type, Spatial model)
	{
		List<Spatial> group = getModels(type);
		Node nodeGroup = (Node)_rootNode.getChild(type);
		if (group == null)
		{
			group = new LinkedList<Spatial>();
			_models.put(type, group);
			nodeGroup = new Node(type);
			_rootNode.attachChild(nodeGroup);
		}
		group.add(model);
		nodeGroup.attachChild(model);
	}
	
	/**
	 * Remove model of specified type. If model is the only model of this type,
	 * type will be removed. 
	 * @param type
	 * @param model
	 */
	public void removeModel(String type, Spatial model)
	{
		List<Spatial> group = getModels(type);
		Node nodeGroup = (Node)_rootNode.getChild(type);
		if (group != null)
		{
			group.remove(model);
			nodeGroup.detachChild(model);
			if (group.isEmpty())
			{
				_models.remove(type);
				_rootNode.detachChild(nodeGroup);
			}
		}
	}
	
	public List<SHCollisionTask> getCollisionTasks()
	{
		return _collisionTasks;
	}
	
	public void addCollisionTask(SHCollisionTask task)
	{
		_collisionTasks.add(task);
	}
	
	public void removeCollisionTask(SHCollisionTask task)
	{
		_collisionTasks.remove(task);
	}
	
	public SHEntity getEntity(Spatial model)
	{
		return _searchMap.get(model);
	}
	
	/**
	 * Updates the scene. It includes node updates, collision detection
	 * @param tpf
	 */
	public void update(float tpf)
	{
		processCollisions();
	}
	
	private void processCollisions()
	{
		List<SHEvent> eventsToSend = new LinkedList<SHEvent>();
		Node source = null;
		Node dest = null;
		for (SHCollisionTask task : _collisionTasks)
		{
			source = (Node)_rootNode.getChild(task.sourceType);
			dest = (Node)_rootNode.getChild(task.destType);
			if (source != null && dest != null)
			{
				for (Spatial sourceModel : source.getChildren())
				{
					for (Spatial destModel : dest.getChildren())
					{
						CollisionResults results = task.checkTris ? 
								new TriangleCollisionResults() :
								new BoundingCollisionResults();
						sourceModel.findCollisions(destModel, results);
						
						SHEntity sourceEntity = getEntity(sourceModel);
						SHEntity destEntity = getEntity(destModel);
						CollisionData data = null;
						for (int i = 0; i < results.getNumber(); i++)
						{
							data = results.getCollisionData(i);
							if (!task.checkTris || (data.getSourceTris().size() > 0 && 
								data.getTargetTris().size() > 0))
							{
								String event = "scene-collision-" 
										+ task.sourceType 
										+ "-" + task.destType;
								Map<String, Object> params = new HashMap<String, Object>();
								params.put("data", results.getCollisionData(i));
								params.put("src", sourceEntity);
								params.put("dst", destEntity);
								eventsToSend.add(new SHEvent(event, this, params));
							}
						}
					}
				}
			}
		}
		
		for (SHEvent event : eventsToSend)
		{
			SHGamePack.dispatcher.addEvent(event);
		}
	}
	
	public void reset()
	{
		_collisionTasks.clear();
		_entities.clear();
		_rootNode.detachAllChildren();
		_models.clear();
		_searchMap.clear();
	}
	
	public static void main(String[] args)
	{
		SimpleGame game = new SimpleGame() 
		{
			SHScene scene; 
			SHBall ball;
			@Override
			protected void simpleInitGame()
			{
				SHGamePack.dispatcher = new SHEventDispatcher();
				SHGamePack.dispatcher.addHandler("all", new SHEventLogger());
				
				scene = new SHScene();
				SHEntity brick1 = SHEntityCreator.createDefaultBrick("brick1");
				scene.addEntity(brick1);
				
				SHEntity brick2 = SHEntityCreator.createDefaultBrick("brick2");
				brick2.setLocation(10, 10, 10);
				brick2.getRoot().updateWorldData(0);
				scene.addEntity(brick2);
				
				ball = SHEntityCreator.createDefaultBall();
				ball.setLocation(0, -2f, 0);
				ball.getRoot().updateWorldData(0);
				scene.addEntity(ball);
				
				SHCollisionTask task = new SHCollisionTask("ball", "brick", false);
				scene.addCollisionTask(task);
				
				rootNode.attachChild(scene.getRootNode());
				rootNode.updateRenderState();
			}
			
			@Override
			protected void simpleUpdate()
			{
				scene.update(0);
				
				if (KeyInput.get().isKeyDown(KeyInput.KEY_0))
				{
					ball.getLocation().y += 0.01f;
				}
				else if (KeyInput.get().isKeyDown(KeyInput.KEY_9))
				{
					ball.getLocation().y -= 0.01f;
				}
				
			}
		};
		game.setConfigShowMode(ConfigShowMode.AlwaysShow);
		game.start();
	}
	

}
