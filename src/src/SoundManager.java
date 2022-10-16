package src;

import java.util.HashMap;

public class SoundManager {
    HashMap<String, SoundEffect> soundEffectHashMap;

    public SoundManager(){
        soundEffectHashMap = new HashMap<>();

        SoundEffect break_sound = new SoundEffect("assets/sounds/break_sound.wav");
        soundEffectHashMap.put("break_sound", break_sound);
        SoundEffect long_break_sound = new SoundEffect("assets/sounds/long_break_sound.wav");
        soundEffectHashMap.put("long_break_sound", long_break_sound);
        SoundEffect work_bell_sound = new SoundEffect("assets/sounds/work_bell_sound.wav");
        soundEffectHashMap.put("work_bell_sound", work_bell_sound);
    }
    public void playSound(String key){
        getSound(key).play();
    }
    private SoundEffect getSound(String key){
        return soundEffectHashMap.get(key);
    }
}
