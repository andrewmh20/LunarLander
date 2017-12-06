import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Client extends NetworkHandler implements Runnable {

    private GameState gs;

    Client(GameState gs){
        
        super();
        this.gs = gs;
        
    }
    
    //TODO: a main loop to read and write from the game_state every # of secs
    //TODO: client logic to handle that reading as well

    
    
    @Override
    public void run() {
    try {
        
            Socket s = new Socket("127.0.0.1", 8080);
            //TODO:use wait and notify to not need to have continuos loops just trying to send errors--wait until button actually pressed

    
        
    //Blocks waiting for a connection
    InputStream nis = s.getInputStream();
    OutputStream nos = s.getOutputStream();
    in = new BufferedReader(new InputStreamReader(nis));
    pw = new PrintWriter(nos);
    
    //TODO:Do stuff, can use network handler....
           
    //THIS is just testing the dynamic dispatch, not the netwroking. 
    //TODO: reall do network parsing/start to use packets.

    while(true) {
        System.out.println("here");

        if(gs.getError() != null) {
            //TODO:more meaningful v's
            pw.write(new NetworkPacket(1,1,1,gs.getError()).getPacket());
            System.out.println(new NetworkPacket(1,1,1,gs.getError()).getPacket().toString());
            gs.setError(null);
        }
        /*
        if (ready()) {
            String packetIn = in.readLine();
            if(packetIn.equals("FULLTHROTTLE")) {
                gs.setError(new FullThrottleError());
                
            }
            if(packetIn.equals("RESET")) {
                gs.setError(new ResetErrors());
                
            }

           // System.out.println("TEST");
          //System.out.println(readPacket().toString());
            //pw.write(new NetworkPacket(String.valueOf(gs.getVy())).getPacket());
            try {
                NetworkPacket np = NetworkPacket.parse(packetIn);
            System.out.println(packetIn.toString());
            //gs.setVx(np.getVx());
            gs.setError(np.getError());
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
            //TODO:Does not always work, but mostly does! Issue with some packets not getting sent to gamestate rightly.
        //.setVy(Float.parseFloat(in.readLine()));
            
            //TODO:^^^use read pcaket, not reimpliment in.readLine parsing
            
            
        }*/
     }
    
    }

    
    catch (IOException e) {
       e.printStackTrace(System.err);
    }

}
    
}
