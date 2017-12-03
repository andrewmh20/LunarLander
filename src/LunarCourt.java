import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Path2D;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

@SuppressWarnings("serial")
public class LunarCourt extends JPanel {
    
    //Physics
    //TODO:This is wrong! Just to see if concept works

    public final Body lander;
    private final World lunarWorld;
    
    //Actual sprites
    private final Surface lunarSurface;
    private final LanderGameObj landerSwing;
    
    public static final Vec2 GRAVITY = new Vec2(0f, .05f);
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;
    
    public static final int MAX_THROTTLE = 100;
    public static final int MIN_THROTTLE = 0;
    //thruster torque settings
    public static final int  torque = 10;
    

    
    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 16;
    
    LunarCourt(JLabel status){
        //create the lander object and the world for the physics
        lunarWorld = new World(GRAVITY);
        LanderDef ld = new LanderDef();
        //Not Body because I need Landers methods....
        //TODO:Will need to fix this, make it a body
        lander = lunarWorld.createBody(ld);
        lander.m_world = lunarWorld;
        //System.out.println(lander.m_world);
        lunarSurface = new Surface(WIDTH,HEIGHT);
        
        landerSwing  = new LanderGameObj((int) lander.getLinearVelocity().x,
                (int)lander.getLinearVelocity().y,
                (int)lander.getPosition().x,(int)lander.getPosition().y,20,20,WIDTH,HEIGHT);

        timer.start(); // MAKE SURE TO START THE TIMER!
     // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key is pressed, by
        // changing the square's velocity accordingly. (The tick method below actually moves the
        // square.)
    addKeyListener(new KeyAdapter() {
       public void keyPressed(KeyEvent e) {
            
                throttle(99);
        }
    });


    }

    public void throttle(int throt) {
        
        float angle = lander.getAngle();
        
        Vec2 attitudeVec = new Vec2((float)Math.cos(angle), (float)Math.sin(angle));
        
        if (throt > MAX_THROTTLE) {
            lander.applyForceToCenter(attitudeVec.mul(MAX_THROTTLE).negate());
            
        }
        else if (throt < MIN_THROTTLE) {
           lander.applyForceToCenter(attitudeVec.mul(MIN_THROTTLE).negate());
            
        }
        else {
            lander.applyForceToCenter(attitudeVec.mul(throt).negate());
            System.out.println(attitudeVec.toString());

        }
    }
    
    public void thrustL() {
        lander.applyTorque(-torque);
    }
    public void thrustR() {
        lander.applyTorque(torque);

        
    }
    
    //Try it as inner class.....to get to physics engine stuff
    public class LanderGameObj extends GameObj{

        public LanderGameObj(int vx, int vy, int px, int py, int width, int height, int courtWidth,
                int courtHeight) {
            
            super( vx,  vy,  px, py,  width,  height,  courtWidth, courtHeight);
            
            // TODO Auto-generated constructor stub
            
            //Create the surface for the lander to land on
            //TODO: Make more complex
        }
        
        
        @Override
        //TODO:FIx all this to delegate to physics engine---I do so in the constructor...
        public void move() {
            this.px += lander.getPosition().x;
            this.py += lander.getPosition().y;
            clip();
        }

        
        @Override
        public void draw(Graphics g) {
            g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
            
        }

    }

    
    // The timer is an object which triggers an action periodically with the given INTERVAL. We
    // register an ActionListener with this timer, whose actionPerformed() method is called each
    // time the timer triggers. We define a helper method called tick() that actually does
    // everything that should be done in a single timestep.
    Timer timer = new Timer(INTERVAL, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            tick();
        }
    });
    


    void tick() {
            // advance the square and snitch in their current direction.
        requestFocusInWindow();
    
        lunarWorld.step(1,3,3);
            landerSwing.move();
            lunarWorld.clearForces();

            //System.out.println(lunarWorld.getBodyCount());

            // update the display
            repaint();

    }
 
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //lunarSurface.draw(g);
        landerSwing.draw(g);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    
    //TODO: Make the lunar surface generate randomly
}
