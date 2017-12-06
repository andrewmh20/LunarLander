
public class StuckLeftThrusterError extends Error {
    
    //TODO:these arent really stuck, just momentarily
    StuckLeftThrusterError(){
        super();
    }
    
    StuckLeftThrusterError(StuckLeftThrusterError error){
        
    }

    
    @Override
    public void causeFailure(LunarModel lm) {
            lm.thrustL();
            
    }
    
    @Override
    public int getErrorCode() {
        return 101;
    }
    
    @Override
    public boolean isComputerError() {
        return false;
    }
}
