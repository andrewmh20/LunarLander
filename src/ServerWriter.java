import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ServerWriter extends NetworkHandler implements Runnable {

    
    private GameState gs;
    private PrintWriter pw;

    
    ServerWriter(GameState gs, PrintWriter pw){
        this.gs = gs;
        this.pw = pw;
    }
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        
        while(true) {
            pw.print(new NetworkPacket(gs.getVx(),gs.getVy(),gs.getVw(),null).getPacket());
            pw.flush();

        }
        
    }

}
