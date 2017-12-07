import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ErrorButtonPanel extends JPanel{
    
    private GameState gs;
    

    ErrorButtonPanel (GameState gs){
        
        super();
        this.gs = gs;
//Set layout
        JButton FullThrottle = new JButton();
        FullThrottle.setText("Full Throttle");
        FullThrottle.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setError(new FullThrottleError());
                // TODO Auto-generated method stub
                
            }
            
        });
        this.add(FullThrottle);
        
        JButton StuckLeftThruster = new JButton();
        StuckLeftThruster.setText("Stuck Left Thruster");
        StuckLeftThruster.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setError(new StuckLeftThrusterError());
                // TODO Auto-generated method stub
                
            }
            
        });
        this.add(StuckLeftThruster);
        
        JButton StuckRightThruster= new JButton();
        StuckRightThruster.setText("Stuck Right Thruster");
        StuckRightThruster.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setError(new StuckRightThrusterError());
                // TODO Auto-generated method stub
                
            }
            
        });
        
        //TODO:reset errors, not game
        this.add(StuckRightThruster);
       
        JButton ResetGame= new JButton();
        ResetGame.setText("Reset Game");
        ResetGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setError(new ResetErrors());
                // TODO Auto-generated method stub
                
            }
            
        });
        this.add(ResetGame);

        JButton Error1201= new JButton();
        Error1201.setText("Error 1201");
        Error1201.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setError(new ComputerOverloadedError());
                // TODO Auto-generated method stub
                
            }
            
        });
        this.add(Error1201);
        
        
    }    
    
    public void paintComponent(Graphics gc) {
        super.repaint();
       // display the light bulb here
        //TODO:set the distances to constants that I can change?....
        //System.out.println(Game.gameState.getVw());
        
            //TODO: Add fuel
            
    }
    
    @Override
    public Dimension getPreferredSize() {
        //TODO:FIx to make correct
        return new Dimension(200,200);
    }



}
