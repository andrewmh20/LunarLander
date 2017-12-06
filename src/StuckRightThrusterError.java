
public class StuckRightThrusterError extends Error {
    
    StuckRightThrusterError(){
        super();
    }
    
    StuckRightThrusterError(StuckRightThrusterError error){
        
    }

    
    @Override
    public void causeFailure(LunarModel lm) {
        while(true) {
            lm.thrustR();
        }
    }
    
    @Override
    public int getErrorCode() {
        return 102;
    }
    
    @Override
    public boolean isComputerError() {
        return false;
    }
}
