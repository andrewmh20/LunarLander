
public class LeftThrusterError extends Error {

    @Override
    public void causeFailure(LunarModel lm, GameState gs) {
        // TODO Auto-generated method stub
        lm.thrustL(gs.getHasFuel());

    }

    @Override
    public int getErrorCode() {
        // TODO Auto-generated method stub
        return 101;
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
