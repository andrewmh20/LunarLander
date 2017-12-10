import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class SimsupGame implements Runnable {


public void run() {

    //isEasy not relevant, but I'm just reusing the same gamestate class
    GameState gameState = new GameState(false);
    
    //TODO:In readme specify which port this game uses.
    
    //dialog for the Server to connect to
    final JFrame dialog = new JFrame("LunarLander LaunchPad");
    
    final TelemetryPanel TelemetryPanel = new TelemetryPanel(gameState, true);

    
    
    Client client = null;
    //Lined the telemetry panel to the networking thread, so that I can repaint it, like before
    //it was linked to the canvas.
    while (client == null) {
        try {
            final String hostname = 
                    JOptionPane.showInputDialog(dialog, 
                            "Enter the hostname or IP Address of your Pilot "
                            + "Make sure he starts the simulation first and has port 8080 opened!");
            
            if (hostname == null) {
                dialog.dispose();
                System.exit(0);
            }

            client = new Client(gameState, hostname, TelemetryPanel);
       }
       catch (UnknownHostException e) {
           e.printStackTrace(System.err);
           JOptionPane.showMessageDialog(dialog, "Hostname could not be resolved!\n"
                   + "Check your spelling. "
                   + "See console for deatils or try again.",
                   "LunarLander LaunchPad", JOptionPane.ERROR_MESSAGE);
           
           
       }
       catch (IOException e) {
           e.printStackTrace(System.err);
           JOptionPane.showMessageDialog(dialog, "There was a problem!\n"
                   + "Check that the pilot has started the game and has the correct ports opened.\n"
                   + "See console for deatils or try again.",
                   "LunarLander LaunchPad", JOptionPane.ERROR_MESSAGE);
           
       }
    }
    
    
    Thread clientThread = new Thread(client);
    clientThread.start();

    
    final JFrame frame = new JFrame("LunarLander");
    frame.setLocation(300, 300);

    //Add the telem panel
    frame.add(TelemetryPanel, BorderLayout.EAST);

    
    // Main playing area
    final ErrorButtonPanel buttons = new ErrorButtonPanel(gameState);
    frame.add(buttons, BorderLayout.CENTER);

    
    // Put the frame on the screen
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

}



}
