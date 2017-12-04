
public class FullThrottleError extends Error {
    
    FullThrottleError(){
        super();
    }
    
    @Override
    public void causeFailure(LunarModel lm) {
        lm.throttle(LunarModel.MAX_THROTTLE);
    }
    
}
