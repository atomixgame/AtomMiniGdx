/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.stage;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author cuongnguyen
 */
public class IntervalControl extends AbstractControl {

    float activeTime = 0;
    float passedTime = 0;
    float interval = 1;
    Runnable onFinish = null;
    boolean removeOnFinish = true;

    public IntervalControl() {
    }

    public IntervalControl(Runnable onFinish) {
        this.onFinish = onFinish;
    }
    @Override
    protected void controlUpdate(float tpf) {
        activeTime += tpf;
        if (isFinish(activeTime)) {
            onFinish();
        } else {
            if (passedTime > interval) {
                passedTime = 0;
                invoke(activeTime);
            } else {
                passedTime += tpf;
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public boolean isFinish(float activeTime) {
        return false;
    }

    public void onFinish() {
        if (removeOnFinish) {
            this.getSpatial().removeControl(this);
        }
        if (onFinish != null) {
            onFinish.run();
        }
    }

    public void invoke(float activeTime) {
    }
}
