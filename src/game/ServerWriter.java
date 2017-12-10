import java.io.PrintWriter;

import javax.swing.JOptionPane;

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
                //Don't continuously send for performance reasons.
                Thread.sleep(100);
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null, "You encountered a network error! See the console for more details.");
                e.printStackTrace();
            }
        }
        
    }

}
