import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;

///TODO: Panel or component?
@SuppressWarnings("serial")
public class TelemetryComponent extends JPanel {

    
    @Override
    public void paintComponent(Graphics gc) {
        //TODO:Which one? THis works but fix to be correct
        super.paintComponent(gc);
        super.repaint();
       // display the light bulb here
        //TODO:set the distances to constants that I can change?....
            gc.drawString("Horizontal Velocity" + String.format("%1$.2f",Game.gameState.getVx()), 0, 10);
            gc.drawString("Vertical Velocity" + String.format("%1$.2f",Game.gameState.getVy()), 0, 25);
            gc.drawString("Angular Velocity" + String.format("%1$.2f",Game.gameState.getVw()), 0, 40);
            
            //TODO: Add fuel
            
    }
    
    @Override
    public Dimension getPreferredSize() {
        //TODO:FIx to make correct
        return new Dimension(200,50);
    }
    
}
