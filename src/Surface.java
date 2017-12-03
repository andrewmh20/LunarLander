import java.awt.Graphics;
import java.awt.geom.Path2D;

public class Surface extends GameObj{

    public Surface(int courtWidth,
            int courtHeight) {
        super(0, 0, 0, 0, courtWidth-10, courtHeight-10, courtWidth, courtHeight);
        // TODO Auto-generated constructor stub
        
        //Create the surface for the lander to land on
        //TODO: Make more complex
    }
    

    
    @Override
    public void draw(Graphics g) {
        g.fillOval(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
        
    }

}
