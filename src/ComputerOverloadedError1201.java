

public class ComputerOverloadedError1201 extends Error {

    @Override
    public void causeFailure(LunarModel lm, GameState gs) {
        // TODO Auto-generated method stub

        // This is more fine grained control than I need, could just shpw the computer Error anytime
        // "isComputerError"
        super.causeFailure(lm, gs);
        gs.setShowComputerError(isComputerError());

        gs.setComputerErrorCode(this.getErrorCode());

    }

    @Override
    public int getErrorCode() {
        // TODO Auto-generated method stub
        return 1201;
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
