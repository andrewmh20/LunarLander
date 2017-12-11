package errors;
import game.GameState;
import game.LunarModel;

public class ComputerOverloadedError1202 extends Error {

    @Override
    public void causeFailure(LunarModel lm, GameState gs) {
        super.causeFailure(lm, gs);
        gs.setShowComputerError(isComputerError());

        gs.setComputerErrorCode(this.getErrorCode());

    }

    @Override
    public int getErrorCode() {
        // TODO Auto-generated method stub
        return 1202;
    }

    @Override
    public boolean isComputerError() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void undoError(LunarModel lm, GameState gs) {
        // TODO Auto-generated method stub
        gs.setShowComputerError(false);

    }

}
