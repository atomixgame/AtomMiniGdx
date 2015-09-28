/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.managers;

import sg.atom.core.lifecycle.AbstractManager;
import com.jme3.audio.AudioNode;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.ViewPort;
import sg.atom.core.AtomMain;

/**
 *
 * @author cuong.nguyen
 */
public class StageManager extends AbstractManager {

    public StageManager(AtomMain app) {
        super(app);
    }

    @Override
    public void init() {
        setupBackground();
        setupCamera();
//        setupSounds();
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
        app.getInputManager().setCursorVisible(false);
        //        flyCam.setEnabled(false);
    }
}
