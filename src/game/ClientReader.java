package game;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClientReader extends NetworkHandler implements Runnable {

    private GameState gs;
    private BufferedReader in;
    private TelemetryPanel telemetryPanel;

    
    ClientReader(GameState gs, BufferedReader in, TelemetryPanel telemetryPanel){
        this.gs =gs;
        this.in = in;
        this.telemetryPanel = telemetryPanel;
    }
    
    @Override
    public void run() {
        
        while(true) {
          try {
              
              String lineIn = in.readLine();
              if (lineIn == null) {
                  throw new IOException("Client disconnected");
                  }


            //this blocks until ready
            NetworkPacket packetIn = NetworkPacket.parse(lineIn);
            
            //Reason I do all this setting and getting is so that I could use the data later in other 
            //ways if I wanted. Only issue is it leaves room for things to get messed up within the GameState
            gs.setVx(packetIn.getVx());
            gs.setVy(packetIn.getVy());
            gs.setVw(packetIn.getVw());
            gs.setAltitude(packetIn.getAltitude());
            gs.setFuel(packetIn.getFuel());
            gs.setAngle(packetIn.getAngle());

            telemetryPanel.updateTelemetryPanel();
          
          } catch (IOException e) {
              JOptionPane.showMessageDialog(null, e.getMessage() + " Simulation is now closing");
              e.printStackTrace();

              System.exit(0);            }


            
            
        }
        
    }

}
