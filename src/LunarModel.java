import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Distance;
import org.jbox2d.collision.Distance.DistanceProxy;
import org.jbox2d.collision.Distance.SimplexCache;
import org.jbox2d.collision.DistanceInput;
import org.jbox2d.collision.DistanceOutput;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
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
    private static final Vec2 INITIAL_V = new Vec2(10,10);
    private static final float INITIAL_Vw = 0;
    private static final Vec2 INITIAL_POSITION = new Vec2(30,30);
    //Gravity Vector
    //6f
    private static final Vec2 gravity = new Vec2(0, 6f);
    //Scale to convert physics world to pixel world
    private static final float SCALE = 1f;

    //TODO:
    //NEED TO SCALE THROTTLE AS WELL
    //LOTS OF SCALING MAGIC, but basically works.

    //TODO:THis is fudging something....really need to fix math
    public static final float THROT_SCALE = 250f;
    public static final int MAX_THROTTLE = 100;
    public static final int MIN_THROTTLE = 0;
    
    //initial throttle settting
    private float throttle = 0; 
    private boolean crashed = false;
    private boolean landed = false;


    //thruster TORQUE settings
    private static final int  TORQUE = 1000000;

    //TODO:another thing I need to change later
    private static final float DENSITY_OF_SHAPES = 1;
    protected static final float CRASH_VELOCITY_Y = 10000;
    protected static final float CRASH_VELOCITY_X = 10000;

    protected static final float CRASH_ANGLE = (float) (Math.PI/6)*1000;

    
    private Vec2 attitudeVec;
    
    private World w;
    private BodyDef lbd;
    private Body lem;
    private BodyDef sbd;
    private Body surface;
    //TODO: Can really make the defs local vars, not fields
    private float altitude;
    
    private Distance d = new Distance();
    private DistanceOutput DO = new DistanceOutput();
    private DistanceInput DI = new DistanceInput();

    private SimplexCache SC = new SimplexCache();
    PolygonShape lemS;
