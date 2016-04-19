/*
 * SHLevelState.java 25.03.2010 Copyright 2010 Stones of History All rights reserved.
 */
package lamao.soh.states;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import lamao.soh.core.SHScene;
import lamao.soh.core.input.listeners.LaunchBallInputListener;
import lamao.soh.core.input.listeners.MovePaddleInputListener;
import lamao.soh.core.input.listeners.ToMenuInputListener;
import lamao.soh.core.model.SHEpochLevelItem;
import lamao.soh.core.model.entity.SHEpoch;
import lamao.soh.core.model.entity.SHLevel;
import lamao.soh.core.service.StateService;
import lamao.soh.ui.controllers.SHInGameScreenController;
import lamao.soh.utils.events.SHEventDispatcher;

import com.jme3.light.PointLight;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

import de.lessvoid.nifty.Nifty;

/**
 * Game state for actual game for one level (from starting to winning or loosing).
 * @author lamao
 */
public class LevelState extends BasicAppState {

    private static final String INPUT_ACTION_PADDLE_LEFT = "paddle-left";
    private static final String INPUT_ACTION_PADDLE_RIGHT = "paddle-right";
    private static final String INPUT_ACTION_BALL_LAUNCH = "ball-launch";
    private static final String INPUT_ACTION_SHOW_GAME_MENU = "show-game-menu";

    /** Level for playing */
    private SHScene scene = null;

    /** Dispatcher used to fire events */
    private SHEventDispatcher dispatcher;

    private String startNiftyScreen;

    private SHInGameScreenController inGameScreenController;

    private Node rootNode;

    private Camera camera;

    private InputManager inputManager;

    private MovePaddleInputListener paddleInputListener;

    private LaunchBallInputListener launchBallInputListener;

    private ToMenuInputListener toMenuInputListener;

    private Node localRootNode;

    private SHEpochLevelItem levelInfo;

    // TODO: Move to constructor scene
    public LevelState(
        StateService stateService,
        SHEventDispatcher dispatcher,
        String startNiftyScreen,
        SHInGameScreenController inGameScreenController) {
        super(stateService);

        this.dispatcher = dispatcher;
        this.startNiftyScreen = startNiftyScreen;
        this.inGameScreenController = inGameScreenController;

        paddleInputListener = new MovePaddleInputListener(this);
        launchBallInputListener = new LaunchBallInputListener(this);
        toMenuInputListener = new ToMenuInputListener(this, inGameScreenController);

    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        SimpleApplication simpleApplication = (SimpleApplication)app;
        rootNode = simpleApplication.getRootNode();
        camera = simpleApplication.getCamera();
        inputManager = simpleApplication.getInputManager();

        localRootNode = new Node("level-root-node");

        PointLight light = new PointLight();
        light.setPosition(new Vector3f(0, 3, 3));
        light.setColor(ColorRGBA.White.clone());
        localRootNode.addLight(light);

        camera.setLocation(new Vector3f(0, 13, 18));
        camera.lookAt(new Vector3f(0, 0, 0), Vector3f.UNIT_Y);

        setupInputMappings();

        localRootNode.attachChild(scene.getRootNode());
        rootNode.attachChild(localRootNode);
        // TODO: Uncomment for game
//            inputManager.setCursorVisible(false);

        getStateService().get(SHNiftyState.class).gotoScreen(startNiftyScreen);
        inGameScreenController.setLevelInfo(levelInfo);
    }

    @Override
    public void cleanup() {
        super.cleanup();

        clearInputMappings();

        rootNode.detachChild(localRootNode);

    }


    private void setupInputMappings() {
        inputManager.addMapping(INPUT_ACTION_PADDLE_LEFT,
            new MouseAxisTrigger(MouseInput.AXIS_X, true),
            new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping(INPUT_ACTION_PADDLE_RIGHT,
            new MouseAxisTrigger(MouseInput.AXIS_X, false),
            new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addMapping(INPUT_ACTION_BALL_LAUNCH,
            new MouseButtonTrigger(MouseInput.BUTTON_RIGHT),
            new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping(INPUT_ACTION_SHOW_GAME_MENU, new KeyTrigger(KeyInput.KEY_ESCAPE));

        inputManager.addListener(paddleInputListener, INPUT_ACTION_PADDLE_LEFT, INPUT_ACTION_PADDLE_RIGHT);
        inputManager.addListener(launchBallInputListener, INPUT_ACTION_BALL_LAUNCH);
        inputManager.addListener(toMenuInputListener, INPUT_ACTION_SHOW_GAME_MENU);
    }

    private void clearInputMappings() {
        inputManager.deleteMapping(INPUT_ACTION_PADDLE_LEFT);
        inputManager.deleteMapping(INPUT_ACTION_PADDLE_RIGHT);
        inputManager.deleteMapping(INPUT_ACTION_BALL_LAUNCH);
        inputManager.deleteMapping(INPUT_ACTION_SHOW_GAME_MENU);

        inputManager.removeListener(paddleInputListener);
        inputManager.removeListener(launchBallInputListener);
        inputManager.removeListener(toMenuInputListener);
    }

    public SHScene getScene() {
        return scene;
    }

    public void setScene(SHScene scene) {
        if (this.scene != null && this.scene != scene) {
            rootNode.detachChild(this.scene.getRootNode());
        }
        this.scene = scene;
    }

    public void setLevelInfo(SHEpochLevelItem levelInfo) {
        this.levelInfo = levelInfo;
    }

    @Override
    public void update(float tpf) {
        if (isEnabled()) {
            super.update(tpf);
            scene.update(tpf);
            dispatcher.update(tpf);
        }
    }

    @Override
    public void render(RenderManager renderManager) {
        super.render(renderManager);
    }

    /**
     * Used in Spring context
     * @return
     */
    public Node getRootNode() {
        return rootNode;
    }
}
