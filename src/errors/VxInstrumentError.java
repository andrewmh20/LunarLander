
public class VxInstrumentError extends Error {

    private static final double VELOCITY_SCALE = 1000;

    @Override
    public void causeFailure(LunarModel lm, GameState gs) {
        // TODO Auto-generated method stub
        gs.setVx((float)(Math.random()*VELOCITY_SCALE));

    }

    @Override
    public int getErrorCode() {
        // TODO Auto-generated method stub
        return 104;
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
