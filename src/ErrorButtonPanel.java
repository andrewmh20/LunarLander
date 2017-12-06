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
        StuckLeftThruster.setText("Full Throttle");
        StuckLeftThruster.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setError(new StuckLeftThrusterError());
                // TODO Auto-generated method stub
                
            }
            
        });
        this.add(StuckLeftThruster);
        
        JButton StuckRightThruster= new JButton();
        StuckRightThruster.setText("FullThrottleError");
        StuckRightThruster.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gs.setError(new StuckRightThrusterError());
                // TODO Auto-generated method stub
                
            }
            
        });
        this.add(StuckRightThruster);
        
        
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
