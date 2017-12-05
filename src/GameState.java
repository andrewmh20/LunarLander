//TODO:Is this the right way to structure it?
//And put everything togethre in the Game class
//TODO: Or create a seperate class for each actual panel
public class GameState {

    
    private double fuel;
    private boolean crashed;
    //true if player is playing as simsup
    private boolean simSup;
    private double timeLeft;
    
    //TODO:weird to do it like this, but kind of good because then my display panels get all data from her erather than from
    //the canvas..
    
    private float altitude;
    private float Vx;
    private float Vy;
    private float Vw;
    
    GameState(){
        //TODO:Set to initial values

    }
    
    //Encapsualted ok because its a primitive
    public synchronized double getFuel() {
        return fuel;
    }

    public synchronized float getVx() {
        // TODO Auto-generated method stub
        return Vx;
    }
    
    public synchronized float getVy() {
        // TODO Auto-generated method stub
        return Vy;
    }
    
    public synchronized float getVw() {
        // TODO Auto-generated method stub
        //System.out.println(this.Vw);
        return Vw;
    }

    public synchronized void setVx(float Vx) {
        // TODO Auto-generated method stub
        this.Vx = Vx;
        
    }
    public synchronized void setVy(float Vy) {
        // TODO Auto-generated method stub
        this.Vy = Vy;
        
    }

    public synchronized void setVw(float Vw) {
        // TODO Auto-generated method stub
        //System.out.println(Vw);
        this.Vw = Vw;
        //System.out.println(this.Vw);
    }
    
    

    
}
