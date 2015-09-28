/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.sound;

/**
 *
 * @author CuongNguyen
 */
public class AudioChannel {

    int id;
    String name;
    int status;
    float volume;
    float fade;
    int type;
    int musicType;
    int playingType;
    int maxSoundAtOnce;
    float fadeTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getFade() {
        return fade;
    }

    public void setFade(float fade) {
        this.fade = fade;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMusicType() {
        return musicType;
    }

    public void setMusicType(int musicType) {
        this.musicType = musicType;
    }

    public int getPlayingType() {
        return playingType;
    }

    public void setPlayingType(int playingType) {
        this.playingType = playingType;
    }
}
