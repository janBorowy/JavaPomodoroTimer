package src;

import javax.sound.sampled.*;

public class SoundEffect implements LineListener {

    private final Clip clip;

    public SoundEffect(String path){
        clip = AppIO.loadClip(path);
        clip.addLineListener(this);
    }

    public void play(){
        clip.setFramePosition(0);
        if(!clip.isRunning()) {
            clip.start();
        } else {
            clip.stop();
            clip.start();
        }
    }

    @Override
    public void update(LineEvent event) {
        if(event.getType() == LineEvent.Type.START){
            // start playback
        }

        if(event.getType() == LineEvent.Type.STOP){
            // stop playback
        }
    }
}
