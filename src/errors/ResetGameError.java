
public class ResetGameError extends Error {

    
    @Override
    public void causeFailure(LunarModel lm, GameState gs) {
        // TODO Auto-generated method stub
        lm = new LunarModel();
        gs.reset();
    }

    @Override
    public int getErrorCode() {
        // TODO Auto-generated method stub
        return 1;
    }

    @Override
    public boolean isComputerError() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void undoError(LunarModel lm, GameState gs) {
        // TODO Auto-generated method stub
        //cannot undo (for now)
    }

}
