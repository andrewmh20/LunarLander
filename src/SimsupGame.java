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

    GameState gameState = new GameState();
    
    //TODO:In readme specify which port this game uses.
    
    //dialog for the Server to connect to
    final JFrame dialog = new JFrame("LunarLander LaunchPad");
    //TODO: Is this bad hostname is not encapsulated?
    
    //TODO:Panel or component?
    final JPanel TelemetryPanel = new TelemetryComponent(gameState);

    
    
    Client client = null;
    //TODO:add a cancel option to these dialogs/close~!!!!
    //TODO:add a time out?
    //Lined the telemetry panel to the networking thread, so that I can repaint it, like before
    //it was linked to the canvas.
    while (client == null) {
        try {
            final String hostname = 
                    JOptionPane.showInputDialog(dialog, 
                            "Enter the hostname or IP Address of your Pilot "
                            + "Make sure he starts the simulation first!");
            client = new Client(gameState, hostname, TelemetryPanel);
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

    //Add the telem panel
    frame.add(TelemetryPanel, BorderLayout.EAST);

    
    // Main playing area
    final ErrorButtonPanel buttons = new ErrorButtonPanel(gameState);
    frame.add(buttons, BorderLayout.CENTER);

    //Create the main timer to update the telemetry panel--not linekd to any type of canvas like in server
//    Timer timer = new Timer(1000, new ActionListener() {
//        public void actionPerformed(ActionEvent e) {
//            //TODO:if this gets more complicaked make a method
//            //TODO: this is probably too fast! see my CPU usage.....
//            //TelemetryPanel.repaint();
//        }
//    });
    
    // MAKE SURE TO START THE TIMER!
//    timer.start();
    
    // Put the frame on the screen
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

}



}
