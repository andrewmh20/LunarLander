import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

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
        // TODO Auto-generated method stub
        
        while(true) {
            //this blocks until ready--good!
          try {
            NetworkPacket packetIn = NetworkPacket.parse(in.readLine());
            gs.setVx(packetIn.getVx());
            gs.setVy(packetIn.getVy());
            gs.setVw(packetIn.getVw());
//TODO:this is weird, I can regactor to not need to do so many set gs and get gs, just set directly, but ok
            //FOr now move to graphics
            telemetryPanel.VxLabel.setText("Horizontal Velocity: " + String.format("%1$.2f",gs.getVx())); 
            telemetryPanel.VyLabel.setText("Vertical Velocity: " + String.format("%1$.2f",gs.getVy())); 
            telemetryPanel.VwLabel.setText("Angular Velocity: " + String.format("%1$.2f",gs.getVw())); 
            telemetryPanel.altLabel.setText("Altitude: " + String.format("%1$.2f",gs.getAltitude()));
            telemetryPanel.errorLabel.setText("Computer Error Code: " + gs.getComputerErrorCode());

          
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


            
            
        }
        
    }

}
