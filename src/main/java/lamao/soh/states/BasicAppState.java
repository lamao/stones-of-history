package lamao.soh.states;

import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import lamao.soh.core.Application;
import lamao.soh.core.service.StateService;

/**
 * Created by Vycheslav Mischeryakov on 19.04.16.
 */
public class BasicAppState extends AbstractAppState {

    private AppStateManager stateManager;

    private Application application;

    private StateService stateService;

    public BasicAppState(StateService stateService) {
        this.stateService = stateService;
        stateService.register(this);
    }

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

    public StateService getStateService() {
        return stateService;
    }

    public void setStateService(StateService stateService) {
        this.stateService = stateService;
    }

    @Override
    public void initialize(AppStateManager stateManager, com.jme3.app.Application app) {
        super.initialize(stateManager, app);
        this.stateManager = stateManager;
        this.application = (Application) app;
    }
}
