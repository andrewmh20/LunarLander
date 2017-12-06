
public class StuckLeftThrusterError extends Error {
    
    StuckLeftThrusterError(){
        super();
    }
    
    StuckLeftThrusterError(StuckLeftThrusterError error){
        
    }

    
    @Override
    public void causeFailure(LunarModel lm) {
        while(true) {
            lm.thrustL();
        }
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
