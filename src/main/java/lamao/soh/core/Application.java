package lamao.soh.core;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.AppState;
import com.jme3.asset.plugins.FileLocator;
import lamao.soh.SHConstants;
import lamao.soh.core.service.StateService;
import lamao.soh.states.SHNiftyState;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.util.List;

/**
 * Created by Vycheslav Mischeryakov on 30.03.16.
 */
public class Application extends SimpleApplication {

    private List<String> assetsLocations;

    private FileSystemXmlApplicationContext applicationContext;

    public Application(List<String> assetsLocations) {
        this.assetsLocations = assetsLocations;
        setShowSettings(false);
        setDisplayFps(false);
    }

    @Override
    public void simpleInitApp() {
        registerPathToAssets();

        detachDefaultStates();
        clearDefaultInputMapping();
        initializeBeans();
        showMainMenu();
    }

    private void detachDefaultStates() {
        getStateManager().detach(getStateManager().getState(StatsAppState.class));
        getStateManager().detach(getStateManager().getState(FlyCamAppState.class));
    }

    private void clearDefaultInputMapping() {
        getInputManager().clearMappings();
    }

    private void registerPathToAssets() {
        for (String pathToAssets : assetsLocations) {
            assetManager.registerLocator(pathToAssets, FileLocator.class);
        }
    }

    private void initializeBeans() {
        GenericApplicationContext genericApplicationContext = new GenericApplicationContext();
        genericApplicationContext.getBeanFactory().registerSingleton(SHConstants.SPRING_APPLICATION_BEAN_NAME, this);
        genericApplicationContext.refresh();

        applicationContext = new FileSystemXmlApplicationContext(
            new String[] {SHConstants.SPRING_CONFIG_LOCATION}, genericApplicationContext);
    }

    private void showMainMenu() {
        StateService stateService = applicationContext.getBean(StateService.class);
        stateService.attach(SHNiftyState.class);
        stateService.get(SHNiftyState.class).gotoStartScreen();
    }


}
