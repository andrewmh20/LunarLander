import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

public class ClientReader extends NetworkHandler implements Runnable {

    private GameState gs;
    private BufferedReader in;
    private JPanel telemetryPanel;

    
    ClientReader(GameState gs, BufferedReader in, JPanel telemetryPanel){
        this.gs =gs;
        this.in = in;
        this.telemetryPanel = telemetryPanel;
    }
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        
        while(true) {
            //this blocks until ready--good!
          try {
            NetworkPacket packetIn = NetworkPacket.parse(in.readLine());
            gs.setVx(packetIn.getVx());
            gs.setVy(packetIn.getVy());
            gs.setVw(packetIn.getVw());
            telemetryPanel.repaint();
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


            
            
        }
        
    }

}
