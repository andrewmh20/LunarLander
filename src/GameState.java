import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

//TODO:Is this the right way to structure it?
//And put everything togethre in the Game class
//TODO: Or create a seperate class for each actual panel
public class GameState {

    
    private double fuel;
    private boolean crashed;
    //true if player is playing as simsup
    private boolean simSup;
    private double timeLeft;
    
    //as of now error is null
    //Change this to be set in constrtuctor
    private BlockingQueue<Error> errorQue = new LinkedBlockingQueue<Error>(); 
    private Error currentError;
    private LinkedList<Error> errorsDone = new LinkedList<Error>();
    
    
    //TODO:weird to do it like this, but kind of good because then my display panels get all data from her erather than from
    //the canvas..
    
    
    //TODO:TEsting blockingqs instead of synchrozied mehtods....
    private BlockingQueue<Float> VxQ = new LinkedBlockingQueue<Float>(); 

    
    private float altitude;
    private float Vx;
    private float Vy;
    private float Vw;
    private boolean showComputerError = false;
    private int ComputerErrorCode;
    
    GameState(){
        //TODO:Set to initial values

    }
    
    ///TODO:Maybe these should not ll need to be synchronized, from network thread ONLY update Error field, an
    //and have the rest hapen from tehre in this thread. and in client, gamestate is only updated from the network thread
    
    
    //Encapsualted ok because its a primitive
    public synchronized double getFuel() {
        return fuel;
    }

    public  float getVx() {
        // TODO Auto-generated method stub
        return Vx;
    }
    
    public  float getVy() {
        // TODO Auto-generated method stub
        return Vy;
    }
    
    public  float getVw() {
        // TODO Auto-generated method stub
        //System.out.println(this.Vw);
        return Vw;
    }

    public  void setVx(float Vx) {
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
    
    public  float getAltitude() {
        // TODO Auto-generated method stub
        //System.out.println(Vw);
        return altitude;
        //System.out.println(this.Vw);
    }
    
    public  void setAltitude(float altitude) {
        // TODO Auto-generated method stub
        //System.out.println(Vw);
        this.altitude = altitude;
        //System.out.println(this.Vw);
    }

    
    
    public void setShowComputerError(boolean b) {
        showComputerError = b;
    }
    public boolean getShowComputerError() {
        return showComputerError;
    }

    public void setErrorsDone(Error err) {
        errorsDone.addLast(err);
    }
    public Error getErrorsDone() {
        return  errorsDone.getLast();
    }
    
    //TODO:fix exceptions
    //Got rid of sync because blocking que should be synced already
    public  void setError(Error error) {
        //currentError = error;
        try {
            errorQue.put(error);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //this.notifyAll();
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

    
//    public synchronized void causeCurrentFailure(LunarModel lm) {
//        if (currentError == null) {
//            
//        }
//        //encapsulated
//        else {
//            currentError.causeFailure(lm);
//            currentError = null;
//        }
    //}

    

    
}
