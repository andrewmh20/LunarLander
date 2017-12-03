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
        public static final int CANVAS_WIDTH = 300;
        public static final int CANVAS_HEIGHT = 300;
        public static final int SQUARE_VELOCITY = 4;

        // Update interval for timer, in milliseconds
        public static final int INTERVAL = 35;

        private int i =0;
        private int j =0;
        private int vold;
        
        //create the physics world
        //TODO:Do I need to pass this anything else?
        private LunarModel lm = new LunarModel();
        
        public Canvas(JLabel status) {
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

                public void keyPressed(KeyEvent e) {
                     if (e.getKeyCode() == KeyEvent.VK_UP) {
//TODO:Something


                    }
                }

                public void keyReleased(KeyEvent e) {
                    //square.setVx(0);
                    //square.setVy(0);

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
                lm.move();
                repaint();
            }
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
//            snitch.draw(g);
            //TODO:make color better....should I make this whole thing a seperate class, or 
            //just draw all in canvas directly like this?
            g.fillRect(lm.getPx(), lm.getPy(), 10, 10);

        }



    }
    

