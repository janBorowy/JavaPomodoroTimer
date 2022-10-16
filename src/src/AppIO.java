package src;

import sun.audio.AudioStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.sound.sampled.*;
public class AppIO {
    // loads images and config
    public AppIO(){}

    public static HashMap<String, ImageIcon> loadImages(){
        HashMap<String, ImageIcon> imgs = new HashMap<>();

        imgs.put("pause_button", loadIcon("assets/pause_button.png"));
        imgs.put("play_button", loadIcon("assets/play_button.png"));

        return imgs;
    }

    public static Config loadConfig(String path){
        try {
            Config con = new Config(path);
            return con;
        } catch (IOException e) {
            // handle no config file;
            // should close app
        }

        return null;
    }

    private static ImageIcon loadIcon(String path) {
        ImageIcon img = null;
        try {
            BufferedImage bImg = ImageIO.read(new File(path));
            img = new ImageIcon(bImg);
        } catch(IOException e){
            // handling here
        }
        return img;
    }

    public static Clip loadClip(String path){
        // open sound file
        File soundFile = new File(path);
        AudioInputStream audioStream;
        AudioFormat format;
        DataLine.Info info;
        Clip clip;

        // parse file
        try {
            audioStream = AudioSystem.getAudioInputStream(soundFile);
            format = audioStream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioStream);
            return clip;
        } catch(IOException e){

        } catch(UnsupportedAudioFileException e){

        } catch(LineUnavailableException e){

        }

        return null;
    }

}
