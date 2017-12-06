//TODO: NEXT really decide on what state game with have, and implement that for serverseide comms
//THen write the client code/add it to the windows
//TODO:set borders, set colors

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
    
    public abstract boolean isComputerError();
    
    public static Error getError(int errorCode) {
        switch (errorCode) {
            case 100:
                return new FullThrottleError();
            case 101:
                return new StuckLeftThrusterError();
            case 102:
                return new StuckRightThrusterError();

                //TODO:eh
                //TODO:fix this from octal
            case 0001:
                return new ResetErrors();
            case 1201:
                return new ComputerOverloadedError();
            default: return null;
        }
        
    }
}
