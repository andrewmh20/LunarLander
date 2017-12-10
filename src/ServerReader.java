import java.io.BufferedReader;
import java.io.IOException;

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

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        
    }

}
