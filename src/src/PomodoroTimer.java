package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PomodoroTimer implements ActionListener {

    private final Timer timer;
    long remaining;
    long lastUpdate;
    private final PomodoroGUI gui;
    private int workPhasesElapsed;
    private STATE_TYPE state;
    private boolean paused;
    private static Config config;
    private final int workPhases;
    private final SoundManager soundManager;

    public enum STATE_TYPE{
        WORK,
        SHORT_BREAK,
        LONG_BREAK;

        static long getDuration(STATE_TYPE s){
            switch (s){
                case WORK:
                    return config.getWorkDuration();
                case SHORT_BREAK:
                    return config.getShortDuration();
                case LONG_BREAK:
                    return config.getLongDuration();
            }
            return 0;
        }

        static String str(STATE_TYPE s){
            switch (s){
                case WORK:
                    return "work";
                case SHORT_BREAK:
                    return "short break";
                case LONG_BREAK:
                    return "long break";
            }
            return "null";
        }
    }

    public PomodoroTimer(PomodoroGUI gui){
        // load config first
        config = AppIO.loadConfig("config.pomodoro");

        // set initial values
        remaining = config.getWorkDuration();
        workPhases = config.getWorkPhases();
        this.gui = gui;
        workPhasesElapsed = 0;
        state = STATE_TYPE.WORK;
        timer = new Timer(1000, this);
        pauseTimer();

        // init soundManager
        soundManager = new SoundManager();
    }

    private void updateTimer(){
        // Calculate time elapsed and set new lastUpdate
        long now = System.currentTimeMillis();
        long elapsed = now - lastUpdate;
        remaining -= elapsed;
        lastUpdate = now;

        if(remaining < 0) remaining = 0;

        if(remaining == 0) finishState();
    }

    public void actionPerformed(ActionEvent e){
        // first update timer then make gui update itself
        updateTimer();
        updateDisplay();
    }

    public void updateDisplay(){
        gui.updateTimeDisplay();
        gui.updateStateDisplay();
    }

    public void finishState(){
        // pause the button through this method
        gui.playPauseButtonAction();

        // determine current phase
        switch (state) {
            case WORK:
                // determine if final phase of cycle ended and increment workPhasesElapsed
                ++workPhasesElapsed;
                if(workPhasesElapsed % workPhases == 0) {
                    setState(STATE_TYPE.LONG_BREAK);
                    TrayNotification.displayNotification(STATE_TYPE.LONG_BREAK);
                    soundManager.playSound("long_break_sound");
                }
                else {
                    setState(STATE_TYPE.SHORT_BREAK);
                    TrayNotification.displayNotification(STATE_TYPE.SHORT_BREAK);
                    soundManager.playSound("break_sound");
                }
                break;
            default:
                setState(STATE_TYPE.WORK);
                TrayNotification.displayNotification(STATE_TYPE.WORK);
                soundManager.playSound("work_bell_sound");
        }
    }
    public void pauseTimer(){
        // stop listening to action and set paused for outside use
        paused = true;
        timer.stop();
    }

    public void unpauseTimer(){
        // also functions as start timer method
        lastUpdate = System.currentTimeMillis();
        paused = false;
        timer.start();
    }

    public void stopTimer(){
        // unpauses timer in case timer was paused
        unpauseTimer();
        finishState();
        // since only timer event updates gui,
        // this has to be added
        updateDisplay();
    }

    public void setState(STATE_TYPE s){
        // setting the state also sets new countdown
        state = s;
        remaining = STATE_TYPE.getDuration(state);
    }

    public String getStateStr(){
        // conversion from state to str
        if(state == STATE_TYPE.WORK)
            return STATE_TYPE.str(state) + " (" + (workPhasesElapsed + 1) + ")";
        return STATE_TYPE.str(state);
    }
    public long getTimeMillis(){ return remaining ;}
    public boolean paused(){ return paused; }
}
