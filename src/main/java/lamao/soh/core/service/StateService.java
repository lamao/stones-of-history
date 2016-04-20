package lamao.soh.core.service;

import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Utility service for managing application states
 *
 * Created by Vycheslav Mischeryakov on 19.04.16.
 */
public class StateService {

    private static final Logger LOG = Logger.getLogger(StateService.class.getName());

    private AppStateManager stateManager;

    private List<AppState> states = new ArrayList<AppState>();

    public StateService(AppStateManager stateManager) {
        this.stateManager = stateManager;
    }

    public void register(AppState state) {
        if (get(state.getClass()) == null) {
            states.add(state);
        } else {
            throw new IllegalArgumentException(String.format("%s state has been already registered", state));
        }
    }

    public void attach(Class<? extends AppState> stateClass) {
        AppState state = get(stateClass);
        if (state != null) {
            stateManager.attach(state);
            state.setEnabled(true);
        } else {
            LOG.warning(String.format("State of class %s was not found", stateClass.getName()));
        }
    }

    public void detach(Class<? extends AppState> stateClass) {
        AppState state = stateManager.getState(stateClass);
        if (state != null) {
            stateManager.detach(state);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends AppState> T get(Class<T> stateClass) {
        for (AppState state : states) {
            if (state.getClass().equals(stateClass)) {
                return (T) state;
            }
        }
        return null;
    }
}
