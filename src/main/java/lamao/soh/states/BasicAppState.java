package lamao.soh.states;

import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import lamao.soh.core.Application;

/**
 * Created by Vycheslav Mischeryakov on 19.04.16.
 */
public class BasicAppState extends AbstractAppState {

    private AppStateManager stateManager;

    private Application application;

    public AppStateManager getStateManager() {
        return stateManager;
    }

    public void setStateManager(AppStateManager stateManager) {
        this.stateManager = stateManager;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    @Override
    public void initialize(AppStateManager stateManager, com.jme3.app.Application app) {
        super.initialize(stateManager, app);
        this.stateManager = stateManager;
        this.application = (Application) app;
    }
}
