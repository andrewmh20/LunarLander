import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;

//TODO: Do I really need this class? Or just set a bd s in the main thing. I'm just using this as to hold "Constants"
//Dont do this, just create it as needed.
public class LanderDef extends BodyDef {
   
    //TODO:Public or private?
    //TODO:WIll want to start moving horizontally.
    private static final Vec2 INITIAL_V = new Vec2(0,0);
    private static final float INITIAL_Vw = 0;
    //default

    
    public LanderDef() {
        super();
        //Keeping most at deafults, but provide easy way to change some?...may not need to.
        setLinearVelocity(INITIAL_V);
        setAngularVelocity(INITIAL_Vw);
        setType(BodyType.DYNAMIC);
    }
}