//    EdgeShape surfaceS;
//    EdgeShape surfaceS2;
//    EdgeShape surfaceS3;

    Distance.DistanceProxy flDp = new Distance.DistanceProxy();
    Distance.DistanceProxy sDp = new Distance.DistanceProxy();
    private float lastVy;
    private float lastVx;
    private float lastAngle;

    
    private boolean contactLight=false;
    
    LinkedList<EdgeShape> surfaceSList = new LinkedList<EdgeShape>();


   ContactListener crashDetector =  new ContactListener() {
       
        @Override
        public void beginContact(Contact contact) {
           Fixture c1 = contact.getFixtureA();
           Fixture c2 = contact.getFixtureB();
           
           contactLight = true;
           
           //Only have 2 fixtures to possibly contact....? TODO:Update this logic as needed
           if (lastVy > CRASH_VELOCITY_Y || Math.abs(lastAngle) > CRASH_ANGLE || lastVx > CRASH_VELOCITY_X) {
               crashed = true;
               landed = false;

           }
           else {
               landed = true;
               crashed = false;
           }
            
        }
        @Override
        public void endContact(Contact contact) {
            crashed = false;
            landed = false;
            contactLight = false;


            
        }
        @Override
        public void postSolve(Contact arg0, ContactImpulse arg1) {
            // TODO Auto-generated method stub
            
        }
        @Override
        public void preSolve(Contact arg0, Manifold arg1) {
            // TODO Auto-generated method stub
            
        }
    };
    
    
    private float canvasWidth;
    private float canvasHeight;
    public void setBounds(float canvasWidth, float CanvasHeight) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = CanvasHeight;

    }
    
    private LinkedList<Vec2> vertices = new LinkedList<Vec2>();

    public LunarModel() {
        
        
        vertices.add(new Vec2(0, (Canvas.CANVAS_HEIGHT-10)));
        vertices.add(new Vec2(30, Canvas.CANVAS_HEIGHT-10));
        vertices.add(new Vec2(30+30, (Canvas.CANVAS_HEIGHT-10-20)));
        vertices.add(new Vec2(30+30+20, (Canvas.CANVAS_HEIGHT-10-20)));
        vertices.add(new Vec2((30+30+20+50), (Canvas.CANVAS_HEIGHT-10-20+20)));
        vertices.add(new Vec2(30+30+20+50+30, (Canvas.CANVAS_HEIGHT-10-20+20)));
        vertices.add(new Vec2(30+30+20+50+30+60, (Canvas.CANVAS_HEIGHT-10-20)));
        vertices.add(new Vec2(30+30+20+50+30+60+20, (Canvas.CANVAS_HEIGHT-10-20+20)));
        vertices.add(new Vec2(30+30+20+50+30+60+20+40, (Canvas.CANVAS_HEIGHT-10-20+20-40)));
        vertices.add(new Vec2(30+30+20+50+30+60+20+40+60, (Canvas.CANVAS_HEIGHT-10-20+20-40+20)));
        vertices.add(new Vec2(30+30+20+50+30+60+20+40+60+30, (Canvas.CANVAS_HEIGHT-10-20+20-40+20+20)));

        
        
        
                
        w = new World(gravity);
        lbd = new BodyDef();
        
        lbd.setPosition(INITIAL_POSITION);
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
        
        
        System.out.println(sbd.getPosition());
        //This is static
        
        surface = w.createBody(sbd);

        //Create shapes and fixtures for collision purposes
        
        
        //TODO:Could add more fixtures to the lem body so it collides better with the surface
        //and add bumps to surface
        
        //TODO:Change staic type to more general???
        lemS = new PolygonShape();
        //TODO:Chaneg shapes to work well
        lemS.setAsBox(Canvas.LEM_WIDTH, Canvas.LEM_HEIGHT);
        //TODO:Fix figure out dimensions based on constants
        
        lem.createFixture(lemS, DENSITY_OF_SHAPES);
        //again....idk why
        //lem.m_invI = 1.0f;
//        System.out.println(lem.getMass());

//        lem.createFixture(lemS, DENSITY_OF_SHAPES);
        //again....idk why
        //lem.m_invI = 1.0f;
//        System.out.println(lem.getMass());

//        System.out.println("done");
        
        //TODO:FIx out of bounds
        

        
        for (int i =0; i<vertices.size()-1; i++) {
            
            EdgeShape surfaceS = new EdgeShape();
            surfaceS.set(vertices.get(i), vertices.get(i+1));
            surfaceSList.add(surfaceS);
        }
        
//        surfaceS = new EdgeShape(); 
//        surfaceS.set(vertices.get(0), vertices.get(1));
//        
//        surfaceS2 = new EdgeShape(); 
//        surfaceS2.set(vertices.get(1), vertices.get(2));
//
//        
//        surfaceS3 = new EdgeShape(); 
//        surfaceS3.set(vertices.get(2), vertices.get(3));
//
//        EdgeShape surfaceS4 = new EdgeShape(); 
//        surfaceS4.set(vertices.get(3), vertices.get(4));
//
//        EdgeShape surfaceS5 = new EdgeShape(); 
//        surfaceS5.set(vertices.get(4), vertices.get(5));

        for (EdgeShape surfaceS : surfaceSList )
        {
            FixtureDef sfd = new FixtureDef();
            sfd.setShape(surfaceS);

            surface.createFixture(sfd);
            
            //surface.createFixture(surfaceS, DENSITY_OF_SHAPES);
        }

        
        
        
        
        
//       flDp = new Distance.DistanceProxy();

//        SC.count = 0;
       
        
        //TODO:consider radii??
        

        

        
        //TODO:AABB & ground body 
//        System.out.println("done");
        
        //setting distances logic up

        
        w.setContactListener(crashDetector);

        
       // DO.iterations = 10;

        

    }
    
    public void reset() {
                //TODO:Make sure this is consistent!
        //reset any fields not dependent on world
        throttle = 0;
        crashed = false;
        landed = false;
        
        //create new world, copied form constructor
        w = new World(gravity);
        lbd = new BodyDef();
        
        lbd.setPosition(INITIAL_POSITION);
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

        //SETTING THE BODY POSITION TO THE BOTTON LEFT jsut to test distance
        
        
        //This is static
        surface = w.createBody(sbd);
        

        //Create shapes and fixtures for collision purposes
        
        
        //TODO:Could add more fixtures to the lem body so it collides better with the surface
        //and add bumps to surface
        
        //TODO:Change staic type to more general???
        PolygonShape lemS = new PolygonShape();
        //TODO:Chaneg shapes to work well
        lemS.setAsBox(Canvas.LEM_WIDTH, Canvas.LEM_HEIGHT);
        
        //TODO:Fix figure out dimensions based on constants

        EdgeShape surfaceS = new EdgeShape(); 
        surfaceS.set(new Vec2(0, Canvas.CANVAS_HEIGHT-10), new Vec2(Canvas.CANVAS_WIDTH/2,Canvas.CANVAS_HEIGHT-30));
        
        EdgeShape surfaceS2 = new EdgeShape(); 
        surfaceS2.set(new Vec2(Canvas.CANVAS_WIDTH/2,Canvas.CANVAS_HEIGHT-30), new Vec2(Canvas.CANVAS_WIDTH/2+10,Canvas.CANVAS_HEIGHT-30));

        
        EdgeShape surfaceS3 = new EdgeShape(); 
        surfaceS3.set(new Vec2(Canvas.CANVAS_WIDTH/2+10,Canvas.CANVAS_HEIGHT-30), new Vec2(Canvas.CANVAS_WIDTH,Canvas.CANVAS_HEIGHT-10));

        lem.createFixture(lemS, DENSITY_OF_SHAPES);
        //again....idk why
        //lem.m_invI = 1.0f;
//        System.out.println(lem.getMass());
        surface.createFixture(surfaceS, DENSITY_OF_SHAPES);
        surface.createFixture(surfaceS2, DENSITY_OF_SHAPES);
        surface.createFixture(surfaceS3, DENSITY_OF_SHAPES);

//      
     w.setContactListener(crashDetector);


        
                
    }
    
    //use field to "delegate" force application to move, so it applies each step.
    //TODO:Keep an eye o n this with erors.
    
