import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.junit.Test;
import static org.junit.Assert.*;

public class LanderTests {

    @Test
    public void testWorldCreation() {
        World w = new World(new Vec2(0,0));
        Lander bd = new Lander();
        bd.setType(BodyType.KINEMATIC);
        bd.setAngle(0);
        //bd.setAngularVelocity((float) (2*Math.PI));
        Body b = w.createBody(bd); 
        
        //bd is the definition, ie initial values for each Lander, and then 
        //can directly update b afterwards


//        System.out.println(b.getAngularVelocity());
       // System.out.println(b.getPosition());

        b.setAngularVelocity((float) (Math.PI));
        //b.applyLinearImpulse(new Vec2(0,5f), new Vec2(0,0), true);
        b.setLinearVelocity(new Vec2(0,1f));
        //b.applyForceToCenter(new Vec2(0,5f));
        b.getPosition();
        System.out.println(b.getMass());
        System.out.println(w.isContinuousPhysics());
        //System.out.println(b.getAngularVelocity());
        System.out.println(b.getAngle());
        System.out.println(b.getPosition());
        System.out.println(b.getLinearVelocity());
        w.step(1f, 10, 10);
        w.step(1f, 10, 10);
        w.step(1f, 10, 10);
        w.step(1f, 10, 10);
        w.step(1f, 10, 10);


        //b.applyForceToCenter(new Vec2(0,5f));
        System.out.println(b.getAngle());
        System.out.println(b.getPosition());

        /*
        float Vw = bd.getAngularVelocity();
        Vec2 v = bd.getLinearVelocity();
        System.out.println(bd.isActive());
        System.out.println(bd.getPosition().toString());
        System.out.println(Vw);
        System.out.println(bd.getAngle());
        w.step(10, 100, 1000);
        System.out.println(bd.getAngle());
        System.out.println(bd.getType());
        System.out.println(bd.getPosition().toString());
        System.out.println(v.toString());
*/


    }
}
