package lamao.soh.states;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import lamao.soh.core.model.entity.SHEpoch;
import lamao.soh.core.model.entity.SHLevel;
import lamao.soh.core.service.SHLevelService;
import lamao.soh.core.service.StateService;

import java.util.logging.Logger;

/**
 * Created by Vycheslav Mischeryakov on 13.04.16.
 */
public class LoadLevelState extends BasicAppState {

    private static final Logger LOG = Logger.getLogger(LoadLevelState.class.getName());

    private SHLevelService levelService;

    private SHEpoch epoch;

    private SHLevel level;

    public LoadLevelState(SHLevelService levelService, StateService stateService) {
        super(stateService);
        this.levelService = levelService;
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
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        LOG.info("Start loading level");
        levelService.loadLevelScene(epoch, level);
        LOG.info("Finish loading level");

        getStateService().detach(LoadLevelState.class);
        getStateService().attach(SHNiftyState.class);
        getStateService().attach(LevelState.class);
    }
}
