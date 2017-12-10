package game;
/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Path2D;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

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

    private static Image LemSprite;

    private static Image FlameSprite;
    public boolean playing = false; // whether the game is running

    // not private because I need coutner to work in reset, and in the keypress listerner class
    private int i = 0;

    private final Timer timer;
    // create the physics world
    // TODO:Do I need to pass this anything else?
    // Important to be final bc I'm passing it around to the error classes
    private LunarModel lm;

    private final GameState gs;
    private final TelemetryPanel telemetryPanel;

    // private field for the shape that is the surface, so I can use it AND draw it
    // Leave the ability to change the path depending on the game---but do that later. would set in
    // reset area.
    private final Path2D surface;
    private int k;

    private final LinkedList<Vec2> vertices;
    private final Path2D lunarSurface;

    private boolean firstExit = true;

    // TODO: change the status thing to just be a field of gs
    // Jpanel for display of info from model displayed in canvas
    public Canvas(GameState gs, TelemetryPanel tp) {
        // Copied from reset.

        // Make sure that this component has the keyboard focus

        // TODO:this means I cna remove all fixture logic from the model--just use the body.
        surface = new Path2D.Float();
        // TOtally wrong, just to see
        surface.moveTo(0, CANVAS_HEIGHT - 10);

        /// TODO : make variable or something
        surface.lineTo(CANVAS_WIDTH - 10, CANVAS_WIDTH - 10);
        this.lm = new LunarModel();

        vertices = lm.getSurfaceVertices();
        lunarSurface = new Path2D.Float();

        lunarSurface.moveTo(vertices.get(0).x, vertices.get(0).y);
        for (int i = 1; i < vertices.size(); i++) {
            lunarSurface.lineTo(vertices.get(i).x, vertices.get(i).y);
        }
        // close the curve
        lunarSurface.lineTo(CANVAS_WIDTH, CANVAS_HEIGHT);
        lunarSurface.lineTo(0, CANVAS_HEIGHT);
        lunarSurface.lineTo(vertices.get(0).x, vertices.get(0).y);

        try {
            LemSprite = ImageIO.read(new File("Files/LEM.png")).getScaledInstance(LEM_WIDTH,
                    LEM_HEIGHT, Image.SCALE_SMOOTH);
            FlameSprite = ImageIO.read(new File("Files/Flame.png")).getScaledInstance(20, 20,
                    Image.SCALE_SMOOTH);
        } catch (final IOException e1) {
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
            @Override
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
        k = 0;
        playing = true;
        // this.gs.reset();
        this.requestFocusInWindow();

        final KeyAdapter easyKeyAdapter = new KeyAdapter() {
            // TODO: Change j and i to +/- constants so that can change rate of throttle increase
            final public static int FUEL_INCREMENT_THRUSTER_JUMP_SCALE = 3;// use 3 times as much
                                                                           // fuel
            int temp;
            boolean first = true;

            @Override
            public void keyPressed(KeyEvent e) {

                // Full throttle momentarily
                if (e.getKeyCode() == KeyEvent.VK_UP) {

                    // store current throttle
                    if (first) {
                        temp = lm.getThrottle();
                        first = false;
                    }
                    lm.throttle(LunarModel.MAX_THROTTLE, gs.getHasFuel());

                }

                // TODO:holding down key doesnt work?
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {

                    if (gs.getVw() != 0) {
                        gs.setFuel(Math.max(gs.getFuel() - NULL_FORCES_PENALTY, 0));

                    }
                    lm.nullAngularForces(gs.getHasFuel());

                    lm.jumpL(gs.getHasFuel());
                    gs.setFuel(Math.max(gs.getFuel()
                            - (FUEL_INCREMENT_THRUSTER * FUEL_INCREMENT_THRUSTER_JUMP_SCALE), 0));

                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

                    if (gs.getVw() != 0) {
                        gs.setFuel(Math.max(gs.getFuel() - NULL_FORCES_PENALTY, 0));

                    }
                    lm.nullAngularForces(gs.getHasFuel());

                    lm.jumpR(gs.getHasFuel());
                    gs.setFuel(Math.max(gs.getFuel()
                            - (FUEL_INCREMENT_THRUSTER * FUEL_INCREMENT_THRUSTER_JUMP_SCALE), 0));

                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_UP) {

                    lm.throttle(temp, gs.getHasFuel());
                    first = true;

                }

            }
        };

        final KeyAdapter hardKeyAdapter = new KeyAdapter() {
            // TODO: Change j and i to +/- constants so that can change rate of throttle increase
            final public static int FUEL_INCREMENT_THRUSTER_JUMP_SCALE = 3;// use 3 times as much
                                                                           // fuel
            int temp;
            boolean first = true;

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {

                    // j=0;
                    i += THROTTLE_JUMP;
                    temp = lm.getThrottle();
                    lm.throttle(i, gs.getHasFuel());

                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {

                    // i=0;
                    i -= THROTTLE_JUMP;
                    temp = lm.getThrottle();
                    // TODO:change behavior for when F is held and then down is pressed and F is
                    // released
                    lm.throttle(i, gs.getHasFuel());

                }

                /// TODO:For readme, the reason gs is so abstracted from lm--ie why not just use
                /// lm.getVx becuase I thought i might need ot have
                // the abstaction, but as it turned out anyone that needed acesss to gs had access
                /// to lem so it just got confugisojns
                if (e.getKeyCode() == KeyEvent.VK_N) {
                    if (gs.getVw() != 0) {
                        gs.setFuel(Math.max(gs.getFuel() - NULL_FORCES_PENALTY, 0));

                    }
                    lm.nullAngularForces(gs.getHasFuel());

                }

                // Full throttle momentarily
                if (e.getKeyCode() == KeyEvent.VK_F) {

                    // store current throttle
                    if (first) {
                        temp = lm.getThrottle();
                        first = false;
                    }
                    lm.throttle(LunarModel.MAX_THROTTLE, gs.getHasFuel());

                }

                // kill Engine
                if (e.getKeyCode() == KeyEvent.VK_K) {

                    // store current throttle
                    lm.throttle(LunarModel.MIN_THROTTLE, gs.getHasFuel());
                    temp = 0;
                    i = 0;

                }

                if (e.getKeyCode() == KeyEvent.VK_LEFT) {

                    lm.thrustL(gs.getHasFuel());
                    gs.setFuel(Math.max(gs.getFuel() - FUEL_INCREMENT_THRUSTER, 0));

                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

                    lm.thrustR(gs.getHasFuel());
                    gs.setFuel(Math.max(gs.getFuel() - FUEL_INCREMENT_THRUSTER, 0));

                }

                if (e.getKeyCode() == KeyEvent.VK_G) {

                    lm.jumpR(gs.getHasFuel());
                    gs.setFuel(Math.max(gs.getFuel()
                            - (FUEL_INCREMENT_THRUSTER * FUEL_INCREMENT_THRUSTER_JUMP_SCALE), 0));

                }

                if (e.getKeyCode() == KeyEvent.VK_D) {

                    lm.jumpL(gs.getHasFuel());
                    gs.setFuel(Math.max(gs.getFuel()
                            - (FUEL_INCREMENT_THRUSTER * FUEL_INCREMENT_THRUSTER_JUMP_SCALE), 0));

                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_F) {

                    lm.throttle(temp, gs.getHasFuel());
                    first = true;

                }

            }

        };
        if (gs.getIsEasy()) {
            addKeyListener(easyKeyAdapter);

        } else {
            addKeyListener(hardKeyAdapter);
        }

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    @Override
    public void paintComponent(Graphics g) {
        final Graphics2D g2d = ((Graphics2D) g);
        super.paintComponent(g);

        final Color Navy = new Color(25, 25, 112);
        this.setBackground(Navy);

        g.setColor(Color.RED);
        g.drawRect(lm.getPx() + (LEM_WIDTH / 2), lm.getPy() + (LEM_HEIGHT / 2), 2, 2);
        g.setColor(Color.BLACK);

        final int PxToDrawCenter = lm.getPx() + (LEM_WIDTH / 2);
        final int PyToDrawCenter = lm.getPy() + (LEM_HEIGHT / 2);
        final float angleToDraw = lm.getAngle();
        // * max in flame scale
        final float flameSizeMin = 1f;
        final float flameSizeMax = 20f;
        final int flameSize = Math.round(
                (((flameSizeMax - flameSizeMin) * (lm.getThrottle() - LunarModel.MIN_THROTTLE))
                        / (LunarModel.MAX_THROTTLE - LunarModel.MIN_THROTTLE)) + flameSizeMin);

        // draw the flame
        g2d.translate(PxToDrawCenter, PyToDrawCenter);

        g2d.rotate(-angleToDraw);
        g2d.drawImage(LemSprite, -(LEM_WIDTH / 2), -(LEM_HEIGHT / 2), null);

        if (flameSize > 1) {
            g2d.drawImage(
                    FlameSprite.getScaledInstance(FlameSprite.getWidth(null), flameSize,
                            Image.SCALE_SMOOTH),
                    -(LEM_WIDTH / 2), LemSprite.getHeight(null) - (LEM_HEIGHT / 2), null);
        }

        g2d.rotate(angleToDraw);
        g2d.translate(-PxToDrawCenter, -PyToDrawCenter);

        // draw the surface
        final Color Silver = new Color(220, 220, 220);
        g2d.setColor(Silver);
        g2d.draw(lunarSurface);
        g2d.fill(lunarSurface);
        g2d.setColor(Color.BLACK);

    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {

        // TODO:Reset
        /*
         * square = new Square(CANVAS_WIDTH, CANVAS_HEIGHT, Color.BLACK); poison = new
         * Poison(CANVAS_WIDTH, CANVAS_HEIGHT); snitch = new Circle(CANVAS_WIDTH, CANVAS_HEIGHT,
         * Color.YELLOW);
         */

        // OR just make a new one each time...this looks cleaner to me
        lm = new LunarModel();
        gs.reset();
        // CANNOT DO THIS BC GS IS BEING PASSED IN! 1 per game bc everything is linked through game
        // stategs = new GameState();
        timer.start();

        i = 0;
        k = 0;
        playing = true;
        firstExit = true;

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor triggers.
     */
    // TODO:I made this private.....
    private void tick() {
        if (playing) {

            repaint();// Need to paint before checking if crashed....
            //

            gs.doErrors(this, lm, gs);

            telemetryPanel.updateTelemetryPanel();

            gs.setVx(lm.getVx());
            gs.setVy(lm.getVy());
            gs.setVw(lm.getVw());
            gs.setAngle(lm.getAngle());
            gs.setAltitude(lm.getAltitude());
            gs.setContactLight(lm.getContactLight());
            gs.setFuel(Math.max(gs.getFuel() - (FUEL_INCREMENT * lm.getThrottle()), 0));

            if (((lm.getPx() < 0) || (lm.getPx() > CANVAS_WIDTH) || (lm.getPy() < 0))
                    && firstExit) {

                JOptionPane.showMessageDialog(null,
                        "You're leaving the target landing area! Be careful!");
                firstExit = false;
            }
            if ((lm.getPy() > 0) && (lm.getPx() > 0) && (lm.getPx() < CANVAS_WIDTH)) {
                firstExit = true;
            }

            lm.move();
            if (lm.isCrashed()) {

                JOptionPane.showMessageDialog(null, "Oh no! You crashed an expensive lander!");
                timer.stop();
            }
            if (gs.getFuel() < 1) {
                lm.throttle(0, true);
                gs.setHasFuel(false);

            }
            // k = time to win
            if (lm.isLanded()) {
                k++;
            } else {
                k = 0;
            }
            if (lm.isLanded() && (k > LANDED_DURATION)) {

                JOptionPane.showMessageDialog(null, "You landed!");
                timer.stop();

            }

        }
    }

}
