package game;


import java.util.LinkedList;

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
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

public class LunarModel {

    /**
     * Class to hold the physics state of the Lunar Lander and provide methods for getting the
     * objects position, which will be used in the Swing Drawing classes AS WELL AS OTHER OBJECTS IN
     * THE LUNAR AREA
     */

    // Initial velocity settings
    private static final Vec2 INITIAL_V = new Vec2(30, 20);
    private static final float INITIAL_Vw = 0;
    private static final Vec2 INITIAL_POSITION = new Vec2(20, 20);
    // Gravity Vector
    // 6f
    private static final Vec2 gravity = new Vec2(0, 6f);
    // Scale to convert physics world to pixel world
    // TODO:Test changing scale
    public static final float SCALE = 1f;
    private static final float DENSITY_OF_SHAPES = 1;
    // Between 0 and 1
    private static final float SURFACE_FRICTION = 1;
    private static final float LEM_FRICTION = 1f;

    public static final float THROT_SCALE = 250f;
    public static final int MAX_THROTTLE = 100;
    public static final int MIN_THROTTLE = 0;
    // thruster TORQUE settings
    private static final int TORQUE = 700000;

    // Vertices of lunar surface

    private static final Vec2 V1 = new Vec2(0, Canvas.CANVAS_HEIGHT - 10);
    private static final Vec2 V2 = new Vec2(35, Canvas.CANVAS_HEIGHT - 10);
    private static final Vec2 V3 = new Vec2(60, (Canvas.CANVAS_HEIGHT - 145 + 30));
    private static final Vec2 V4 = new Vec2(107, (Canvas.CANVAS_HEIGHT - 114 + 30));
    private static final Vec2 V5 = new Vec2(134, (Canvas.CANVAS_HEIGHT - 114 + 30));
    private static final Vec2 V6 = new Vec2(150, (Canvas.CANVAS_HEIGHT - 58) + 30);
    private static final Vec2 V7 = new Vec2(210, (Canvas.CANVAS_HEIGHT - 58) + 30);
    private static final Vec2 V8 = new Vec2(253, (Canvas.CANVAS_HEIGHT - 86) + 30);
    private static final Vec2 V9 = new Vec2(299, (Canvas.CANVAS_HEIGHT - 98) + 30);
    private static final Vec2 V10 = new Vec2(378, (Canvas.CANVAS_HEIGHT - 98) + 30);
    private static final Vec2 V11 = new Vec2(420, (Canvas.CANVAS_HEIGHT - 140) + 30);
    private static final Vec2 V12 = new Vec2(476, (Canvas.CANVAS_HEIGHT - 163) + 30);
    private static final Vec2 V13 = new Vec2(530, (Canvas.CANVAS_HEIGHT - 81) + 30);
    private static final Vec2 V14 = new Vec2(578, (Canvas.CANVAS_HEIGHT - 81) + 30);
    private static final Vec2 V15 = new Vec2(642, (Canvas.CANVAS_HEIGHT - 106) + 30);
    private static final Vec2 V16 = new Vec2(738, (Canvas.CANVAS_HEIGHT - 98) + 30);
    private static final Vec2 V17 = new Vec2(757, (Canvas.CANVAS_HEIGHT - 81) + 30);
    private static final Vec2 V18 = new Vec2(913, (Canvas.CANVAS_HEIGHT - 123) + 30);
    private static final Vec2 V19 = new Vec2(963, (Canvas.CANVAS_HEIGHT - 83) + 30);
    private static final Vec2 V20 = new Vec2(1000, (Canvas.CANVAS_HEIGHT - 83) + 30);

    // Crash settings
    private static final float CRASH_VELOCITY_Y = 10;
    private static final float CRASH_VELOCITY_X = 10;
    private static final float CRASH_ANGLE = (float) (Math.PI / 6) / 2;
    public static float JUMP_ANGLE = (float) Math.PI / 24;

    // initial setttings
    private float throttle = 0;
    private boolean crashed = false;
    private boolean landed = false;

    private boolean contactLight = false;
    private float lastVy;
    private float lastVx;

    // Physics World

    // TODO: Can really make the defs local vars, not fields...

    private float lastAngle;
    private final World world;
    private final BodyDef lemBodyDef;
    private final Body lemBody;
    private final BodyDef surfaceBodyDef;

    private final Body surfaceBody;

    private Vec2 attitudeVec;

    private float altitude;
    private final Distance distance;
    private final DistanceOutput DO;
    private final DistanceInput DI;
    private final SimplexCache SC;
    private final Distance.DistanceProxy lemDistanceProxy = new Distance.DistanceProxy();

    private Distance.DistanceProxy surfaceDistanceProxy = new Distance.DistanceProxy();
    // Shape fields
    private final PolygonShape lemShape;
    // Edge shapes are created later.....beccause at this point do not know how many there are
    private final LinkedList<Vec2> vertices = new LinkedList<Vec2>();

    private final LinkedList<EdgeShape> surfaceShapeList = new LinkedList<EdgeShape>();

