public class ResetErrors extends Error {
    
    
    //TODO:This Class does not work as expected--it resets the game, not the errors
    ResetErrors(){
        super();
    }
    
    
    @Override
    public boolean isComputerError() {
        return false;
    }
    
    @Override
    public void causeFailure(LunarModel lm, GameState gs) {
        lm.reset();
        gs.reset();

    }
    
    
    @Override
    public int getErrorCode() {
        return 0001;
    }
    
}
