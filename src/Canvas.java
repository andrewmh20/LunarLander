    /**
     * CIS 120 Game HW
     * (c) University of Pennsylvania
     * @version 2.1, Apr 2017
     */

    import java.awt.*;
    import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;

    /**
     * Canvas
     * 
     * This class holds the primary game logic for how different objects interact with one another. Take
     * time to understand how the timer interacts with the different methods and how it repaints the GUI
     * on every tick().
     */
@SuppressWarnings("serial")
public class Canvas extends JPanel {

  

        public boolean playing = false; // whether the game is running 

        // Game constants
        public static final int CANVAS_WIDTH = 1000;
        public static final int CANVAS_HEIGHT = 500;
        public static final int SQUARE_VELOCITY = 4;

        // Update interval for timer, in milliseconds
        public static final int INTERVAL = 16;

        protected static final int THROTTLE_JUMP = 5;
        
        public static final int LEM_HEIGHT = 20;
        public static final int LEM_WIDTH = 20;


        //not private because I need coutner to work in reset, and in the keypress listerner class
        protected int i =0 ;
        private int j =0;
        
        //create the physics world
        //TODO:Do I need to pass this anything else?
        //Important to be final bc I'm passing it around to the error classes
        private final LunarModel lm = new LunarModel();
                
        private GameState gs;
        private TelemetryPanel telemetryPanel;
        
         private static Image LemSprite;
         private static Image FlameSprite;
         
         //private field for the shape that is the surface, so I can use it AND draw it
         //Leave the ability to change the path depending on the game---but do that later. would set in reset area.
          private Path2D surface;
          private Rectangle2D lemShape = new Rectangle2D.Float(-(LEM_WIDTH/2),-(LEM_HEIGHT/2),LEM_WIDTH, LEM_HEIGHT);

        private int k;;