    ContactListener crashDetector = new ContactListener() {

        @Override
        public void beginContact(Contact contact) {

            contactLight = true;

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

    public LunarModel() {
        // Create world
        world = new World(gravity);
        // Listen for contacts
        world.setContactListener(crashDetector);

        // Create the BodyDefs
        lemBodyDef = new BodyDef();
        lemBodyDef.setPosition(INITIAL_POSITION);
        lemBodyDef.setLinearVelocity(INITIAL_V);
        lemBodyDef.setAngularVelocity(INITIAL_Vw);
        lemBodyDef.setType(BodyType.DYNAMIC);

        surfaceBodyDef = new BodyDef();
        
        // Create the bodies
        lemBody = world.createBody(lemBodyDef);
        // bit of a hack, but it works
        // TODO: maybe I do not need this?
        lemBody.m_invI = 1;
        
        

        surfaceBody = world.createBody(surfaceBodyDef);

        //System.out.println(surfaceBodyDef.getPosition());

        
        // Create necessary vector from Lem body
        attitudeVec = new Vec2((float) Math.sin(lemBody.getAngle()),
                (float) Math.cos(lemBody.getAngle()));

        // Create lem Shape, linked with the constant size of the lem in the display
        lemShape = new PolygonShape();
        lemShape.setAsBox(Canvas.LEM_WIDTH, Canvas.LEM_HEIGHT);

        // Add the shape to the lem Body as fixture
        final FixtureDef lemFixtureDef = new FixtureDef();
        lemFixtureDef.setDensity(DENSITY_OF_SHAPES);
        lemFixtureDef.setFriction(LEM_FRICTION);
        lemFixtureDef.setRestitution(0);
        lemFixtureDef.setShape(lemShape);

        lemBody.createFixture(lemFixtureDef);
        // lemBody.getFixtureList().setRestitution(0);
        // lemBody.createFixture(lemShape, DENSITY_OF_SHAPES);
       
        // Create all the shapes for the lunar surface, using the vertices "linked" to the display

        // Add the vertices
        vertices.add(V1);
        vertices.add(V2);
        vertices.add(V3);
        vertices.add(V4);
        vertices.add(V5);
        vertices.add(V6);
        vertices.add(V7);
        vertices.add(V8);
        vertices.add(V9);
        vertices.add(V10);
        vertices.add(V11);
        vertices.add(V12);
        vertices.add(V13);
        vertices.add(V14);
        vertices.add(V15);
        vertices.add(V16);
        vertices.add(V17);
        vertices.add(V18);
        vertices.add(V19);
        vertices.add(V20);

        // Create the shapes and add to the shape list
        for (int i = 0; i < (vertices.size() - 1); i++) {

            final EdgeShape surfaceShape = new EdgeShape();
            surfaceShape.set(vertices.get(i), vertices.get(i + 1));
            surfaceShapeList.add(surfaceShape);
        }

        // I.e. surfaceBody's point
        // Add each shape to the body as a seperate fixture
        for (final EdgeShape surfaceS : surfaceShapeList) {
            final FixtureDef surfaceFixtureDef = new FixtureDef();
            //surfaceFixtureDef.setDensity(DENSITY_OF_SHAPES);
            surfaceFixtureDef.setDensity(0);
            surfaceFixtureDef.setShape(surfaceS);
            // Never bounce
            surfaceFixtureDef.setRestitution(0);
            surfaceFixtureDef.setFriction(SURFACE_FRICTION);
            surfaceBody.createFixture(surfaceFixtureDef);
        }

        // create "closing fixtures" and a seperate body to prevent looping back around to the moons
        // surface

//        final BodyDef encloserBodyDef = new BodyDef();
//        final Body encloserBody = world.createBody(encloserBodyDef);
//        final EdgeShape encloser1 = new EdgeShape();
//        final EdgeShape encloser2 = new EdgeShape();
//        final EdgeShape encloser3 = new EdgeShape();
//        encloser1.set(vertices.getLast(), (new Vec2(Canvas.CANVAS_WIDTH, Canvas.CANVAS_HEIGHT)));
//        encloser2.set(new Vec2(Canvas.CANVAS_WIDTH, Canvas.CANVAS_HEIGHT),
//                new Vec2(0, Canvas.CANVAS_HEIGHT));
//        encloser3.set(new Vec2(0, Canvas.CANVAS_HEIGHT), vertices.get(0));
//        encloserBody.createFixture(encloser1, DENSITY_OF_SHAPES);
//        encloserBody.createFixture(encloser2, DENSITY_OF_SHAPES);
//        encloserBody.createFixture(encloser3, DENSITY_OF_SHAPES);

        // create a new distance setup between any two fixtures
        distance = new Distance();
        DO = new DistanceOutput();
        DI = new DistanceInput();
        SC = new SimplexCache();

    }

    public float getAltitude() {
        return SCALE * altitude;
    }

    public float getAngle() {
        return lemBody.getAngle();

    }

    public int getAttX() {
        return Math.round(SCALE * attitudeVec.x);

    }

    public int getAttY() {
        return Math.round(SCALE * attitudeVec.y);

    }

    public boolean getContactLight() {
        return contactLight;
    }

    public float getPx() {
        return SCALE * lemBody.getPosition().x;

    }

    public float getPy() {
        return SCALE * lemBody.getPosition().y;

    }


    public LinkedList<Vec2> getSurfaceVertices() {

        final LinkedList<Vec2> verticesScaled = new LinkedList<Vec2>();
        for (final Vec2 v : vertices) {
           verticesScaled.add(v.mul(SCALE));
            verticesScaled.add(v);
        }
        return verticesScaled;
    }

    public int getThrottle() {
        // Return it unscaled
        return Math.round(throttle / THROT_SCALE);
    }

    public float getVw() {
        ;
        return SCALE * lemBody.getAngularVelocity();
    }

    public float getVx() {
        // TODO Auto-generated method stub
        return SCALE * lemBody.getLinearVelocity().x;
    }

    // For Testing
    public float getVy() {
        return SCALE * lemBody.getLinearVelocity().y;
    }

    public boolean isCrashed() {

        if (contactLight && ((lastVy > CRASH_VELOCITY_Y) || (Math.abs(lastAngle) > CRASH_ANGLE)
                || (lastVx > CRASH_VELOCITY_X))) {
            crashed = true;

        } else {
            crashed = false;
        }

        return crashed;

    }

    public boolean isLanded() {

        if (contactLight && !((lastVy > CRASH_VELOCITY_Y) || (Math.abs(lastAngle) > CRASH_ANGLE)
                || (lastVx > CRASH_VELOCITY_X))) {
            landed = true;
        } else {
            landed = false;
        }

        return landed;

    }

    public void jumpL(boolean hasFuel) {

        if (hasFuel) {
            lemBody.setTransform(lemBody.getPosition(), lemBody.getAngle() + JUMP_ANGLE);

        }

    }

    public void jumpR(boolean hasFuel) {
        if (hasFuel) {
            lemBody.setTransform(lemBody.getPosition(), lemBody.getAngle() - JUMP_ANGLE);

        }

    }

    // TODO: Change this logic to maybe be a list, or soemthing to work nicely with returning all
    // the vertices
    // public Vec2 getSurfaceStart() {
    // surfaceS
    // }

    // Surface and collisions Logic:

    // TODO: Created surface above

    public void move() {

        // Choose which surfaceShape to calculate distance on
        for (int i = 0; i < (vertices.size() - 1); i++) {
            // I.e center of lemBody
            if (((lemBody.getPosition().x + (Canvas.LEM_WIDTH / 2)) > vertices.get(i).x)
                    && ((lemBody.getPosition().x + (Canvas.LEM_WIDTH / 2)) <= vertices
                            .get(i + 1).x)) {
                surfaceDistanceProxy = new DistanceProxy();
                surfaceDistanceProxy.set(surfaceShapeList.get(i), 0);

            }
        }

        // Setup distance and calculate
        DI.proxyA = lemDistanceProxy;
        DI.proxyB = surfaceDistanceProxy;
        DI.transformA = lemBody.getTransform();
        DI.transformB = surfaceBody.getTransform();
        
        distance.distance(DO, SC, DI);

        // TODO: 20 off always...
        altitude = DO.distance - 20;

        // Set the new attitude, save the velocity and angle states
        attitudeVec = new Vec2((float) Math.sin(lemBody.getAngle()),
                (float) Math.cos(lemBody.getAngle()));
        lastVy = lemBody.getLinearVelocity().y;
        lastVx = lemBody.getLinearVelocity().x;
        lastAngle = lemBody.getAngle();

        // Apply forces that have not yet been applied (i.e. not thrusters)
        lemBody.applyForceToCenter(attitudeVec.mul(throttle).negate());

        // Step and clear forces
        world.step(1 / 60f, 1, 1);
        world.clearForces();

    }

    // Would not really be that immidiate, but reasonable.
    // TODO:if I really fix teh jump I can store a list of torque forces and use this to undo them.
    public void nullAngularForces(boolean hasFuel) {

        if (hasFuel) {
            lemBody.setAngularVelocity(0);

        }

    }

    public void throttle(int throt, boolean hasFuel) {
        if (hasFuel) {

            if (throt > MAX_THROTTLE) {
                throttle = MAX_THROTTLE * THROT_SCALE;

            } else if (throt < MIN_THROTTLE) {
                throttle = MIN_THROTTLE * THROT_SCALE;

            } else {

                throttle = (throt * THROT_SCALE);
            }

        }
    }

    public void thrustL(boolean hasFuel) {
        if (hasFuel) {
            lemBody.applyTorque(TORQUE);

        }

    }

    public void thrustR(boolean hasFuel) {
        if (hasFuel) {
            lemBody.applyTorque(-TORQUE);

        }

    }

}
