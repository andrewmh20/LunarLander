
public class FullThrottleError extends Error {
    
    FullThrottleError(){
        super();
    }
    
    FullThrottleError(FullThrottleError error){
        
    }

    
    @Override
    public void causeFailure(LunarModel lm) {
        lm.throttle(LunarModel.MAX_THROTTLE);
    }
    
    @Override
    public int getErrorCode() {
        return 0100;
    }
    
    @Override
    public boolean isComputerError() {
        return true;
    }
}
