package game;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends NetworkHandler implements Runnable {

    // TODO: test all this on an actual network.....uh oh.
    private final GameState gs;
    private final Socket s;
    private final TelemetryPanel telemetryPanel;

    Client(GameState gs, String hostname, TelemetryPanel telemetryPanel)
            throws UnknownHostException, IOException {
        super();
        this.gs = gs;
        s = new Socket(hostname, 8080);
        this.telemetryPanel = telemetryPanel;

    }

    // TODO: a main loop to read and write from the game_state every # of secs
    // TODO: client logic to handle that reading as well

    @Override
    public void run() {

        try {

            // Blocks waiting for a connection
            final InputStream nis = s.getInputStream();
            final OutputStream nos = s.getOutputStream();
            in = new BufferedReader(new InputStreamReader(nis));
            pw = new PrintWriter(nos);

            // Assume ready--unlike in server
            final Thread cw = new Thread((new ClientWriter(gs, pw)));
            cw.start();
            final Thread cr = new Thread((new ClientReader(gs, in, telemetryPanel)));
            cr.start();
        }

        catch (final Exception e) {
            e.printStackTrace(System.err);
        }
    }

}
