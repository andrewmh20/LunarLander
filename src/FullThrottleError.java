

public class FullThrottleError extends Error {

    int tempThrottle;

    public FullThrottleError() {
        super();
    }

    FullThrottleError(FullThrottleError error) {

    }

    @Override
    public void causeFailure(LunarModel lm, GameState gs) {
        tempThrottle = lm.getThrottle();
        lm.throttle(LunarModel.MAX_THROTTLE, gs.getHasFuel());
    }

    @Override
    public int getErrorCode() {
        return 100;
    }

    @Override
    public boolean isComputerError() {
        return false;
    }

    @Override
    public void undoError(LunarModel lm, GameState gs) {
        // TODO Auto-generated method stub
        lm.throttle(tempThrottle, gs.getHasFuel());

    }
}
