package errors;

import game.GameState;
import game.LunarModel;

public class DummyError extends Error {

    @Override
    public void causeFailure(LunarModel lm, GameState gs) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getErrorCode() {
        // TODO Auto-generated method stub
        return 0;
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
