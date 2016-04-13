/* 
 * SHSceneLoader.java 03.05.2010
 * 
 * Copyright 2010 Stones of History
 * All rights reserved. 
 */
package lamao.soh.utils.deled;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import lamao.soh.core.ISHEntityFactory;
import lamao.soh.core.SHEntity;
import lamao.soh.core.SHScene;
import lamao.soh.utils.xmlparser.SHDocXMLParser;

/**
 * @author lamao
 *
 */
public class SHSceneLoader
{
	/** Scene where load data from file */
	private SHScene scene;

    private AssetManager assetManager;
	
	private ISHEntityFactory entityFactory;

	public SHSceneLoader(SHScene scene, ISHEntityFactory entityFactory, AssetManager assetManager)
	{
        this.assetManager = assetManager;
		this.scene = scene;
		this.entityFactory = entityFactory;
	}
	
	/**
	 * Resets all internal variables to default
	 */
	protected void resetLoader()
	{
		scene.reset();
	}

	public SHScene getScene()
	{
		return scene;
	}

	public void setScene(SHScene scene)
	{
		this.scene = scene;
	}
	
	/**
     * @param pathToScene
     */
	public void load(String pathToScene)
	{
		scene.resetAll();
        Node sceneModel = (Node) assetManager.loadModel(pathToScene);
        processSceneModel(sceneModel);
        scene.getRootNode().attachChild(sceneModel);
	}

    private void processSceneModel(Node sceneModel) {
        for (Spatial group : sceneModel.getChildren()) {
            if (group instanceof Node) {
                Node groupAsNode = (Node)group;
                for (Spatial item : groupAsNode.getChildren()) {
                }
            }
        }
    }

}
