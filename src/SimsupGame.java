import java.awt.BorderLayout;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SimsupGame implements Runnable {


public void run() {

    GameState gameState = new GameState();
    
    //TODO:In readme specify which port this game uses.
    
    //dialog for the Server to connect to
    final JFrame dialog = new JFrame("LunarLander LaunchPad");
    //TODO: Is this bad hostname is not encapsulated?
    
    Client client = null;
    //TODO:add a cancel option to these dialogs/close~!!!!
    //TODO:add a time out?
    while (client == null) {
        try {
            final String hostname = 
                    JOptionPane.showInputDialog(dialog, 
                            "Enter the hostname or IP Address of your Pilot "
                            + "Make sure he starts the simulation first!");
            client = new Client(gameState, hostname);
       }
       catch (UnknownHostException e) {
           e.printStackTrace(System.err);
           JOptionPane.showMessageDialog(dialog, "Hostname could not be resolved! "
                   + "See console for deatils or try again.",
                   "LunarLander LaunchPad", JOptionPane.ERROR_MESSAGE);
       }
       catch (IOException e) {
           e.printStackTrace(System.err);
           JOptionPane.showMessageDialog(dialog, "There was a problem! "
                   + "See console for deatils or try again.",
                   "LunarLander LaunchPad", JOptionPane.ERROR_MESSAGE);

       }
    }

    Thread clientThread = new Thread(client);
    clientThread.start();

    
    final JFrame frame = new JFrame("LunarLander");
    frame.setLocation(300, 300);

    
    //TODO:Panel or component?
    final JPanel TelemtryPanel = new TelemetryComponent(gameState);
    frame.add(TelemtryPanel, BorderLayout.EAST);
    
    // Main playing area
    final ErrorButtonPanel buttons = new ErrorButtonPanel(gameState);
    frame.add(buttons, BorderLayout.CENTER);

    
    // Put the frame on the screen
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

}


}
