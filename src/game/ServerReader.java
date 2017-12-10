package game;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ServerReader extends NetworkHandler implements Runnable {

    
    private GameState gs;
    private BufferedReader in;

    
    ServerReader(GameState gs, BufferedReader in){
        this.gs = gs;
        this.in = in;
    }
    
    @Override
    public void run() {
        
        while(true) {
            //Bock until read avaliable
            try {
                String lineIn = in.readLine();
                if (lineIn == null) {
                    throw new IOException("Client disconnected");
                }
                else {
                    NetworkPacket packetIn = NetworkPacket.parse(lineIn);
                    gs.setErrorReceived(packetIn.getError());

                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage() + " Simulation is now closing");
                e.printStackTrace();
                System.exit(0);            }
            //TODO: prevent the incessacnt dialogs!
            finally{
               // return;
            }

        }
        
    }

}
