package sg.atom.corex.managers;

import com.jme3.audio.AudioNode;
import sg.atom.core.lifecycle.AbstractManager;
import sg.atom.corex.sound.SoundClip;
import java.util.ArrayList;
import org.apache.commons.configuration.Configuration;
import sg.atom.core.AtomMain;
import sg.atom.corex.sound.AudioChannel;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class SoundManager extends AbstractManager {

    protected ArrayList<SoundClip> fxSounds;
    protected ArrayList<SoundClip> musicSounds;
    protected ArrayList<AudioChannel> channels;

    public SoundManager(AtomMain app) {
        super(app);
    }

    public void playSound(String soundName) {
    }

    public void playSound(SoundClip clip) {
    }

    public void playMusic(String name) {
        AudioNode music = new AudioNode(assetManager, name);
        music.setPositional(false);
        music.setLooping(true);
        music.setVolume(1f);
        music.play();
    }
    //Cycle -------------------------------------------------------------------

    public void init() {
    }

    public void config(Configuration configuration) {
    }

    public void load() {
    }

    public void update(float tpf) {
    }

    public void finish() {
    }

    //GETTER & SETTER
    public ArrayList<AudioChannel> getChannels() {
        return channels;
    }

    public void addChannel(AudioChannel channel) {
        this.channels.add(channel);

        refreshChannels();
    }

    public AudioChannel getChannel(String name) {
        for (AudioChannel c : channels) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    public void removeChannel(AudioChannel channel) {
        this.channels.remove(channel);
        refreshChannels();
    }

    private void refreshChannels() {
    }
}
