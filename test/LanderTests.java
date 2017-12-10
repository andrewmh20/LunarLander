import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.junit.Test;

public class LanderTests {

   /* //TODO:Need to do tests with my actual Lunar MOdel guy (thats a requirement)
    @Test
    public void testWorldGravity() {
        World w = new World(new Vec2(0,.05f));
        BodyDef bd = new BodyDef();

        //bd.setLinearVelocity(new Vec2(0,0));
        //bd.setAngularVelocity(0);
        bd.setType(BodyType.DYNAMIC);
        Body b = w.createBody(bd);
        
        System.out.println(b.getPosition());
        w.step(1f, 1, 10);
        System.out.println(b.getLinearVelocity());
        w.step(1f, 1,10);
        System.out.println(b.getLinearVelocity());
        w.step(1f, 10,10);
        System.out.println(b.getLinearVelocity());
        w.step(1f, 5, 5);
        System.out.println(b.getLinearVelocity());
        


    }
    */
    @Test
    public void testBodyTorque() {
        World w = new World(new Vec2(0,.05f));
        BodyDef bd = new BodyDef();

        //bd.setLinearVelocity(new Vec2(0,0));
        //bd.setAngularVelocity(0);
        bd.setType(BodyType.DYNAMIC);
       
        Body b = w.createBody(bd);
        //b.setAwake(true);
        b.m_invI = 1;

        b.applyTorque(100000);
        //b.m_torque = 100.0f;
        //System.out.println(b.m_torque);
       // System.out.println(b.getMass());
        System.out.println(b.m_I);

       // b.setAngularVelocity(5);
        w.step(1f, 10,10);
        System.out.println(b.m_torque);

        System.out.println(b.getAngle());

        w.step(1f, 10,10);
        w.step(1f, 10,10);
        w.step(1f, 10,10);
        w.step(1f, 10,10);
        w.step(1f, 10,10);

        System.out.println(b.getMass());
        System.out.println(b.m_torque);
        System.out.println(b.getWorldCenter());
        System.out.println(b.getAngle());
        System.out.println(b.isFixedRotation());
    
    }
    
//    
//    @Test
//    public void testWorldCreation() {
//        World w = new World(new Vec2(0,1));
//        BodyDef bd = new BodyDef();
//        bd.setType(BodyType.KINEMATIC);
//        bd.setAngle(0);
//        //bd.setAngularVelocity((float) (2*Math.PI));
//        Body b = w.createBody(bd); 
//        
//        //bd is the definition, ie initial values for each Lander, and then 
//        //can directly update b afterwards
//
//
////        System.out.println(b.getAngularVelocity());
//       // System.out.println(b.getPosition());
//
//        b.setAngularVelocity((float) (Math.PI));
//        //b.applyLinearImpulse(new Vec2(0,5f), new Vec2(0,0), true);
//        b.setLinearVelocity(new Vec2(0,1f));
//        //b.applyForceToCenter(new Vec2(0,5f));
//        b.getPosition();
//        System.out.println(b.getMass());
//        System.out.println(w.isContinuousPhysics());
//        //System.out.println(b.getAngularVelocity());
//        System.out.println(b.getAngle());
//        System.out.println(b.getPosition());
//        System.out.println(b.getLinearVelocity());
//        w.step(1f, 10, 10);
//        w.step(1f, 10, 10);
//        w.step(1f, 10, 10);
//        w.step(1f, 10, 10);
//        w.step(1f, 10, 10);
//        
////TODO::ACTUALLY WRITE TESTS BEFORE MOVING BEYOND LANDER
//        //b.applyForceToCenter(new Vec2(0,5f));
//        System.out.println(b.getAngle());
//        System.out.println(b.getPosition());
//
//        /*
//        float Vw = bd.getAngularVelocity();
//        Vec2 v = bd.getLinearVelocity();
//        System.out.println(bd.isActive());
//        System.out.println(bd.getPosition().toString());
//        System.out.println(Vw);
//        System.out.println(bd.getAngle());
//        w.step(10, 100, 1000);
//        System.out.println(bd.getAngle());
//        System.out.println(bd.getType());
//        System.out.println(bd.getPosition().toString());
//        System.out.println(v.toString());
//*/
//
//
//    }
}
