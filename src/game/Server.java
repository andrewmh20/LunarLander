package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends NetworkHandler implements Runnable {

    private final GameState gs;

    Socket connectedSocket = null;

    Server(GameState gs) throws IOException {

        super();
        this.gs = gs;

        ss = new ServerSocket(8080);

    }

    @Override
    public void run() {
        // try {

        try {

            // Blocks waiting for a connection
            connectedSocket = ss.accept();
            final InputStream nis = connectedSocket.getInputStream();
            final OutputStream nos = connectedSocket.getOutputStream();
            in = new BufferedReader(new InputStreamReader(nis));
            pw = new PrintWriter(
                    new BufferedWriter(new OutputStreamWriter(nos)));
        }
        // TODO:Do stuff, can use network handler....

        // THIS is just testing the dynamic dispatch, not the netwroking.
        // TODO: reall do network parsing/start to use packets.

        catch (final IOException e) {
            e.printStackTrace(System.err);
        }
        while (!ready()) {
            // Only start(below) when connection is ready
        }

        final Thread sw = new Thread((new ServerWriter(gs, pw)));
        sw.start();
        final Thread sr = new Thread((new ServerReader(gs, in)));
        sr.start();

        // go bcack to waiting for conncetion

    }

}
