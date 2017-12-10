package errors;
import errors.Error;
import game.GameState;
import game.LunarModel;

public class RightThrusterError extends Error {

    @Override
    public void causeFailure(LunarModel lm, GameState gs) {
        // TODO Auto-generated method stub
        lm.thrustR(gs.getHasFuel());


    }

    @Override
    public int getErrorCode() {
        // TODO Auto-generated method stub
        return 102;
    }

    @Override
    public boolean isComputerError() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void undoError(LunarModel lm, GameState gs) {
        // TODO Auto-generated method stub
        //just stop doing it 
    }

}
