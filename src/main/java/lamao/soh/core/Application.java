package lamao.soh.core;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.asset.plugins.FileLocator;
import lamao.soh.states.SHNiftyState;

import java.util.List;

/**
 * Created by Vycheslav Mischeryakov on 30.03.16.
 */
public class Application extends SimpleApplication {

    private String pathToAssets;

    public Application(String pathToAssets) {
        this.pathToAssets = pathToAssets;
    }

    public void setAppStates(List<AppState> appStates) {
        getStateManager().attachAll(appStates);
    }

    @Override
    public void simpleInitApp() {
        assetManager.registerLocator(pathToAssets, FileLocator.class);

        SHNiftyState niftyState = getStateManager().getState(SHNiftyState.class);
        niftyState.setEnabled(true);
    }


}
