package errors;

import game.GameState;
import game.LunarModel;


public abstract class Error implements Comparable<Error> {

    public static Error getError(int errorCode) {
        switch (errorCode) {
        // can't put null in....
        case 0:
            return new DummyError();
        case 100:
            return new FullThrottleError();
        case 101:
            return new LeftThrusterError();
        case 102:
            return new RightThrusterError();

        case 1:
            return new ResetGameError();
        case 2:
            return new ResetErrorsError();
        case 3:
            return new ResetLastError();

        case 1201:
            return new ComputerOverloadedError1201();
        case 1202:
            return new ComputerOverloadedError1202();
        case 103:
            return new FuelLeakError();
        case 104:
            return new VxInstrumentError();
        case 105:
            return new VyInstrumentError();
        case 106:
            return new VwInstrumentError();
        case 107:
            return new AttInstrumentError();
        case 108:
            return new AltInstrumentError();

        default:
            return new DummyError();
        }

    }

    Error() {
        
    };

    // TODO: Hope this is good use of it, must have some other reason
    // TODO: Or make input more general somehow; depends on types of failures I have
    // TODO: Refactor, ecause really may need to take in teh "game state" for fuel failures etc....
    // Unless I incoporate that into canvaS? Weird. but so far I think setup is good.
    public void causeFailure(LunarModel lm, GameState gs) {

    }

    @Override
    // Throws null pointer exception if null
    public int compareTo(Error e) {
        // TODO Auto-generated method stub

        if (getErrorCode() < e.getErrorCode()) {
            return -1;
        } else if (getErrorCode() > e.getErrorCode()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub

        if (obj == null) {
            return false;
        } else {
            return (getErrorCode() == ((Error) obj).getErrorCode());

        }
    }

    public abstract int getErrorCode();

    public abstract boolean isComputerError();

    public abstract void undoError(LunarModel lm, GameState gs);
}
