

// imports necessary libraries for Java swing
import java.io.IOException;
import java.lang.reflect.Array;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import game.PilotGame;
import game.SimsupGame;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {

    public static void main(String[] args) throws IOException {
        // TODO:Start network handler to accept client connections
        // TODO: make it only start when server is created. If client is created, run the connect
        // mmethod in
        // Network handler
        // TODO:I should maybe do all the networking logic in networkhandler? as in all in
        // that one seperate thread.....
        SwingUtilities.invokeLater(new Game());

    }

    boolean nextFrameOpened = false;

    @Override
    public void run() {

        // The first dialog box for mode selection
        // TODO:make this close correctly when closed
        final JFrame dialog = new JFrame("LunarLander LaunchPad");

        final String[] modeOptions = new String[2];
        Array.set(modeOptions, 0, "SimSup");
        Array.set(modeOptions, 1, "Pilot");

        final int result = JOptionPane.showOptionDialog(dialog, "Are you the pilot or the SimSup?",
                "LunarLander Launchpad", 0, 0, new ImageIcon("files/MissionControl.jpg"),
                modeOptions, null);
        if (result == 0) {
            SwingUtilities.invokeLater(new SimsupGame());
            dialog.dispose();
        } else if (result == 1) {
            SwingUtilities.invokeLater(new PilotGame());
            dialog.dispose();
        } else if (result == JOptionPane.CLOSED_OPTION) {
            dialog.dispose();
            System.exit(0);
        }

    }
}