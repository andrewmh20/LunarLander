//
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//
//import LunarModel;
//
//public class LunarModelTest {
//
//    LunarModel lm;
//    
//    @Before
//    public void setUp() throws Exception {
//        lm = new LunarModel();
//        //Scale is equal to 1, I removed that from consideration as the design went on
//        /*FOR TESTS, because of having to do the math (maybe change to arguments?),
//         * LunarModel constants must be as follows:
//         * gravity vector = (0,-10)
//         * initial V = (0,0)
//         * initial Vw = 0
//         * initial P = (0,0)
//         * Torque = 1000000 (10E5);
//         * (dimensions of LM fixture = (20,20), density = 1)
//         */
//            
//    }
//    //Getters are implicitly tested by checking results of other methods
//
//    @Test
//    public void testGetAltitude() throws Exception {
//        throw new RuntimeException("not yet implemented");
//    }
//
//    @Test
//    public void testGetAngle() throws Exception {
//        
//            lm.move();
//        lm.thrustL(true);
//        assertEquals(lm.getAngle(), lm.getAngle());
//        
//    }
//
//    @Test
//    public void testGetAttX() throws Exception {
//        throw new RuntimeException("not yet implemented");
//    }
//
//    @Test
//    public void testGetAttY() throws Exception {
//        throw new RuntimeException("not yet implemented");
//    }
//
//    @Test
//    public void testGetContactLight() throws Exception {
//        throw new RuntimeException("not yet implemented");
//    }
//
//    @Test
//    public void testGetPx() throws Exception {
//        throw new RuntimeException("not yet implemented");
//    }
//
//    @Test
//    public void testGetPy() throws Exception {
//        throw new RuntimeException("not yet implemented");
//    }
//
//    @Test
//    public void testGetSurfaceVertices() throws Exception {
//        throw new RuntimeException("not yet implemented");
//    }
//
//    @Test
//    public void testGetThrottle() throws Exception {
//        throw new RuntimeException("not yet implemented");
//    }
//
//    @Test
//    public void testGetVw() throws Exception {
//        throw new RuntimeException("not yet implemented");
//    }
//
//    @Test
//    public void testGetVx() throws Exception {
//        throw new RuntimeException("not yet implemented");
//    }
//
//    @Test
//    public void testGetVy() throws Exception {
//        throw new RuntimeException("not yet implemented");
//    }
//
//    @Test
//    public void testIsCrashed() throws Exception {
//        throw new RuntimeException("not yet implemented");
//    }
//
//    @Test
//    public void testIsLanded() throws Exception {
//        throw new RuntimeException("not yet implemented");
//    }
//
//    @Test
//    public void testJumpL() throws Exception {
//        throw new RuntimeException("not yet implemented");
//    }
//
//    @Test
//    public void testJumpR() throws Exception {
//        throw new RuntimeException("not yet implemented");
//    }
//
//    @Test
//    public void testMove() throws Exception {
//        throw new RuntimeException("not yet implemented");
//    }
//
//    @Test
//    public void testNullAngularForces() throws Exception {
//        throw new RuntimeException("not yet implemented");
//    }
//
//    @Test
//    public void testThrottle() throws Exception {
//        throw new RuntimeException("not yet implemented");
//    }
//
//    @Test
//    public void testThrustL() throws Exception {
//        throw new RuntimeException("not yet implemented");
//    }
//
//    @Test
//    public void testThrustR() throws Exception {
//        throw new RuntimeException("not yet implemented");
//    }
//
//}
