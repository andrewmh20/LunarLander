import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;

public class LunarModel {

    /**Class to hold the physics state of the Lunar Lander and provide methods for getting
     * the objects position, which will be used in the Swing Drawing classes
     */

    //Initial velocity settings
    private static final Vec2 INITIAL_V = new Vec2(0,0);
    private static final float INITIAL_Vw = 0;
    //Gravity Vector
    private static final Vec2 gravity = new Vec2(0, 9.8f);


    private static final int MAX_THROTTLE = 100;
    private static final int MIN_THROTTLE = 0;
    //thruster torque settings
    private static final int  torque = 10;

    
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
    }
    
public void throttle(int throt) {
        
        float angle = b.getAngle();
        
        Vec2 attitudeVec = new Vec2((float)Math.cos(angle), (float)Math.sin(angle));
        
        if (throt > MAX_THROTTLE) {
            b.applyForceToCenter(attitudeVec.mul(MAX_THROTTLE).negate());
            
        }
        else if (throt < MIN_THROTTLE) {
            b.applyForceToCenter(attitudeVec.mul(MIN_THROTTLE).negate());
            
        }
        else b.applyForceToCenter(attitudeVec.mul(throt).negate());
    }
    
    public void thrustL() {
        b.applyTorque(-torque);
    }
    public void thrustR() {
        b.applyTorque(torque);
        
    }
    
    public int getX() {
        return Math.round(b.getPosition().x);
        
    }
    public int getY() {
        return Math.round(b.getPosition().y);
        
    }
    
}
