import java.io.BufferedReader;
import java.io.IOException;

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
        // TODO Auto-generated method stub
        
        while(true) {
            //Bock until read avaliable
            try {
                NetworkPacket packetIn = NetworkPacket.parse(in.readLine());
                //ok to not sync because of way I am writing and reading data.....
                gs.setErrorReceived(packetIn.getError());
                //OKAY to conflate the network and graphics here?

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "You encountered a network error! See the console for more details.");

                e.printStackTrace();
            }

        }
        
    }

}