        //TODO: change the status thing to just be a field of gs
        //Jpanel for display of info from model displayed in canvas
        public Canvas(GameState gs, TelemetryPanel tp) {
            
            //TODO:this means I cna remove all fixture logic from the model--just use the body.
             surface = new Path2D.Float();
             //TOtally wrong, just to see
             surface.moveTo(0, CANVAS_HEIGHT-10);
             
             ///TODO : make variable or something
             surface.lineTo(CANVAS_WIDTH-10, CANVAS_WIDTH-10);
            
             try {
                LemSprite = ImageIO.read(new File("Files/LEM.png")).getScaledInstance(LEM_WIDTH, LEM_HEIGHT, Image.SCALE_SMOOTH);
                FlameSprite = ImageIO.read(new File("Files/Flame.png")).getScaledInstance(20, 20, Image.SCALE_SMOOTH);
             } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            
            this.gs = gs;
            this.telemetryPanel = tp;
            // creates border around the court area, JComponent method
            setBorder(BorderFactory.createLineBorder(Color.BLACK));

            // The timer is an object which triggers an action periodically with the given INTERVAL. We
            // register an ActionListener with this timer, whose actionPerformed() method is called each
            // time the timer triggers. We define a helper method called tick() that actually does
            // everything that should be done in a single timestep.
            Timer timer = new Timer(INTERVAL, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    tick();
                }
            });
            // MAKE SURE TO START THE TIMER!
            timer.start();

            // Enable keyboard focus on the court area.
            // When this component has the keyboard focus, key events are handled by its key listener.
            setFocusable(true);

            // This key listener allows the square to move as long as an arrow key is pressed, by
            // changing the square's velocity accordingly. (The tick method below actually moves the
            // square.)
            addKeyListener(new KeyAdapter() {
                int temp;
                //TODO: Change j and i to +/- constants so that can change rate of throttle increase
                
                public void keyPressed(KeyEvent e) {
                     if (e.getKeyCode() == KeyEvent.VK_UP) {
                         
                         //j=0;
                         i+=THROTTLE_JUMP;
                         lm.throttle(i);

                     }
                     if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                         
                        // i=0;
                         i-=THROTTLE_JUMP;
                         lm.throttle(i);
                         

                     }
                     //Full throttle momentarily
                     if (e.getKeyCode() == KeyEvent.VK_F) {
                         
                         //store current throttle
                         temp = lm.getThrottle();
                         lm.throttle(LunarModel.MAX_THROTTLE);

                     }
                     //TODO: Add "Abort" key that sets full throttle until you manually bring it down/ shut it off
                     if (e.getKeyCode() == KeyEvent.VK_LEFT) {

                         
                         lm.thrustL();

                     }
                     if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

                         
                         lm.thrustR();

                     }
                     
             
                }

                public void keyReleased(KeyEvent e) {

                    if (e.getKeyCode() == KeyEvent.VK_F) {
                        
                        lm.throttle(temp);

                    }

                }
            });

        }

        /**
         * (Re-)set the game to its initial state.
         */
        public void reset() {
            
            //TODO:Reset
            /*square = new Square(CANVAS_WIDTH, CANVAS_HEIGHT, Color.BLACK);
            poison = new Poison(CANVAS_WIDTH, CANVAS_HEIGHT);
            snitch = new Circle(CANVAS_WIDTH, CANVAS_HEIGHT, Color.YELLOW);*/
            
            //OR just make a new one each time...this looks cleaner to me
            lm.reset();
            
            i =0;
            j =0;
            k = 0;
            playing = true;

            // Make sure that this component has the keyboard focus
            requestFocusInWindow();
        }

        /**
         * This method is called every time the timer defined in the constructor triggers.
         */
        //TODO:I made this private.....
        private void tick() {
            if (playing) {

                // update the display
                //TODO:This is a demo of what some type of network listener should do
                
                //TODO:Before or after move? probs doesnt matter.
                //error.causeFailure(lm);

                //TODO:refactor where the timer is and ticking. ie maybe make timer at top level
                //and pass as arg to both canvas and telempanel
                
                //if theres an error in state, cause the error
                //TODO:WILL NEED TO CHANGE THAT ERROR FIELD TO A QUEUE so that he can send multiple errors at time
                //paint before removing error from que, may as well repaint all here
                
                //TODO:moving paing up here doesnt fix anything, so can move back down later.
                //Need to actually fix logic of getting error from queue.
                repaint();//Need to paint before checking if crashed....
//
//                if(isCrashed(surface, lemShape)) {
//                    System.out.println("CRASHED");
//                }

                gs.setAltitude(lm.getAltitude());
                
                //TODO:change get error to not force waiting......annoying because in netwrork I need it 
                //to wait but here it doesnt. Rethink.
                
                Error err = gs.getErrorAttempt();
                //System.out.println(err);
                if (err != null) {
                    err.causeFailure(lm, gs);
                    gs.setErrorsDone(err);
                    //System.out.println("here");
                    

                }
                
                
                
                //update the game state velocities
                
                //TODO:commented out just for debugging networking, only game thread will ever really set
                //so not need to be synced. This is just an issue for client.
                //ok/ switch to errors.
                gs.setVx(lm.getVx());                
                gs.setVy(lm.getVy());
                gs.setVw(lm.getVw());
                
                //Set the linked panel labels
                telemetryPanel.VxLabel.setText("Horizontal Velocity: " + String.format("%1$.2f",gs.getVx())); 
                telemetryPanel.VyLabel.setText("Vertical Velocity: " + String.format("%1$.2f",gs.getVy())); 
                telemetryPanel.VwLabel.setText("Angular Velocity: " + String.format("%1$.2f",gs.getVw())); 
                telemetryPanel.altLabel.setText("Altitude: " + String.format("%1$.2f",gs.getAltitude()));
                telemetryPanel.errorLabel.setText("Computer Error Code: " + gs.getComputerErrorCode());

                
                
                
                
                
                //System.out.println(gs.getVy());

                lm.move();
             //System.out.println(lm.getPy());
                //System.out.println(lm.getVy());
//System.out.println(lm.getAttX());
//System.out.println(lm.getAttY());
//System.out.println(lem.getLinearVelocity());

                //super.repaint();

                
                
                //TODO:Still do actually need fixutre in the lemModel for landing.
                if (lm.isCrashed()) {
                    
                    //TODO:Add logic for velocity of colossion with collision, can unit test that
                    //TODO:DO something more exciting, i.e. "you lose" (Have seperate class for "Game State"
                    reset();
                }
                //k = time to win
                //TODO:gs.add contact light
                if (lm.isLanded() && k > 100) {
                    
                    JOptionPane.showMessageDialog(null, "Engine Shutdown!");
                    
                    //TODO:Add logic for velocity of colossion with collision, can unit test that
                    //TODO:DO something more exciting, i.e. "you lose" (Have seperate class for "Game State"
                }

            }
        }
        
        
      
       
         
        /**
         * We check if rect intersects with the lunar surface
         * @param rect The rectangle that contains the LEM sprite (in this case)
         * @return 
         */
        private boolean isCrashed(Shape shape, Rectangle2D rect) {

            return shape.intersects(rect);
        }
        
        
        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = ((Graphics2D)g);
            super.paintComponent(g);

            //snitch.draw(g);
            //TODO:make color better....should I make this whole thing a seperate class, or 
            //just draw all in canvas directly like this?

           // TODO: DRAW BASED ON SHAPE, not body (use constant that sets both)
            //TODO: THis is for now the logic for drawing the lunar lander FIX THIS
            
            //Draw the LunarLander:
