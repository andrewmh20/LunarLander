package game;

import java.io.PrintWriter;

import errors.Error;

public class ClientWriter extends NetworkHandler implements Runnable {

    private final GameState gs;
    private final PrintWriter pw;

    ClientWriter(GameState gs, PrintWriter pw) {
        this.gs = gs;
        this.pw = pw;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

        while (true) {
            // this blocks until ready--good!
            final Error err = gs.getErrorToSend();

            // TODO:this.
            pw.print(new NetworkPacket(1, 1, 1, 1, 1, err, 1).getPacket());
            pw.flush();

        }

    }

}
