    /**
     * CIS 120 Game HW
     * (c) University of Pennsylvania
     * @version 2.1, Apr 2017
     */

    import java.awt.*;
    import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

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

        protected static final int THROTTLE_JUMP = 10;
        
        public static final int LEM_HEIGHT = 20;
        public static final int LEM_WIDTH = 20;

        private static final float FUEL_INCREMENT = 1;

        private static final float FUEL_INCREMENT_THRUSTER = 5;

        private static final int LANDED_DURATION = 90;

        protected static final float NULL_FORCES_PENALTY = 20;


        //not private because I need coutner to work in reset, and in the keypress listerner class
        private int i =0;
        private int j =0;
        private Timer timer;
        
        //create the physics world
        //TODO:Do I need to pass this anything else?
        //Important to be final bc I'm passing it around to the error classes
        private LunarModel lm;
                
        private GameState gs;
        private TelemetryPanel telemetryPanel;
        
         private static Image LemSprite;
         private static Image FlameSprite;
         
         //private field for the shape that is the surface, so I can use it AND draw it
         //Leave the ability to change the path depending on the game---but do that later. would set in reset area.
          private Path2D surface;
          private Rectangle2D lemShape = new Rectangle2D.Float(-(LEM_WIDTH/2),-(LEM_HEIGHT/2),LEM_WIDTH, LEM_HEIGHT);
        private int k;

        private LinkedList<Vec2> vertices; 
        private Path2D lunarSurface; 

        private boolean  firstExit = true;

        //TODO: change the status thing to just be a field of gs
        //Jpanel for display of info from model displayed in canvas
        public Canvas(GameState gs, TelemetryPanel tp) {
            //Copied from reset.

            // Make sure that this component has the keyboard focus
            

            //TODO:this means I cna remove all fixture logic from the model--just use the body.
             surface = new Path2D.Float();
             //TOtally wrong, just to see
             surface.moveTo(0, CANVAS_HEIGHT-10);
             
             ///TODO : make variable or something
             surface.lineTo(CANVAS_WIDTH-10, CANVAS_WIDTH-10);
             this.lm = new LunarModel();

             vertices  = lm.getSurfaceVertices();
              lunarSurface = new Path2D.Float();
             
             //TODO:I can move the getting out of this method....
             lunarSurface.moveTo(vertices.get(0).x, vertices.get(0).y);
             for (int i = 1; i < vertices.size(); i++) {
                 //System.out.println(vertices.size());              
                 lunarSurface.lineTo(vertices.get(i).x, vertices.get(i).y);
                 //lunarSurface.moveTo(vertices.get(i+1).x, vertices.get(i+1).y);
                 //
                             }
             //close the curve
             lunarSurface.lineTo(CANVAS_WIDTH, CANVAS_HEIGHT);
             lunarSurface.lineTo(0, CANVAS_HEIGHT);
             lunarSurface.lineTo(vertices.get(0).x, vertices.get(0).y);
             
            
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
            timer = new Timer(INTERVAL, new ActionListener() {
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
            
            i = 0;
            j = 0;
            k = 0;
            playing = true;
            //this.gs.reset();
            this.requestFocusInWindow();

            KeyAdapter easyKeyAdapter = new KeyAdapter() {
                int temp;
                //TODO: Change j and i to +/- constants so that can change rate of throttle increase
                final public static int FUEL_INCREMENT_THRUSTER_JUMP_SCALE = 3;//use 3 times as much fuel 
                boolean first = true;

                public void keyPressed(KeyEvent e) {
                     
                     

                     //Full throttle momentarily
                     if (e.getKeyCode() == KeyEvent.VK_UP) {
                         
                         //store current throttle
                         if (first) {
                             temp = lm.getThrottle();
                             first = false;
                         }
                         lm.throttle(LunarModel.MAX_THROTTLE, gs.getHasFuel());

                     }
                                          
                     //TODO:holding down key doesnt work?
                     if (e.getKeyCode() == KeyEvent.VK_LEFT) {

                         
                         if (gs.getVw() !=0) {
                             gs.setFuel(Math.max(gs.getFuel()-NULL_FORCES_PENALTY,0));

                         }
                         lm.nullAngularForces(gs.getHasFuel());
                         
                         
                         //System.out.println(e.getKeyChar());
                         lm.jumpL(gs.getHasFuel());
                         gs.setFuel(Math.max(gs.getFuel()-FUEL_INCREMENT_THRUSTER*FUEL_INCREMENT_THRUSTER_JUMP_SCALE,0));


                     }

                     if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

                         if (gs.getVw() !=0) {
                             gs.setFuel(Math.max(gs.getFuel()-NULL_FORCES_PENALTY,0));

                         }
                         lm.nullAngularForces(gs.getHasFuel());

                         lm.jumpR(gs.getHasFuel());
                         gs.setFuel(Math.max(gs.getFuel()-FUEL_INCREMENT_THRUSTER*FUEL_INCREMENT_THRUSTER_JUMP_SCALE,0));


                     }

                     
             
                }

                public void keyReleased(KeyEvent e) {

                    if (e.getKeyCode() == KeyEvent.VK_UP) {
                        
                        lm.throttle(temp,gs.getHasFuel());
                        first = true;

                    }

                }
            };

         KeyAdapter hardKeyAdapter = new KeyAdapter() {
             int temp;
             //TODO: Change j and i to +/- constants so that can change rate of throttle increase
             final public static int FUEL_INCREMENT_THRUSTER_JUMP_SCALE = 3;//use 3 times as much fuel 
             boolean first = true;

             public void keyPressed(KeyEvent e) {
                  if (e.getKeyCode() == KeyEvent.VK_UP) {
                      
                      //j=0;
                      i+=THROTTLE_JUMP;
                      temp = lm.getThrottle();
                      lm.throttle(i, gs.getHasFuel());

                  }
                  if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                      
                     // i=0;
                      i-=THROTTLE_JUMP;
                      temp = lm.getThrottle();
                      //TODO:change behavior for when F is held and then down is pressed and F is released
                      lm.throttle(i, gs.getHasFuel());
                      

                  }
                  
                  ///TODO:For readme, the reason gs is so abstracted from lm--ie why not just use lm.getVx becuase I thought i might need ot have
                  //the abstaction, but as it turned out anyone that needed acesss to gs had access to lem so it just got confugisojns
                  if (e.getKeyCode() == KeyEvent.VK_N) {
                      if (gs.getVw() !=0) {
                          gs.setFuel(Math.max(gs.getFuel()-NULL_FORCES_PENALTY,0));

                      }
                      lm.nullAngularForces(gs.getHasFuel());


                   }

                  //Full throttle momentarily
                  if (e.getKeyCode() == KeyEvent.VK_F) {
                      
                      //store current throttle
                      if (first) {
                          temp = lm.getThrottle();
                          first = false;
                      }
                      lm.throttle(LunarModel.MAX_THROTTLE, gs.getHasFuel());

                  }
                  
                //kill Engine
                  if (e.getKeyCode() == KeyEvent.VK_K) {
                      
                      //store current throttle
                      lm.throttle(LunarModel.MIN_THROTTLE, gs.getHasFuel());
                      temp = 0;
                      i = 0;

                  }
                  
                  //TODO: Add "Abort" key that sets full throttle until you manually bring it down/ shut it off
                  if (e.getKeyCode() == KeyEvent.VK_LEFT) {

                      //System.out.println(e.getKeyChar());

                      lm.thrustL(gs.getHasFuel());
                      gs.setFuel(Math.max(gs.getFuel()-FUEL_INCREMENT_THRUSTER,0));

                  }
                  if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

                      
                      lm.thrustR(gs.getHasFuel());
                      gs.setFuel(Math.max(gs.getFuel()-FUEL_INCREMENT_THRUSTER,0));


                  }
                  
                  //TODO:holding down key doesnt work?
                  if (e.getKeyCode() == KeyEvent.VK_G) {

                      //System.out.println(e.getKeyChar());
                      lm.jumpR(gs.getHasFuel());
                      gs.setFuel(Math.max(gs.getFuel()-FUEL_INCREMENT_THRUSTER*FUEL_INCREMENT_THRUSTER_JUMP_SCALE,0));


                  }

                  if (e.getKeyCode() == KeyEvent.VK_D) {

                      
                      lm.jumpL(gs.getHasFuel());
                      gs.setFuel(Math.max(gs.getFuel()-FUEL_INCREMENT_THRUSTER*FUEL_INCREMENT_THRUSTER_JUMP_SCALE,0));


                  }

                  
          
             }

             public void keyReleased(KeyEvent e) {

                 if (e.getKeyCode() == KeyEvent.VK_F) {
                     
                     lm.throttle(temp,gs.getHasFuel());
                     first = true;

                 }

             }

         };
            if(gs.getIsEasy()) {
                addKeyListener(easyKeyAdapter);

            }
            else {
                addKeyListener(hardKeyAdapter);
            }
            

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
            gs.reset();
            //CANNOT DO THIS BC GS IS BEING PASSED IN! 1 per game bc everything is linked through game stategs = new GameState();
            timer.start();

            
            i =0;
            j =0;
            k = 0;
            playing = true;
            firstExit = true;

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
                
                
                //TODO:change get error to not force waiting......annoying because in netwrork I need it 
                //to wait but here it doesnt. Rethink.
                
                gs.doErrors(this, lm, gs);
                telemetryPanel.updateTelemetryPanel();

                //TODO: actually, thats the way to screw up the telemetry--just set gs.vx but not lm.vx
                
                //update the game state velocities
                
                //TODO:commented out just for debugging networking, only game thread will ever really set
                //so not need to be synced. This is just an issue for client.
                //ok/ switch to errors.
                gs.setVx(lm.getVx());                
                gs.setVy(lm.getVy());
                gs.setVw(lm.getVw());
                gs.setAngle(lm.getAngle());
                gs.setAltitude(lm.getAltitude());
                gs.setContactLight(lm.getContactLight());
                //TODO:Turn fuel back on
                ///TODO: add thruster fuel gaugue too.
                gs.setFuel(Math.max(gs.getFuel() - (FUEL_INCREMENT*lm.getThrottle()),0));

                
                
                lm.setBounds(this.getWidth(), this.getHeight());

                //Yes I know this is only tthe top left. There are bigger priorities
                //System.out.println(firstExit);
                if ((lm.getPx() < 0 || lm.getPx() > CANVAS_WIDTH || lm.getPy() < 0) && firstExit) {
                    
                    JOptionPane.showMessageDialog(null, "You're leaving the target landing area! Be careful!");
                    firstExit = false;
                }
                if(lm.getPy() > 0 && lm.getPx() > 0 && lm.getPx() < CANVAS_WIDTH) {
                    firstExit = true;
                }
                
                //Set the linked panel labels
                
                
                
                
                
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
                    
                    //TODO:DO something more exciting, i.e. "you lose" (Have seperate class for "Game State"

                    JOptionPane.showMessageDialog(null, "Oh no! You crashed an expensive lander!");
                    timer.stop();
                }
                //Fuel left can never be measured exactly! 
                System.out.println(gs.getFuel());
                if (gs.getFuel() < 1) {
                    //System.out.println("fuelllop");
                    lm.throttle(0,true);
                    gs.setHasFuel(false);
                    
                    
                }
                //k = time to win
                //TODO:gs.add contact light
                if (lm.isLanded()) {
                    k++;
                }
                else {
                    k = 0;
                }
                if (lm.isLanded() && k > LANDED_DURATION ) {
                    
                    JOptionPane.showMessageDialog(null, "You landed!");
                    timer.stop();
                    

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
            Color Navy = new Color(25,25,112);
            this.setBackground(Navy);
            //
            g.setColor(Color.RED);
            g.drawRect(lm.getPx()+(LEM_WIDTH/2), lm.getPy()+(LEM_HEIGHT/2), 2, 2);
            g.setColor(Color.BLACK);

            //g2d.draw(surface);
            
            //Drawing based on origin point of rectangle, consider center of mass to be basd on unifrom
            //density squares, so in center of square.
            
            //USING lm. here is not ideal, but its not going to be set anywhere outside of canvas, so ok. anythign that gets sent in telemetry needs
            //to be done with gs.
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
            
            //draw the flame
            g2d.translate(PxToDrawCenter, PyToDrawCenter);
            //TODO:Double check sign consistency with lem model
            
            g2d.rotate(-angleToDraw);
            g2d.drawImage(LemSprite, -(LEM_WIDTH/2), -(LEM_HEIGHT/2), null);

            if (flameSize > 1 ) {
                g2d.drawImage(FlameSprite.getScaledInstance(FlameSprite.getWidth(null), flameSize, Image.SCALE_SMOOTH), -(LEM_WIDTH/2), LemSprite.getHeight(null)-(LEM_HEIGHT/2), null);
            }

            g2d.rotate(angleToDraw);
            g2d.translate(-PxToDrawCenter, -PyToDrawCenter);


            
            //draw the lem
            //THis will be off, get the location first.
//            //TODO:Fix this, messing things up.
//            g2d.translate(PxToDrawCenter, PyToDrawCenter);
//            //TODO:Double check sign consistency with lem model
//            g2d.rotate(-angleToDraw);
//            g2d.rotate(angleToDraw);
//            g2d.translate(-PxToDrawCenter, -PyToDrawCenter);

            //TODO:this wont fully work, but idea is chaning value of LemShape with each image draw and get that 
            //coordinate system........wont work really. lets just try it.
            
            //TODO:draw the surface of teh ground however I do it.

            //THe collisions logic will be done entirely in Swing, since we just need to compare
            //2 boxes. 
            
            
            //draw the lunar surface (get these numbers form same place as lunar model to ensure consistency
            //maybe at to game state?
            
            //TODO: fix the ordering of what gets drawn

            
            //draw the surface
            Color Silver = new Color(220,220,220);
            g2d.setColor(Silver);            
            g2d.draw(lunarSurface);
            g2d.fill(lunarSurface);
            g2d.setColor(Color.BLACK);

            

            
            
//            g2d.drawLine((int)vertices.get(0).x, (int)vertices.get(0).y, (int)vertices.get(1).x, (int)vertices.get(1).y);
//            g2d.drawLine((int)vertices.get(1).x, (int)vertices.get(1).y, (int)vertices.get(2).x, (int)vertices.get(2).y);
//            g2d.drawLine((int)vertices.get(2).x, (int)vertices.get(2).y, (int)vertices.get(3).x, (int)vertices.get(3).y);
//            g2d.drawLine((int)vertices.get(3).x, (int)vertices.get(3).y, (int)vertices.get(4).x, (int)vertices.get(4).y);


//          for (int i = 0; i < vertices.size()-1; i++) {
////System.out.println(vertices.size());              
//g2d.drawLine((int)Math.round(vertices.get(i).x), (int)Math.round(vertices.get(i).y), 
//     (int)Math.round(vertices.get(i+1).x), (int)Math.round(vertices.get(i+1).y));
////
//          }

            
//            //            
//            while (verticesIterator.hasNext()) {
//                 v1 = verticesIterator.next();
//                 if (v2 != null) {
//                     g2d.drawLine((int)v2.x, (int)v2.y, (int)v1.x, (int)v1.y);
//
//                 }
//
//                if (verticesIterator.hasNext()) {
//                     v2 = verticesIterator.next();
//                    g2d.drawLine((int)v1.x, (int)v1.y, (int)v2.x, (int)v2.y);
//                    
//                }
////                
//                
//            }
            
//            
//            
//            g2d.drawLine(0, this.getHeight()-10, Canvas.this.getWidth()/2,Canvas.this.getHeight()-30);
//            g2d.drawLine(Canvas.this.getWidth()/2,Canvas.this.getHeight()-30, Canvas.this.getWidth()/2+10,Canvas.this.getHeight()-30);
//            g2d.drawLine(Canvas.this.getWidth()/2+10,Canvas.this.getHeight()-30, Canvas.this.getWidth(),Canvas.this.getHeight()-10);
//            //TODO:Create a shape from those lines, and fill that shape.
//            //g2d.fillRect(0,0,10000,10000);
//           // g.fillRect(lm.getPx(), 
//             //       lm.getPy(), 10, 10);
            
            
            
            
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
    

