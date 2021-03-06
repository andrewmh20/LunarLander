package errors;

import game.GameState;
import game.LunarModel;

public class AttInstrumentError extends Error {

    private static final double ANGLE_SCALE = 1000;

    @Override
    public void causeFailure(LunarModel lm, GameState gs) {
        // TODO Auto-generated method stub
        gs.setAngle((float) (Math.random() * ANGLE_SCALE));

    }

    @Override
    public int getErrorCode() {
        // TODO Auto-generated method stub
        return 107;
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
