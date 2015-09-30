package sg.atom.corex.managers;

import sg.atom.core.lifecycle.AbstractManager;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.ViewPort;
import sg.atom.core.AtomMain;

/**
 *
 * @author cuong.nguyen
 */
public class StageManager extends AbstractManager {

    float totalTime = 0;

    public StageManager(AtomMain app) {
        super(app);
    }

    @Override
    public void init() {
        setupBackground();
        setupCamera();
//        setupSounds();
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);

        totalTime += tpf;
    }

    public void setupBackground() {
        ViewPort viewPort = app.getViewPort();
        viewPort.setBackgroundColor(ColorRGBA.Gray);
    }

    public void setupSounds() {
    }

    public void setupCamera() {
    }

    public void onStageReady() {
    }

    public float getTime() {
        return totalTime;
    }
}
