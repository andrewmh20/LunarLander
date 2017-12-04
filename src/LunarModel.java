import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;

public class LunarModel {

    /**Class to hold the physics state of the Lunar Lander and provide methods for getting
     * the objects position, which will be used in the Swing Drawing classes
     AS WELL AS OTHER OBJECTS IN THE LUNAR AREA
     */

    //Initial velocity settings
    private static final Vec2 INITIAL_V = new Vec2(0,0);
    private static final float INITIAL_Vw = 0;
    //Gravity Vector
    private static final Vec2 gravity = new Vec2(0, 3.6f);
    //Scale to convert physics world to pixel world
    private static final float SCALE = 1f;

    //TODO:
    //NEED TO SCALE THROTTLE AS WELL
    //LOTS OF SCALING MAGIC, but basically works.

    private static final float THROT_SCALE = 1f;
    private static final int MAX_THROTTLE = 100;
    private static final int MIN_THROTTLE = 0;
    //thruster torque settings
    private static final int  torque = 10;

    
    private Vec2 attitudeVec;
    
    private World w;
    private BodyDef bd;
    private Body b;
    
    public LunarModel() {
        w = new World(gravity);
        bd = new BodyDef();
        
        bd.setLinearVelocity(INITIAL_V);
        bd.setAngularVelocity(INITIAL_Vw);
        bd.setType(BodyType.DYNAMIC);
        
        //The actual lander physics body
        b = w.createBody(bd);
        
        //HACK TO FIX ROATION TODO: AND/OR Make variable, again issue with SCALE
        b.m_invI = 1;
        
        //TODO:Rememver to reset anytime I might change tourque!
        attitudeVec = new Vec2((float)Math.sin(b.getAngle()), (float)Math.cos(b.getAngle()));
    }
    
    //TODO:FOR NOW JUST APPLIES FORCE ONCE
public void throttle(int throt) {
        
        if (throt > MAX_THROTTLE) {
            b.applyForceToCenter(attitudeVec.mul(MAX_THROTTLE*THROT_SCALE).negate());
            
        }
        else if (throt < MIN_THROTTLE) {
            b.applyForceToCenter(attitudeVec.mul(MIN_THROTTLE*THROT_SCALE).negate());
            
        }
        else {
            b.applyForceToCenter(attitudeVec.mul(throt*THROT_SCALE).negate());
        }
    }
    
    public void thrustL() {
        b.applyTorque(-torque);
        attitudeVec = new Vec2((float)Math.sin(b.getAngle()), (float)Math.cos(b.getAngle()));
        System.out.println(b.getAngle());
        //System.out.println(b.getInertia());

    }
    public void thrustR() {
        b.applyTorque(torque);
        attitudeVec = new Vec2((float)Math.sin(b.getAngle()), (float)Math.cos(b.getAngle()));
        System.out.println(b.getAngle());

        
    }
    
    public int getPx() {
        return Math.round(SCALE*b.getPosition().x);
        
    }
    public int getPy() {
        return Math.round(SCALE*b.getPosition().y);
        
    }
    public int getAttX() {
        return Math.round(SCALE*attitudeVec.x);
        
    }
    public int getAttY() {
        return Math.round(SCALE*attitudeVec.y);
        
    }
    //For TEsting
    public float getVy() {
        return SCALE*b.getLinearVelocity().y;
    }
    
    public void move() {
        
        w.step(1/60f, 5, 5);
        w.clearForces();
    }
    public void reset() {
        w = new World(gravity);
        bd = new BodyDef();
        
        bd.setLinearVelocity(INITIAL_V);
        bd.setAngularVelocity(INITIAL_Vw);
        bd.setType(BodyType.DYNAMIC);
        
        //The actual lander physics body
        b = w.createBody(bd);        
    }
    
}
