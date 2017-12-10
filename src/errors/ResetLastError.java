
public class ResetLastError extends Error {

    @Override
    public void causeFailure(LunarModel lm, GameState gs) {
        // TODO Auto-generated method stub
        //Dummy class to signigfy reset the errors.
        
    }

    @Override
    public int getErrorCode() {
        // TODO Auto-generated method stub
        return 3;
    }

    @Override
    public boolean isComputerError() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void undoError(LunarModel lm, GameState gs) {
        // TODO Auto-generated method stub
        
    }

}
