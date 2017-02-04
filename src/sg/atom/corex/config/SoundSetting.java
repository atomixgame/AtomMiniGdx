/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.config;

import static sg.atom.corex.config.QualitySetting.NORMAL;

/**
 *
 * @author DaiPhongPC
 */
public class SoundSetting {
    // Sound
    public float soundVolume = 1;
    public float musicVolume = 1;
    public boolean muted = false;
    public QualitySetting soundQuality = NORMAL;

    public SoundSetting() {
    }
    
    public void resetDefault(){
        this.soundVolume = 1;
        this.musicVolume = 1;
        this.muted = false;
    }
    
}
