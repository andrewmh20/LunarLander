import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
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
        //super.paintComponent(gc); //THIS MAY HAVE CAUSED HIGH CPU? Migh tstill be too high :)
        super.repaint();
       // display the light bulb here
        //TODO:set the distances to constants that I can change?....
        //System.out.println(Game.gameState.getVw());
            gc.drawString("Horizontal Velocity" + String.format("%1$.2f",gs.getVx()), 0, 10);
            gc.drawString("Vertical Velocity" + String.format("%1$.2f",gs.getVy()), 0, 25);
            gc.drawString("Angular Velocity" + String.format("%1$.2f",gs.getVw()), 0, 40);
            gc.drawString("Altitude" + String.format("%1$.2f",gs.getAltitude()), 0, 55);

            //TODO: Add fuel
            
    }
    
    @Override
    public Dimension getPreferredSize() {
        //TODO:FIx to make correct
        return new Dimension(200,50);
    }
    
}
