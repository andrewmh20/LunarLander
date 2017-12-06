import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SimsupGame implements Runnable 
{
GameState gameState;
Client client;
Thread clientThread;


public void run() {
    // NOTE : recall that the 'final' keyword notes immutability even for local variables.

    //For each game you run, start a new server
    //TODO:change to be result of button
    gameState = new GameState();
    client = new Client(gameState);
    clientThread = new Thread(client);
    clientThread.start();

    
    
    // Top-level frame in which game components live
    // Be sure to change "TOP LEVEL FRAME" to the name of your game
    final JFrame frame = new JFrame("TOP LEVEL FRAME");
    frame.setLocation(300, 300);

    // Status panel
    final JPanel status_panel = new JPanel();
    frame.add(status_panel, BorderLayout.SOUTH);
    final JLabel status = new JLabel("Running...");
    status_panel.add(status);

    
    //TODO:Panel or component?
    final JPanel TelemtryPanel = new TelemetryComponent(gameState);
    
    frame.add(TelemtryPanel, BorderLayout.EAST);
    
    // Main playing area
    final ErrorButtonPanel buttons = new ErrorButtonPanel(gameState);
    frame.add(buttons, BorderLayout.CENTER);

    // Reset button
    final JPanel control_panel = new JPanel();
    frame.add(control_panel, BorderLayout.NORTH);

    // Note here that when we add an action listener to the reset button, we define it as an
    // anonymous inner class that is an instance of ActionListener with its actionPerformed()
    // method overridden. When the button is pressed, actionPerformed() will be called.

    
    // Put the frame on the screen
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //TODO:Make visible when done with network testing.
    frame.setVisible(true);

}


}
