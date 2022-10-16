package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.util.HashMap;

public class PomodoroGUI extends JFrame {
    private JPanel mainPanel;
    private JButton playButton;
    private JButton stopButton;
    private JLabel timeLabel;
    private JLabel stateLabel;
    private final PomodoroTimer timer;
    private final NumberFormat format;
    private final HashMap<String, ImageIcon> loadedIcons;

    public PomodoroGUI(){
        // Setup frame
        super("Java Pomodoro Timer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setMinimumSize(new Dimension(300, 250));
        this.setLocationRelativeTo(null);
        this.add(mainPanel);
        setActionListeners();
        // load images
        loadedIcons = AppIO.loadImages();

        // Setup Timer
        timer = new PomodoroTimer(this);
        format = NumberFormat.getNumberInstance();
        format.setMinimumIntegerDigits(2);
        updateTimeDisplay();
        updateStateDisplay();

        // Stop button is hidden when paused
        stopButton.setVisible(false);
    }

    private void setActionListeners(){
        playButton.addActionListener((ActionEvent e) -> {
            playPauseButtonAction();
        });
        stopButton.addActionListener((ActionEvent e) ->{
            timer.stopTimer();
        });
    }

    public void updateTimeDisplay(){
        long cur_time = timer.getTimeMillis();
        long minutes = (cur_time/60_000);
        long seconds = Math.round((double)(cur_time - minutes * 60_000) / 1000);
        timeLabel.setText(minutes + " : " + format.format(seconds));
    }

    public void updateStateDisplay(){
        stateLabel.setText("Phase: " + timer.getStateStr());
    }

    public void playPauseButtonAction(){
        boolean paused = timer.paused();

        if(paused){
            // unpause
            timer.unpauseTimer();
            playButton.setIcon(loadedIcons.get("pause_button"));

            // when unpaused, stop button reappears
            stopButton.setVisible(true);
        } else {
            // pause
            timer.pauseTimer();
            playButton.setIcon(loadedIcons.get("play_button"));

            // when paused, stop is hidden
            stopButton.setVisible(false);
        }
    }
}
