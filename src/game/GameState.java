package game;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import errors.Error;

public class GameState {
    
    private static final float FUEL_INITIAL = 100000000;

    private boolean contactLight = false;
    
    public void setContactLight(boolean contactLight) {
        this.contactLight = contactLight;
    }
    
    public boolean getContactLight() {
        return contactLight;
    }
    
    //TODO:change angle to be reversed.
    
    
    //TODO:To use to show if crashed in telem panel--DO THIS
    private boolean crashed;
    //true if player is playing as simsup
    private boolean simSup;
    
    //TODO:if I want to add time in game
    private float timeLeft;
    
    //TODO:for the error reset "Error"
    private LinkedList<Error> errorsDone = new LinkedList<Error>();
        
    private Set<Integer> indicesToRemove;
    private Map<Error, Integer> errorFreqSent;
    private LinkedList<Error> errors;

    
    private float Vx;
    private float Vy;
    private float Vw;
    private float angle;
    private float altitude;
    private BlockingQueue<Error> errorQue;
    private  float fuel = FUEL_INITIAL;
    
    private Error currentError;

    private boolean showComputerError = false;
    private int ComputerErrorCode;

    private boolean hasFuel = true;

    private boolean isEasy;
    
    GameState(boolean isEasy){
        //TODO: doesnt need to be blocking...does it? 
       errorQue =  new LinkedBlockingQueue<Error>(); 
       this.isEasy = isEasy;
       
       indicesToRemove = new TreeSet<Integer>();
       errorFreqSent = new TreeMap<Error, Integer>();
      errors = new LinkedList<Error>();
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

        hasFuel = true;
        indicesToRemove = new TreeSet<Integer>();
        errorFreqSent = new TreeMap<Error, Integer>();
       errors = new LinkedList<Error>();


        
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

    
    
    public void setErrorToSend(Error error) {
        System.out.println(error);
        if(error == null) {
            
        }
        else if (errorFreqSent.get(error)==null) {
            errorFreqSent.put(error, 1);

        }
        else {
            errorFreqSent.put(error, errorFreqSent.get(error)+1);
        }
        try {
          errorQue.put(error);
      } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
    }
    
  public Error getErrorToSend() {
  // return currentError;
   try {
       return errorQue.take();
   } catch (InterruptedException e) {
       //wont happen
       // TODO Auto-generated catch block
       e.printStackTrace();
       return null;
   }
}
  
  public void setErrorReceived(Error error) {
      
          
          errors.addLast(error);
  
  }
    
  public void doErrors(Canvas canvas, LunarModel lm, GameState gs) {

       


      
      
      for (int i = 0; i< errors.size(); i++) {
          
          Error errorInList = errors.get(i);
          if (errorInList == null) {
              continue;
          }

//          if (errorInList instanceof ResetGameError) {
//              canvas.reset();
//              indicesToRemove.add(i);
//          }
//          else if (errorInList instanceof ResetLastError) {
//              if (errors.get(i-1) != null) {
//                  errors.get(i-1).undoError(lm, gs);
//
//              }
//              indicesToRemove.add(i-1);
//          }
//          else if (errorInList instanceof ResetErrorsError) {
//              for (Error errorsInList: errors) {
//                  errorsInList.undoError(lm, gs);
//              }
//              for (int j = 0 ; j<errors.size(); j++) {
//                  indicesToRemove.add(j);
//
//              }
//              
//          }
//          
//          else {
          
          if ( errorInList.isComputerError()){
              SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    JOptionPane.showMessageDialog(null, "COMPUTER ERROR: " + errorInList.getErrorCode());
                    System.out.println("New display thread!");
                }
                  
              });
              indicesToRemove.add(i);

          }

              errorInList.causeFailure(lm, gs);
//          }
      }
          for (int i : indicesToRemove) {
              errors.set(i, null);
      }
          indicesToRemove.clear();
      
  }
  

  public int getErrorFreq(Error error) {
      if (errorFreqSent.get(error) == null) {
          return 0;
      }
      else {
          return errorFreqSent.get(error);

      }
  }
  
  
//    
//    public void setErrorsDone(Error err) {
//        errorsDone.addLast(err);
//    }
//    public Error getErrorsDone() {
//        return  errorsDone.getLast();
//    }
//    
//    //TODO:fix exceptions
//    public  void setError(Error error) {
//        try {
//            errorQue.put(error);
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//    //TODO:FIx this encapsulation
//    public Error getError() {
//       // return currentError;
//        try {
//            return errorQue.take();
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return null;
//        }
//    }
//    public Error getErrorAttempt() {
//        // return currentError;
//         try {
//             return errorQue.poll(1, TimeUnit.MILLISECONDS);
//         } catch (InterruptedException e) {
//             // TODO Auto-generated catch block
//             e.printStackTrace();
//             return null;
//         }
//     }
//
//    public Error peekError() {
//        // TODO Auto-generated method stub
//        return errorQue.peek();
//    }

    public void setComputerErrorCode(int errorCode) {
        // TODO Auto-generated method stub
        ComputerErrorCode = errorCode;
        
    }
    public int getComputerErrorCode() {
        // TODO Auto-generated method stub
        return ComputerErrorCode;
        
    }

    public void setHasFuel(boolean hasFuel) {
this.hasFuel  = hasFuel;        
    }
    public boolean getHasFuel() {
return hasFuel;        
    }

    public boolean getIsEasy() {
        // TODO Auto-generated method stub
        return isEasy;
    }



//TODO:Still a bunch of stuff here re errors I'm not sure I'm using. Deal with later

    


    
}
