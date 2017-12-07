import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

///TODO: Panel or component?
@SuppressWarnings("serial")
public class TelemetryPanel extends JPanel {

    //Package because other people need to be able to update this panel
    
    final JLabel VxLabel = new JLabel("Horizontal Velocity: ");
    final JLabel VyLabel = new JLabel("Vertical Velocity: ");
    final JLabel VwLabel = new JLabel("Rotational Velocity: ");
    final JLabel altLabel =  new JLabel("Altitude : ");
    final JLabel errorLabel = new JLabel("Error Code : ");
    
    private GameState gs;


    TelemetryPanel (GameState gs){
        
        super();
        this.gs = gs;
        
        add(VxLabel);
        add(VyLabel);
        add(VwLabel);
        add(altLabel);
        add(errorLabel);

    }    
    
    //TODO:i could have a dfucntion that sets the labels and make the labels private. ugh
    // just see if what Im doing works, and then get to work on graphics/make playable
    
   /*
    @Override
    public void paintComponent(Graphics gc) {
        //TODO:Which one? THis works but fix to be correct
        super.paintComponent(gc);
        gc.drawString("Horizontal Velocity: " + String.format("%1$.2f",gs.getVx()), 0, 15);
            gc.drawString("Vertical Velocity: " + String.format("%1$.2f",gs.getVy()), 0, 30);
            gc.drawString("Angular Velocity: " + String.format("%1$.2f",gs.getVw()), 0, 45);
            gc.drawString("Altitude: " + String.format("%1$.2f",gs.getAltitude()), 0, 60);
            //peek, because computer error might also have an effect

            if (gs.getShowComputerError()) {
                //TODO: this still has issues with when it's seen....maybe err
            gc.drawString("Computer Error Code: " + gs.getComputerErrorCode(), 0, 75);
            System.out.println(gs.getComputerErrorCode());
            }
            else {
                gc.drawString("Computer Error Code:" , 0, 75);

            }

            //TODO: Add fuel
            
    }
    
    @Override
    public Dimension getPreferredSize() {
        //TODO:FIx to make correct
        return new Dimension(200,75);
    }
    */
    
}
