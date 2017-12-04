import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;

public class LunarModel {

    //TODO:Used iforce2d.com
    /**Class to hold the physics state of the Lunar Lander and provide methods for getting
     * the objects position, which will be used in the Swing Drawing classes
     AS WELL AS OTHER OBJECTS IN THE LUNAR AREA
     */

    //Initial velocity settings
    private static final Vec2 INITIAL_V = new Vec2(0,0);
    private static final float INITIAL_Vw = 0;
    //Gravity Vector
    private static final Vec2 gravity = new Vec2(0, 4.6f);
    //Scale to convert physics world to pixel world
    private static final float SCALE = 1f;

    //TODO:
    //NEED TO SCALE THROTTLE AS WELL
    //LOTS OF SCALING MAGIC, but basically works.

    //TODO:THis is fudging something....really need to fix math
    private static final float THROT_SCALE = 1000f;
    private static final int MAX_THROTTLE = 100;
    private static final int MIN_THROTTLE = 0;
    //thruster torque settings
    private static final int  torque = 100000;

    //TODO:another thing I need to change later
    private static final float DENSITY_OF_SHAPES = 1;
    
    private Vec2 attitudeVec;
    
    private World w;
    private BodyDef lbd;
    private Body lem;
    private BodyDef sbd;
    private Body surface;
    //TODO: Can really make the defs local vars, not fields
    
    public LunarModel() {
        w = new World(gravity);
        lbd = new BodyDef();
        
        lbd.setLinearVelocity(INITIAL_V);
        lbd.setAngularVelocity(INITIAL_Vw);
        lbd.setType(BodyType.DYNAMIC);
       
        //The actual lander physics body
        lem = w.createBody(lbd);
        
        //HACK TO FIX ROATION TODO: AND/OR Make variable, again issue with SCALE
        lem.m_invI = 1;
        
        //TODO:Rememver to reset anytime I might change tourque!
        attitudeVec = new Vec2((float)Math.sin(lem.getAngle()), (float)Math.cos(lem.getAngle()));
        
        
        //Create the lunar surface
        BodyDef sbd = new BodyDef();
        //This is static
        surface = w.createBody(sbd);
        
        
        //Create shapes and fixtures for collision purposes
        
        
        //TODO:Could add more fixtures to the lem body so it collides better with the surface
        //and add bumps to surface
        
        //TODO:Change staic type to more general???
        PolygonShape lemS = new PolygonShape();
        //TODO:Chaneg shapes to work well
        lemS.setAsBox(10, 10);
        EdgeShape surfaceS = new EdgeShape(); 
        //TODO:Fix figure out dimensions based on constants
        surfaceS.set(new Vec2(0, Canvas.CANVAS_HEIGHT-10), new Vec2(Canvas.CANVAS_WIDTH,Canvas.CANVAS_HEIGHT));
        
        lem.createFixture(lemS, DENSITY_OF_SHAPES);
        //again....idk why
        //lem.m_invI = 1.0f;
//        System.out.println(lem.getMass());
        surface.createFixture(surfaceS, DENSITY_OF_SHAPES);
//        
//        System.out.println("done");
        
    }
    
    //use fieled to "delegate" force application to move, so it applies each step.
    
    private Vec2 throttle = new Vec2(0,0); 
    
public void throttle(int throt) {
        
        if (throt > MAX_THROTTLE) {
            throttle = attitudeVec.mul(MAX_THROTTLE*THROT_SCALE).negate();
            
        }
        else if (throt < MIN_THROTTLE) {
            throttle = attitudeVec.mul(MIN_THROTTLE*THROT_SCALE).negate();
            
        }
        else {
            
            throttle = attitudeVec.mul(throt*THROT_SCALE).negate();
        }
    }
    
    public void thrustL() {
        lem.applyTorque(-torque);
        attitudeVec = new Vec2((float)Math.sin(lem.getAngle()), (float)Math.cos(lem.getAngle()));
        System.out.println(lem.getAngle());
        //System.out.println(lem.getInertia());

    }
    public void thrustR() {
        lem.applyTorque(torque);
        attitudeVec = new Vec2((float)Math.sin(lem.getAngle()), (float)Math.cos(lem.getAngle()));
        System.out.println(lem.getAngle());

        
    }
    
    public int getPx() {
        return Math.round(SCALE*lem.getPosition().x)+5;
        
    }
    public int getPy() {
        return Math.round(SCALE*lem.getPosition().y)+5;
        
    }
    public int getAttX() {
        return Math.round(SCALE*attitudeVec.x);
        
    }
    public int getAttY() {
        return Math.round(SCALE*attitudeVec.y);
        
    }
    public float getAngle() {
        return Math.round(lem.getAngle());
        
    }
    //For TEsting
    public float getVy() {
        return SCALE*lem.getLinearVelocity().y;
    }
    
    public void move() {
       
        lem.applyForceToCenter(throttle);
        w.step(1/60f, 5, 5);
        w.clearForces();
    }
    
    //TODO: Change this logic to maybe be a list, or soemthing to work nicely with returning all the vertices
//    public Vec2 getSurfaceStart() {
//        surfaceS
//    }
    

    //Surface and collisions Logic:
    
    //TODO: Created surface above
    
    boolean collided = false;
    public boolean isCollided() {
        //Looked at tutorial https://www.programcreek.com/java-api-examples/index.php?api=org.jbox2d.callbacks.ContactListener
        
        
        w.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {
               Fixture c1 = contact.getFixtureA();
               Fixture c2 = contact.getFixtureB();
               //Only have 2 fixtures to possibly contact....? TODO:Update this logic as needed
               collided = true;
                
            }
            @Override
            public void endContact(Contact contact) {
                // TODO Auto-generated method stub
                
            }
            @Override
            public void postSolve(Contact arg0, ContactImpulse arg1) {
                // TODO Auto-generated method stub
                
            }
            @Override
            public void preSolve(Contact arg0, Manifold arg1) {
                // TODO Auto-generated method stub
                
            }
        }
                );
        return collided;
        
    }
    
    

    
}