//TODO:Get basic idea of coordinates working, then fine tune images.
            //GET better image
            this.setBackground(Color.WHITE);
            //
            g.setColor(Color.RED);
            g.drawRect(lm.getPx()+(LEM_WIDTH/2), lm.getPy()+(LEM_HEIGHT/2), 2, 2);
            g.setColor(Color.BLACK);

            g2d.draw(surface);
            
            //Drawing based on origin point of rectangle, consider center of mass to be basd on unifrom
            //density squares, so in center of square.
            
            int PxToDrawCenter = lm.getPx()+(LEM_WIDTH/2);
            int PyToDrawCenter = lm.getPy()+(LEM_HEIGHT/2);
            float angleToDraw = lm.getAngle();
            //* max in flame scale
            float flameSizeMin = 1f;
            float flameSizeMax = 20f;
            int flameSize = Math.round(
                    ((flameSizeMax-flameSizeMin)*(lm.getThrottle()-LunarModel.MIN_THROTTLE)/
                    (LunarModel.MAX_THROTTLE-LunarModel.MIN_THROTTLE))
                    + flameSizeMin
                    );
            //THis will be off, get the location first.
            //TODO:Fix this, messing things up.
            g2d.translate(PxToDrawCenter, PyToDrawCenter);
            //TODO:Double check sign consistency with lem model
            g2d.rotate(-angleToDraw);
            g2d.drawImage(LemSprite, -(LEM_WIDTH/2), -(LEM_HEIGHT/2), null);
            if (flameSize > 1 ) {
                g2d.drawImage(FlameSprite.getScaledInstance(FlameSprite.getWidth(null), flameSize, Image.SCALE_SMOOTH), -(LEM_WIDTH/2), LemSprite.getHeight(null)-(LEM_HEIGHT/2), null);
            }
            //TODO:this wont fully work, but idea is chaning value of LemShape with each image draw and get that 
            //coordinate system........wont work really. lets just try it.
            
            //TODO:draw the surface of teh ground however I do it.

            //THe collisions logic will be done entirely in Swing, since we just need to compare
            //2 boxes. 
            
            g2d.rotate(angleToDraw);
            g2d.translate(-PxToDrawCenter, -PyToDrawCenter);
            
            //draw the lunar surface (get these numbers form same place as lunar model to ensure consistency
            //maybe at to game state?
            g2d.drawLine(0, CANVAS_HEIGHT-10, Canvas.CANVAS_WIDTH/2,Canvas.CANVAS_HEIGHT-30);
            g2d.drawLine(Canvas.CANVAS_WIDTH/2,Canvas.CANVAS_HEIGHT-30, Canvas.CANVAS_WIDTH/2+10,Canvas.CANVAS_HEIGHT-30);
            g2d.drawLine(Canvas.CANVAS_WIDTH/2+10,Canvas.CANVAS_HEIGHT-30, Canvas.CANVAS_WIDTH,Canvas.CANVAS_HEIGHT-10);
            //TODO:Create a shape from those lines, and fill that shape.
            //g2d.fillRect(0,0,10000,10000);

           // g.fillRect(lm.getPx(), 
             //       lm.getPy(), 10, 10);
            
            
            
            
            //TODO:fix the thrust l vs right to be intutitive
            //TODO:fix the telem panel to draw as large as largest number.
            //System.out.println(lm.getAngle());
            //g.drawLine(lm.getPx(), lm.getPy(), (int)Math.round(lm.getPx()+30*Math.sin(lm.getAngle())), (int)Math.round(lm.getPy()+30*Math.cos(lm.getAngle())));
            //TODO: Draw the surface when its more complicated
            //g.drawLine(0, Canvas.CANVAS_HEIGHT-10, Canvas.CANVAS_WIDTH, Canvas.CANVAS_HEIGHT-10);

        }
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT);
        }



    }
    

