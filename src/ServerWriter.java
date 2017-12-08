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
        
        while(true) {
            //TODO:when I deal with errors, set this to actually send the errorCode of what's happening
            pw.print(new NetworkPacket(gs.getVx(),gs.getVy(),gs.getVw(),
                    gs.getAngle(), gs.getAltitude(), null, gs.getFuel()).getPacket());
            pw.flush();
            try {
                //Don't continuously send
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }

}
