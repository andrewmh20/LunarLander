

import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JOptionPane;

public class ServerReader extends NetworkHandler implements Runnable {

    private final GameState gs;
    private final BufferedReader in;

    ServerReader(GameState gs, BufferedReader in) {
        this.gs = gs;
        this.in = in;
    }

    @Override
    public void run() {

        while (true) {
            // Bock until read avaliable
            try {
                final String lineIn = in.readLine();
                if (lineIn == null) {
                    throw new IOException("Client disconnected");
                } else {
                    final NetworkPacket packetIn = NetworkPacket.parse(lineIn);
                    gs.setErrorReceived(packetIn.getError());

                }

            } catch (final IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage() + " Simulation is now closing");
                if (!e.getMessage().equals("Client disconnected")){
                    e.printStackTrace(System.err);
                }
                System.exit(0);
            }
            // TODO: prevent the incessacnt dialogs!
            finally {
                // return;
            }

        }

    }

}
