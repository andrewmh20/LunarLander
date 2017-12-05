/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    
  //TODO: fine tune, this is just for testing
    //Game info panel
    //"package"
    //WIll ever only have 1 Game at once....
    GameState gameState;
    Server server;
    Thread serverThread;
    //NEeds to be more public field so that can be accessed from other parts.....but we dont want to be able to set it,
    //so jus tmake a getter....eh idk! WRong way to organize....
    
    
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for local variables.

        //For each game you run, start a new server
        //TODO:change to be result of button
        gameState = new GameState();
        server = new Server(gameState);
        serverThread = new Thread(server);
        serverThread.start();

        
        
        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("TOP LEVEL FRAME");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);

        
        //TODO:Fix this.
        //TODO:Panel or component?
        final JPanel TelemtryPanel = new TelemetryComponent(gameState);
        
        frame.add(TelemtryPanel, BorderLayout.EAST);
/*
        final JLabel fuelLeft = new JLabel("Fuel Remaining" + String.valueOf(gameState.getFuel()));
        gameStatePanel.add(fuelLeft);
        
        final JLabel Vx = new JLabel("Horizontal Velocity" + String.valueOf(gameState.getVx()));
        gameStatePanel.add(Vx);
        final JLabel Vy = new JLabel("Vertical Velocity" + String.valueOf(gameState.getVy()));
        gameStatePanel.add(Vy);
        fuelLeft.setText("Hi");
        */

        
        // Main playing area
        final Canvas court = new Canvas(status);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
            }
        });
        control_panel.add(reset);

        
        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    //TODO:Handle exception! THIS IS JUST FOR TESTING
    public static void main(String[] args) throws IOException {
        //TODO:Start network handler to accept client connections
        //TODO: make it only start when server is created. If client is created, run the connect mmethod in 
        //Network handler
        //TODO:I should maybe do all the networking logic in networkhandler? as in all in
        //that one seperate thread.....
        SwingUtilities.invokeLater(new Game());
        
    }
}