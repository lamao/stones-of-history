package lamao.soh.states;

import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import lamao.soh.core.model.entity.SHEpoch;
import lamao.soh.core.model.entity.SHLevel;
import lamao.soh.core.service.SHLevelService;

import java.util.logging.Logger;

/**
 * Created by Vycheslav Mischeryakov on 13.04.16.
 */
public class LoadLevelState extends AbstractAppState {

    private static final Logger LOG = Logger.getLogger(LoadLevelState.class.getName());

    private SHLevelService levelService;

    private AppStateManager stateManager;

    private SHEpoch epoch;

    private SHLevel level;

    public LoadLevelState(SHLevelService levelService, AppStateManager stateManager) {
        this.levelService = levelService;
        this.stateManager = stateManager;
        setEnabled(false);
    }

    public SHEpoch getEpoch() {
        return epoch;
    }

    public void setEpoch(SHEpoch epoch) {
        this.epoch = epoch;
    }

    public SHLevel getLevel() {
        return level;
    }

    public void setLevel(SHLevel level) {
        this.level = level;
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (!isEnabled() && enabled) {
            LOG.info("Start loading level");
            levelService.loadLevelScene(epoch, level);
            LOG.info("Finish loading level");
            SHLevelState levelState = stateManager.getState(SHLevelState.class);
            levelState.setEnabled(true);
        } else {
            super.setEnabled(enabled);
        }
    }
}
