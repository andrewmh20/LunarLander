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
                if ( packetIn.getError().isComputerError() ){
                    JOptionPane.showMessageDialog(null, "COMPUTER ERROR: " + packetIn.getError().getErrorCode());
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        
    }

}
