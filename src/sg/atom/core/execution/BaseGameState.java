package sg.atom.core.execution;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.scene.Node;
import java.util.logging.Logger;
import sg.atom.core.AtomMain;
import sg.atom.corex.managers.GUIManager;
import sg.atom.corex.managers.StageManager;

/**
 *
 * @author cuong.nguyen
 */
public class BaseGameState extends AbstractAppState {

    private static final Logger logger = Logger.getLogger(AbstractAppState.class.getName());
    protected AtomMain app;
    protected Node rootNode;
    protected Node guiNode;
    protected AssetManager assetManager;
    protected AppStateManager stateManager;
    protected InputManager inputManager;
    protected GUIManager guiManager;
    protected StageManager stageManager;
    protected boolean actived = false;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (AtomMain) app;
        this.rootNode = this.app.getRootNode();
        this.guiNode = this.app.getGuiNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.guiManager = this.app.getGUIManager();
        this.stageManager = this.app.getStageManager();
        this.inputManager = this.app.getInputManager();

        this.initialized = true;
    }

    public void goToState(Class<? extends AbstractAppState> newState) {
    }

    public void dispose() {
    }

    public AtomMain getApp() {
        return app;
    }

    public boolean isActived() {
        return actived;
    }
}
