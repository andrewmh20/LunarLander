package errors;
import game.GameState;
import game.LunarModel;

public class FuelLeakError extends Error {

    
    private static final float FUEL_LEAK_RATE = 300;

    @Override
    public void causeFailure(LunarModel lm, GameState gs) {
        // TODO Auto-generated method stub
        //TODO: eh I could make this variable but I do not have time.
        
        gs.setFuel(Math.max(gs.getFuel()-FUEL_LEAK_RATE,0));

    }

    @Override
    public int getErrorCode() {
        // TODO Auto-generated method stub
        return 103;
    }

    @Override
    public boolean isComputerError() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void undoError(LunarModel lm, GameState gs) {
        // TODO Auto-generated method stub
        //Nithin to undo, just stop doing it
        
    }

}
