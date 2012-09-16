/* 
 * SHCollisionProcessor.java 16.09.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.core;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lamao.soh.utils.events.SHEvent;
import lamao.soh.utils.events.SHEventDispatcher;

import com.jme.intersection.BoundingCollisionResults;
import com.jme.intersection.CollisionData;
import com.jme.intersection.CollisionResults;
import com.jme.intersection.TriangleCollisionResults;
import com.jme.scene.Node;
import com.jme.scene.Spatial;

/**
 * @author lamao
 *
 */
public class SHCollisionProcessor implements ISHCollisionProcessor
{
	/**
	 * Pairs of entity types to check for collision.
	 */
	private List<SHCollisionTask> collisionTasks = 
			new LinkedList<SHCollisionTask>();
	
	private SHEventDispatcher dispatcher;
	
	/* (non-Javadoc)
	 * @see lamao.soh.core.ISHCollisionProcessor#getCollisionTasks()
	 */
	@Override
	public List<SHCollisionTask> getCollisionTasks()
	{
		return collisionTasks;
	}
	
	/* (non-Javadoc)
	 * @see lamao.soh.core.ISHCollisionProcessor#addCollisionTask(lamao.soh.core.SHCollisionTask)
	 */
	@Override
	public void addCollisionTask(SHCollisionTask task)
	{
		collisionTasks.add(task);
	}
	
	/* (non-Javadoc)
	 * @see lamao.soh.core.ISHCollisionProcessor#removeCollisionTask(lamao.soh.core.SHCollisionTask)
	 */
	@Override
	public void removeCollisionTask(SHCollisionTask task)
	{
		collisionTasks.remove(task);
	}
	
	public SHEventDispatcher getDispatcher()
	{
		return dispatcher;
	}

	public void setDispatcher(SHEventDispatcher dispatcher)
	{
		this.dispatcher = dispatcher;
	}

	/**
	 * Checks if this model is entity and return it if so
	 * @param model - model to check
	 * @return entity or null if model is not an entity
	 */
	private SHEntity getEntity(Spatial model)
	{
		if (model instanceof SHEntity) 
		{
			return (SHEntity)model;
		} 
		else 
		{
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see lamao.soh.core.ISHCollisionProcessor#processCollisions(com.jme.scene.Node)
	 */
	@Override
	public void processCollisions(Node rootNode)
	{
		List<SHEvent> eventsToSend = new LinkedList<SHEvent>();
		Node source = null;
		Node dest = null;
		for (SHCollisionTask task : collisionTasks)
		{
			source = (Node)rootNode.getChild(task.sourceType);
			dest = (Node)rootNode.getChild(task.destType);
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
			dispatcher.addEvent(event);
		}
	}

}
