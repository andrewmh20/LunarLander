package errors;

import game.GameState;
import game.LunarModel;

public class ResetErrorsError extends Error {

    @Override
    public void causeFailure(LunarModel lm, GameState gs) {
        // TODO Auto-generated method stub
        // Dummy class to signigfy reset the errors.

    }

    @Override
    public int getErrorCode() {
        // TODO Auto-generated method stub
        return 2;
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
