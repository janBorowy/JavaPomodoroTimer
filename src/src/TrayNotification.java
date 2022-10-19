package src;

import java.awt.*;

public class TrayNotification {

    public static void displayNotification(PomodoroTimer.STATE_TYPE state){
        SystemTray tray = SystemTray.getSystemTray();
        Image img = Toolkit.getDefaultToolkit().getImage("icon.png");
        String msg = "";
        switch(state){
            case WORK:
                msg = "Time to work";
                break;
            case SHORT_BREAK:
                msg = "Time for a short break";
                break;
            case LONG_BREAK:
                msg = "You earned it! Take some time off.";
                break;
        }
        TrayIcon icon = new TrayIcon(img, "PomodoroTimer");
        icon.setImageAutoSize(true);

        try {
            tray.add(icon);
        } catch(AWTException e) {

        }
        icon.displayMessage("Java Pomodoro Timer", msg, TrayIcon.MessageType.INFO);
    }

}
