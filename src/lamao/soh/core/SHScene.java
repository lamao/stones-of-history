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
	
	private ISHCollisionProcessor collisionProcessor;
	
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
	
//	public static void main(String[] args)
//	{
//		SimpleGame game = new SimpleGame() 
//		{
//			SHScene scene; 
//			SHBall ball;
//			@Override
//			protected void simpleInitGame()
//			{
//				SHGamePack.dispatcher = new SHEventDispatcher();
//				SHGamePack.dispatcher.addHandler("all", new SHEventLogger());
//				
//				scene = new SHScene();
//				SHEntity brick1 = SHEntityCreator.createDefaultBrick("brick1");
//				scene.addEntity(brick1);
//				
//				SHEntity brick2 = SHEntityCreator.createDefaultBrick("brick2");
//				brick2.setLocation(10, 10, 10);
//				brick2.getRoot().updateWorldData(0);
//				scene.addEntity(brick2);
//				
//				ball = SHEntityCreator.createDefaultBall();
//				ball.setLocation(0, -2f, 0);
//				ball.getRoot().updateWorldData(0);
//				scene.addEntity(ball);
//				
//				SHCollisionTask task = new SHCollisionTask("ball", "brick", false);
//				scene.addCollisionTask(task);
//				
//				rootNode.attachChild(scene.getRootNode());
//				rootNode.updateRenderState();
//			}
//			
//			@Override
//			protected void simpleUpdate()
//			{
//				scene.update(0);
//				
//				if (KeyInput.get().isKeyDown(KeyInput.KEY_0))
//				{
//					ball.getLocation().y += 0.01f;
//				}
//				else if (KeyInput.get().isKeyDown(KeyInput.KEY_9))
//				{
//					ball.getLocation().y -= 0.01f;
//				}
//				
//			}
//		};
//		game.setConfigShowMode(ConfigShowMode.AlwaysShow);
//		game.start();
//	}
	

}
