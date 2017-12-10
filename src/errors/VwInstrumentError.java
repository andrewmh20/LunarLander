package errors;
import game.GameState;
import game.LunarModel;

public class VwInstrumentError extends Error {

    private static final double VELOCITY_SCALE = 100;

    @Override
    public void causeFailure(LunarModel lm, GameState gs) {
        // TODO Auto-generated method stub
        //TODO: in theory this would be more subtle, hard to catch, but I do not have time
        gs.setVw((float)(Math.random()*VELOCITY_SCALE));
    }

    @Override
    public int getErrorCode() {
        // TODO Auto-generated method stub
        return 106;
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