public void throttle(int throt) {
        
        if (throt > MAX_THROTTLE) {
            throttle = MAX_THROTTLE*THROT_SCALE;
            
        }
        else if (throt < MIN_THROTTLE) {
            throttle = MIN_THROTTLE*THROT_SCALE;
            
        }
        else {
            
            throttle = (throt*THROT_SCALE);
        }
    }
    
    public void thrustL() {
        lem.applyTorque(TORQUE);

    }
    public void thrustR() {
        lem.applyTorque(-TORQUE);
        
    }
    
    
    public static float JUMP_ANGLE =  (float) Math.PI/24;
    public void jumpL() {
        
        
            lem.setTransform(lem.getPosition(), lem.getAngle()+JUMP_ANGLE);
        //lem.applyTorque((TORQUE));
        //thrusted++;
        //lem.applyAngularImpulse((-TORQUE));


    }
    public void jumpR() {
        lem.setTransform(lem.getPosition(), lem.getAngle()-JUMP_ANGLE);

        
    }

    
    
    public boolean getContactLight() {
        return contactLight;
    }
    
    
    //TODO:Check TORQUE signs
    //TODO:Make hard and easy modes for each control ,adn a kill swith to recenter everything.
    //TODO: DO unit tests, but really need to get display setup better to test myself and fine tune settings.
    
    public int getPx() {
        return Math.round(SCALE*lem.getPosition().x);
        
    }
    public int getPy() {
        return Math.round(SCALE*lem.getPosition().y);
        
    }
    public int getAttX() {
        return Math.round(SCALE*attitudeVec.x);
        
    }
    public int getAttY() {
        return Math.round(SCALE*attitudeVec.y);
        
    }
    public float getAngle() {
        return lem.getAngle();
        
    }
    
    public int getThrottle() {
        //Return it unscaled
        //TODO:Should just get original throttle and have 2 fields
        return Math.round(throttle/THROT_SCALE);
    }
    //For TEsting
    public float getVy() {
        return SCALE*lem.getLinearVelocity().y;
    }
    
    public float getAltitude() {
        return SCALE*altitude;
    }
    
    public LinkedList<Vec2> getSurfaceVertices() {
        return new LinkedList<Vec2>(vertices);
    }
    
    public void move() {
       
        //TODO: this is not a real distance...handle here or in the canvas with constants?
//        System.out.println(surface.getPosition());
//        altitude = surface.getPosition().sub(lem.getPosition()).length();
        //Fixture fl = lem.getFixtureList();
        
        
        //TODO: THis all sucks; leave physics and drawing to the end.
        
        //TODO:I think the issue is that lemS does not have dynamic postion data....
     // we need to get the body's position, let's use a Vector2 to store it.

        

        
//        
//        vertices.add(new Vec2(canvasWidth/2,(canvasHeight-30)));
//        vertices.add(new Vec2((canvasWidth/2+10),(canvasHeight-30)));
//        vertices.add(new Vec2(canvasWidth,(canvasHeight-10)));

        
        
        //Now that I do not need to worry about resizable, could actually move this out. BUt ok.
       
        /*
        surface.createFixture(surfaceS, DENSITY_OF_SHAPES);
        surface.createFixture(surfaceS2, DENSITY_OF_SHAPES);
        surface.createFixture(surfaceS3, DENSITY_OF_SHAPES);      
        surface.createFixture(surfaceS4, DENSITY_OF_SHAPES);      
        surface.createFixture(surfaceS5, DENSITY_OF_SHAPES);      

        */
        
        
        //lemS.setAsBox(Canvas.LEM_WIDTH, Canvas.LEM_HEIGHT);
        
        //flDp.set(lemS, 0);

        for(int i=0; i<vertices.size()-1; i++) {
            //TODO:Set this if to be actually based on ceter position
            if (lem.getPosition().x > vertices.get(i).x && lem.getPosition().x <= vertices.get(i+1).x) {
                sDp.set(surfaceSList.get(i),0);

            }

        }
        
        /*
        if (lem.getPosition().x > vertices.get(0).x && lem.getPosition().x <= vertices.get(1).x) {
            sDp.set(surfaceS,1);

        }
        else if (lem.getPosition().x > vertices.get(1).x && lem.getPosition().x <= vertices.get(2).x) {
            sDp.set(surfaceS2,1);

        }
        else if (lem.getPosition().x > vertices.get(2).x && lem.getPosition().x <= vertices.get(3).x) {
            sDp.set(surfaceS3,1);

        }*/

        
//        sDp.set(surfaceS, 0);
//        sDp.set(surfaceS2,0);
//        sDp.set(surfaceS3,2);
       
        //TODO:Still working on fixing altitude/crashing (AnD do fuel) but getting there
        //TODO:DO not use reset button for now
        DI.proxyA = flDp;
        DI.proxyB = sDp;
        DI.transformA = lem.getTransform();
        DI.transformB = surface.getTransform();

        d.distance(DO, SC, DI);

        //System.out.println(DO.distance);
        //altitude = Canvas.CANVAS_HEIGHT-10 - (lem.getPosition().y+lemS.getRadius());
        //System.out.println(DO.distance);
        altitude = DO.distance;
        
        attitudeVec = new Vec2((float)Math.sin(lem.getAngle()), (float)Math.cos(lem.getAngle()));

        
        //TODO:put this here or above?
        lem.applyForceToCenter(attitudeVec.mul(throttle).negate());
        //System.out.println(throttle);
        //TODO:Maybe this can somehow fix the CPU util
        lastVy = lem.getLinearVelocity().y;
        lastVx = lem.getLinearVelocity().x;

        lastAngle = lem.getAngle();
        
        w.step(1/60f, 1, 1);
        w.clearForces();
        //cal new attitude vec for next throttle application

    }
    
    //TODO: Change this logic to maybe be a list, or soemthing to work nicely with returning all the vertices
//    public Vec2 getSurfaceStart() {
//        surfaceS
//    }
    

    //Surface and collisions Logic:
    
    //TODO: Created surface above
    
    public boolean isCrashed() {
        return crashed;
        //Looked at tutorial https://www.programcreek.com/java-api-examples/index.php?api=org.jbox2d.callbacks.ContactListener
        
        //TODO:What to do when the lander goes off screen, and then doesnt have surface to collide into
        
        
    }
  
    public boolean isLanded() {
        return landed;
        //Looked at tutorial https://www.programcreek.com/java-api-examples/index.php?api=org.jbox2d.callbacks.ContactListener
        
        //TODO:What to do when the lander goes off screen, and then doesnt have surface to collide into
        
        
    }


    public float getVx() {
        // TODO Auto-generated method stub
        return SCALE*lem.getLinearVelocity().x;
    }
    
    public float getVw() {
        // TODO Auto-generated method stub
        //System.out.println(lem.getAngularVelocity());
        return SCALE*lem.getAngularVelocity();
    }
    
    

    
}
