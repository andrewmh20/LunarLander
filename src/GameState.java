import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

//TODO:Is this the right way to structure it?
//And put everything togethre in the Game class
//TODO: Or create a seperate class for each actual panel


public class GameState {
    
    private static final float FUEL_INITIAL = 10000;

    
    
    //TODO:To use to show if crashed in telem panel--DO THIS
    private boolean crashed;
    //true if player is playing as simsup
    private boolean simSup;
    
    //TODO:if I want to add time in game
    private float timeLeft;
    
    //TODO:for the error reset "Error"
    private LinkedList<Error> errorsDone = new LinkedList<Error>();
        
    
    
    private float Vx;
    private float Vy;
    private float Vw;
    private float angle;
    private float altitude;
    private BlockingQueue<Error> errorQue;
    private float fuel = FUEL_INITIAL;
    
    private Error currentError;

    private boolean showComputerError = false;
    private int ComputerErrorCode;
    
    GameState(){
       errorQue =  new LinkedBlockingQueue<Error>(); 
       
    }
    public void reset(){
        Vx = 0;
        Vy = 0;
        Vw = 0;
        angle = 0;
        altitude = 0;
        errorQue =  new LinkedBlockingQueue<Error>(); 
        fuel = FUEL_INITIAL;
        currentError = null;
        showComputerError = false;
        ComputerErrorCode = 0;

        
    }
    
    public  float getVx() {
        return Vx;
    }
    
    public  float getVy() {
        return Vy;
    }
    
    public  float getVw() {
        return Vw;
    }
    public  float getAngle() {
        
        //TODO:THis works, but need to make decisions exactly how I want to display angles
        
        double angle = (this.angle%(2*Math.PI));
        
        if (angle < 0) {
            return (float) Math.toDegrees(Math.PI*2+angle);

        }
        
        return (float) Math.toDegrees(angle);


    }

    public  float getAltitude() {
        return altitude;
    }
    
    public  float getFuel() {
        return fuel;
    }



    public  void setVx(float Vx) {
        this.Vx = Vx;
        
    }
    
    public  void setVy(float Vy) {
        this.Vy = Vy;
        
    }

    public  void setVw(float Vw) {
        this.Vw = Vw;
    }
    public  void setAltitude(float altitude) {
        this.altitude = altitude;
    }
        
    public  void setAngle(float angle) {
        this.angle = angle;
    }

    public  void setFuel(float fuel) {
        this.fuel = fuel;
    }


    public boolean getShowComputerError() {
        return showComputerError;
    }

    
    public void setShowComputerError(boolean b) {
        showComputerError = b;
    }

    public void setErrorsDone(Error err) {
        errorsDone.addLast(err);
    }
    public Error getErrorsDone() {
        return  errorsDone.getLast();
    }
    
    //TODO:fix exceptions
    public  void setError(Error error) {
        try {
            errorQue.put(error);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //TODO:FIx this encapsulation
    public Error getError() {
       // return currentError;
        try {
            return errorQue.take();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    public Error getErrorAttempt() {
        // return currentError;
         try {
             return errorQue.poll(1, TimeUnit.MILLISECONDS);
         } catch (InterruptedException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
             return null;
         }
     }

    public Error peekError() {
        // TODO Auto-generated method stub
        return errorQue.peek();
    }

    public void setComputerErrorCode(int errorCode) {
        // TODO Auto-generated method stub
        ComputerErrorCode = errorCode;
        
    }
    public int getComputerErrorCode() {
        // TODO Auto-generated method stub
        return ComputerErrorCode;
        
    }


//TODO:Still a bunch of stuff here re errors I'm not sure I'm using. Deal with later

    


    
}
