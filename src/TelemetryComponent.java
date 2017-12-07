import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

///TODO: Panel or component?
@SuppressWarnings("serial")
public class TelemetryComponent extends JPanel {

    
    private GameState gs;

    TelemetryComponent (GameState gs){
        
        super();
        this.gs = gs;
        
    }    
    @Override
    public void paintComponent(Graphics gc) {
        //TODO:Which one? THis works but fix to be correct
        super.paintComponent(gc);
        // display the light bulb here
        //TODO:set the distances to constants that I can change?....
        //System.out.println(Game.gameState.getVw());
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
        return new Dimension(200,50);
    }
    
}
