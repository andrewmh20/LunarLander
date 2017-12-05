    /**
     * CIS 120 Game HW
     * (c) University of Pennsylvania
     * @version 2.1, Apr 2017
     */

    import java.awt.*;
    import java.awt.event.*;
    import javax.swing.*;

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
        private JLabel status; // Current status text, i.e. "Running..."

        // Game constants
        public static final int CANVAS_WIDTH = 500;
        public static final int CANVAS_HEIGHT = 500;
        public static final int SQUARE_VELOCITY = 4;

        // Update interval for timer, in milliseconds
        public static final int INTERVAL = 16;

        //not private because I need coutner to work in reset, and in the keypress listerner class
        protected int i =0 ;
        private int j =0;
        private int vold;
        
        //create the physics world
        //TODO:Do I need to pass this anything else?
        private LunarModel lm;
        
        //Error from network
        private Error error = new FullThrottleError();
        //TODO: this creation will happen on network;
        //ALSO--TODO: THis entire canvas is ONLY forthe server, I need a cleint one as well.
        
        private GameState gs;
        
        //TODO: change the status thing to just be a field of gs
        public Canvas(JLabel status, GameState gs) {
            
            this.gs = gs;
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
            timer.start(); // MAKE SURE TO START THE TIMER!

            // Enable keyboard focus on the court area.
            // When this component has the keyboard focus, key events are handled by its key listener.
            setFocusable(true);

            // This key listener allows the square to move as long as an arrow key is pressed, by
            // changing the square's velocity accordingly. (The tick method below actually moves the
            // square.)
            addKeyListener(new KeyAdapter() {

                //TODO: Change j and i to +/- constants so that can change rate of throttle increase
                
                public void keyPressed(KeyEvent e) {
                     if (e.getKeyCode() == KeyEvent.VK_UP) {
                         
                         j=0;
                         //TODO:FOR NOW JUST APPLIES FORCE ONCE
                         lm.throttle(i);
                         i++;

                     }
                     if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                         
                         i=0;
                         //TODO:FOR NOW JUST APPLIES FORCE ONCE
                         lm.throttle(j);
                         j--;

                     }
                     //Full throttle momentarily
                     if (e.getKeyCode() == KeyEvent.VK_F) {
                         
                         
                         //TODO:FOR NOW JUST APPLIES FORCE ONCE
                         //max useful throttle, not necesairly max throttle
                         lm.throttle(20);

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
                    //square.setVx(0);
                    //square.setVy(0);
                    //lm.throttle(0);
                    if (e.getKeyCode() == KeyEvent.VK_F) {
                        
                        
                        //TODO:FOR NOW JUST APPLIES FORCE ONCE
                        //max useful throttle, not necesairly max throttle
                        lm.throttle(0);

                    }

                }
            });

            this.status = status;
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
            lm = new LunarModel();
            
            i =0;
            j =0;
            vold=0;
            playing = true;
            status.setText("Running...");

            // Make sure that this component has the keyboard focus
            requestFocusInWindow();
        }

        /**
         * This method is called every time the timer defined in the constructor triggers.
         */
        void tick() {
            if (playing) {
                /*// advance the square and snitch in their current direction.
                square.move();
                snitch.move();

                // make the snitch bounce off walls...
                snitch.bounce(snitch.hitWall());
                // ...and the mushroom
                snitch.bounce(snitch.hitObj(poison));

                // check for the game end conditions
                if (square.intersects(poison)) {
                    playing = false;
                    status.setText("You lose!");
                } else if (square.intersects(snitch)) {
                    playing = false;
                    status.setText("You win!");
                }
*/
                
                // update the display
                //TODO:This is a demo of what some type of network listener should do
                
                //TODO:Before or after move? probs doesnt matter.
                //error.causeFailure(lm);

                //update the game state velocities
                gs.setVx(lm.getVx());
               gs.setVy(lm.getVy());
                gs.setVw(lm.getVw());

                lm.move();
             //System.out.println(lm.getPy());
                //System.out.println(lm.getVy());
//System.out.println(lm.getAttX());
//System.out.println(lm.getAttY());
//System.out.println(lem.getLinearVelocity());

                super.repaint();
                repaint();
                
                if (lm.isCollided()) {
                    
                    //TODO:Add logic for velocity of colossion with collision, can unit test that
                    //TODO:DO something more exciting, i.e. "you lose" (Have seperate class for "Game State"
                    reset();
                }
            }
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
//            snitch.draw(g);
            //TODO:make color better....should I make this whole thing a seperate class, or 
            //just draw all in canvas directly like this?

           // TODO: DRAW BASED ON SHAPE, not body (use constant that sets both)
            //TODO: THis is for now the logic for drawing the lunar lander FIX THIS
            g.fillRect(lm.getPx(), 
                    lm.getPy(), 10, 10);
            //System.out.println(lm.getAngle());
            g.drawLine(lm.getPx(), lm.getPy(), (int)Math.round(lm.getPx()+30*Math.sin(lm.getAngle())), (int)Math.round(lm.getPy()+30*Math.cos(lm.getAngle())));
            //TODO: Draw the surface when its more complicated
            g.drawLine(0, Canvas.CANVAS_HEIGHT-10, Canvas.CANVAS_WIDTH, Canvas.CANVAS_HEIGHT-10);

        }
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT);
        }



    }
    

