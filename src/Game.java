/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.lang.reflect.Array;

import javax.swing.*;
import javax.swing.plaf.metal.MetalIconFactory;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    
    boolean nextFrameOpened = false;
    public void run() {
        
        //The first dialog box for mode selection
        //TODO:make this close correctly when closed
        final JFrame dialog = new JFrame("LunarLander LaunchPad");
        
        //dialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //final JButton[] modeOptions = new JButton[2];
        
//        final JButton SimSup =  new JButton("SimSup");
//        SimSup.addActionListener(new ActionListener() {
//            
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                SwingUtilities.invokeLater(new SimsupGame());
//                //dialog.setVisible(false);
//                dialog.dispose();
//               // nextFrameOpened = true;
//                }
//        });
//        
//        Array.set(modeOptions, 0, SimSup);
//        
//        
//        final JButton Pilot = new JButton("Pilot");
//        Pilot.addActionListener(new ActionListener() {
//            
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                SwingUtilities.invokeLater(new PilotGame());
//                //dialog.setVisible(false);
//                dialog.dispose();
//               
//            }
//        });
//        
//        Array.set(modeOptions, 1, Pilot);
//
        String[] modeOptions = new String[2];
        Array.set(modeOptions, 0, "SimSup");
        Array.set(modeOptions, 1, "Pilot");
        
        int result = JOptionPane.showOptionDialog(dialog, "Are you the pilot or the SimSup?", 
                "LunarLander Launchpad", 
                0, 0, new ImageIcon("Files/MissionControl.jpg"), modeOptions, null);
        if (result == 0) {
            SwingUtilities.invokeLater(new SimsupGame());
            dialog.dispose();
        }
        else if (result == 1) {
            SwingUtilities.invokeLater(new PilotGame());
            dialog.dispose();
        }
        else if (result == JOptionPane.CLOSED_OPTION){
            dialog.dispose();            
            System.exit(0);
        }

        System.out.println(result);
        //dialog.dispose();
    }
    
    
    public static void main(String[] args) throws IOException {
        //TODO:Start network handler to accept client connections
        //TODO: make it only start when server is created. If client is created, run the connect mmethod in 
        //Network handler
        //TODO:I should maybe do all the networking logic in networkhandler? as in all in
        //that one seperate thread.....
        SwingUtilities.invokeLater(new Game());
        
    }
}