import java.io.PrintWriter;

public class ClientWriter extends NetworkHandler implements Runnable {

    private GameState gs;
    private PrintWriter pw;
    
    ClientWriter(GameState gs, PrintWriter pw){
        this.gs =gs;
        this.pw = pw;
    }
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        
        while(true) {
            //this blocks until ready--good!
            Error err = gs.getError();

            pw.print(new NetworkPacket(1,1,1,err).getPacket());
            pw.flush();

            
            
        }
        
    }

}
