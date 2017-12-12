package game;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import errors.Error;

public class GameState {

    private static final float FUEL_INITIAL = 100000;

    private boolean contactLight = false;

    private Set<Integer> indicesToRemove;

    // TODO:change angle to be reversed.

    private Map<Error, Integer> errorFreqSent;

    private LinkedList<Error> errors;
    private float Vx;
    private float Vy;

    private float Vw;
    private float angle;
    private float altitude;
    private BlockingQueue<Error> errorQue;
    private float fuel = FUEL_INITIAL;
    private boolean showComputerError = false;
    private int ComputerErrorCode;

    private boolean hasFuel = true;
    private final boolean isEasy;

    GameState(boolean isEasy) {
        // TODO: doesnt need to be blocking...does it?
        errorQue = new LinkedBlockingQueue<Error>();
        this.isEasy = isEasy;

        indicesToRemove = new TreeSet<Integer>();
        errorFreqSent = new TreeMap<Error, Integer>();
        errors = new LinkedList<Error>();
    }

    public void doErrors(Canvas canvas, LunarModel lm, GameState gs) {

        for (int i = 0; i < errors.size(); i++) {

            final Error errorInList = errors.get(i);
            if (errorInList == null) {
                continue;
            }

            else {

                if (errorInList.isComputerError()) {
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            JOptionPane.showMessageDialog(null,
                                    "COMPUTER ERROR: "
                                            + errorInList.getErrorCode());
                        }

                    });
                    indicesToRemove.add(i);

                }

                errorInList.causeFailure(lm, gs);
            }
        }
        for (final int i : indicesToRemove) {
            errors.set(i, null);
        }
        indicesToRemove.clear();

    }

    public float getAltitude() {
        return altitude;
    }

    public float getAngle() {

        final double angle = (this.angle % (2 * Math.PI));

        if (angle < 0) {
            return (float) Math.toDegrees((Math.PI * 2) + angle);

        }

        return (float) Math.toDegrees(angle);

    }

    public int getComputerErrorCode() {
        // TODO Auto-generated method stub
        return ComputerErrorCode;

    }

    public boolean getContactLight() {
        return contactLight;
    }

    public int getErrorFreq(Error error) {
        if (errorFreqSent.get(error) == null) {
            return 0;
        } else {
            return errorFreqSent.get(error);

        }
    }

    public Error getErrorToSend() {
        // return currentError;
        try {
            return errorQue.take();
        } catch (final InterruptedException e) {
            // wont happen
            // TODO Auto-generated catch block
            e.printStackTrace(System.err);
            return null;
        }
    }

    public float getFuel() {
        return fuel;
    }

    public boolean getHasFuel() {
        return hasFuel;
    }

    public boolean getIsEasy() {
        // TODO Auto-generated method stub
        return isEasy;
    }

    public boolean getShowComputerError() {
        return showComputerError;
    }

    public float getVw() {
        return Vw;
    }

    public float getVx() {
        return Vx;
    }

    public float getVy() {
        return Vy;
    }

    public void reset() {
        Vx = 0;
        Vy = 0;
        Vw = 0;
        angle = 0;
        altitude = 0;
        errorQue = new LinkedBlockingQueue<Error>();
        fuel = FUEL_INITIAL;
        showComputerError = false;
        ComputerErrorCode = 0;

        hasFuel = true;
        indicesToRemove = new TreeSet<Integer>();
        errorFreqSent = new TreeMap<Error, Integer>();
        errors = new LinkedList<Error>();

    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setComputerErrorCode(int errorCode) {
        // TODO Auto-generated method stub
        ComputerErrorCode = errorCode;

    }

    public void setContactLight(boolean contactLight) {
        this.contactLight = contactLight;
    }

    public void setErrorReceived(Error error) {

        errors.addLast(error);

    }

    public void setErrorToSend(Error error) {
        if (error == null) {

        } else if (errorFreqSent.get(error) == null) {
            errorFreqSent.put(error, 1);

        } else {
            errorFreqSent.put(error, errorFreqSent.get(error) + 1);
        }
        try {
            errorQue.put(error);
        } catch (final InterruptedException e) {
            e.printStackTrace(System.err);
        }
    }

    public void setFuel(float fuel) {
        this.fuel = fuel;
    }

    //
    // public void setErrorsDone(Error err) {
    // errorsDone.addLast(err);
    // }
    // public Error getErrorsDone() {
    // return errorsDone.getLast();
    // }
    //
    // //TODO:fix exceptions
    // public void setError(Error error) {
    // try {
    // errorQue.put(error);
    // } catch (InterruptedException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace(System.err);
    // }
    // }
    // //TODO:FIx this encapsulation
    // public Error getError() {
    // // return currentError;
    // try {
    // return errorQue.take();
    // } catch (InterruptedException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace(System.err);
    // return null;
    // }
    // }
    // public Error getErrorAttempt() {
    // // return currentError;
    // try {
    // return errorQue.poll(1, TimeUnit.MILLISECONDS);
    // } catch (InterruptedException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace(System.err);
    // return null;
    // }
    // }
    //
    // public Error peekError() {
    // // TODO Auto-generated method stub
    // return errorQue.peek();
    // }

    public void setHasFuel(boolean hasFuel) {
        this.hasFuel = hasFuel;
    }

    public void setShowComputerError(boolean b) {
        showComputerError = b;
    }

    public void setVw(float Vw) {
        this.Vw = Vw;
    }

    public void setVx(float Vx) {
        this.Vx = Vx;

    }

    public void setVy(float Vy) {
        this.Vy = Vy;

    }

    // TODO:Still a bunch of stuff here re errors I'm not sure I'm using. Deal with later

}
