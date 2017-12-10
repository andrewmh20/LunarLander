package errors;
import game.GameState;
import game.LunarModel;

public class FullThrottleError extends Error {
    
    public FullThrottleError(){
        super();
    }
    
    FullThrottleError(FullThrottleError error){
        
    }

    int tempThrottle;
    
    @Override
    public void causeFailure(LunarModel lm, GameState gs) {
        tempThrottle = lm.getThrottle();
        lm.throttle(LunarModel.MAX_THROTTLE, gs.getHasFuel());
    }
    
    @Override
    public int getErrorCode() {
        return 100;
    }
    
    @Override
    public boolean isComputerError() {
        return false;
    }

    @Override
    public void undoError(LunarModel lm, GameState gs) {
        // TODO Auto-generated method stub
        lm.throttle(tempThrottle, gs.getHasFuel());
        
    }
}
