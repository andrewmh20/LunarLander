
public class StuckRightThrusterError extends Error {
    
    StuckRightThrusterError(){
        super();
    }
    
    StuckRightThrusterError(StuckRightThrusterError error){
        
    }

    
    @Override
    public void causeFailure(LunarModel lm, GameState gs) {
            lm.thrustR(gs.getHasFuel());
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
