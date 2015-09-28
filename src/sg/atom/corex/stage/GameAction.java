/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.stage;

import com.jme3.animation.LoopMode;
import com.jme3.app.Application;
import com.jme3.cinematic.Cinematic;
import com.jme3.cinematic.PlayState;
import com.jme3.cinematic.events.CinematicEvent;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class GameAction implements Callable, Runnable, CinematicEvent {

    int id;
    int status;
    boolean actived;
    boolean isTransparent;
    int type;
    GameActor actor;
    ActionTrigger trigger;
    float duration;
    float time;
    boolean isStopable;
    boolean isReplayable;
    private LoopMode loopMode;
    private PlayState playState;
    private float speed;

    public GameAction(int id, GameActor actor, ActionTrigger trigger) {
        this.id = id;
        this.trigger = trigger;
        this.actor = actor;
    }

    public void run() {
    }

    public Object call() throws Exception {
        return trigger.getData();
    }

    public void play() {
    }

    public void stop() {
    }

    public void forceStop() {
    }

    public void pause() {
    }

    public float getDuration() {
        return duration;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return this.speed;
    }

    public PlayState getPlayState() {
        return this.playState;
    }

    public void setLoopMode(LoopMode loop) {
        this.loopMode = loop;
    }

    public LoopMode getLoopMode() {
        return this.loopMode;
    }

    public float getInitialDuration() {
        return duration;
    }

    public void setInitialDuration(float initialDuration) {
        this.duration = initialDuration;
    }

    public void internalUpdate(float tpf) {
    }

    public void initEvent(Application app, Cinematic cinematic) {
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getTime() {
        return time;
    }

    public void dispose() {
    }

    public void write(JmeExporter ex) throws IOException {
    }

    public void read(JmeImporter im) throws IOException {
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public int getType() {
        return type;
    }

    public GameActor getActor() {
        return actor;
    }
}
