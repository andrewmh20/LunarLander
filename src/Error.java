
public abstract class Error {
///TODO:OR MAKE INTERFACE
    
    Error(){
    }
    //TODO: Hope this is good use of it, must have some other reason
    //TODO: Or make input more general somehow; depends on types of failures I have
    //TODO: Refactor, ecause really may need to take in  teh "game state" for fuel failures etc....
    //Unless I incoporate that into canvaS? Weird. but so far I think setup is good.
    public abstract void causeFailure(LunarModel lm);
    
    public abstract int getErrorCode();
    
    public static Error getError(int errorCode) {
        switch (errorCode) {
            case 1201:
                return new FullThrottleError();
                //TODO:eh
            case 0001:
                return new ResetErrors();
            default: return null;
        }
        
    }
}
