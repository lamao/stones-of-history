package lamao.soh.core;

import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;

import java.util.List;

/**
 * Utility class for adding application states to stateManager in Spring configuration XML
 * Created by Vycheslav Mischeryakov on 13.04.16.
 */
public class AppStatesInjector {
    public AppStatesInjector(AppStateManager appStateManager, List<AppState> states) {
        appStateManager.attachAll(states);
    }
}
