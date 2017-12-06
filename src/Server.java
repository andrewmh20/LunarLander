import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends NetworkHandler implements Runnable {

    private GameState gs;

    Server(GameState gs){
        
        super();
        this.gs = gs;
        
    }
    
    //TODO: a main loop to read and write from the game_state every # of secs
    //TODO: client logic to handle that reading as well

    
    
    @Override
    public void run() {
    try {
        
    ss = new ServerSocket(8080);
    //Blocks waiting for a connection
    Socket connectedSocket = ss.accept();
    InputStream nis = connectedSocket.getInputStream();
    OutputStream nos = connectedSocket.getOutputStream();
    in = new BufferedReader(new InputStreamReader(nis));
    pw = new PrintWriter(nos);
    
    //TODO:Do stuff, can use network handler....
           
    //THIS is just testing the dynamic dispatch, not the netwroking. 
    //TODO: reall do network parsing/start to use packets.

    while(true) {
        //TODO:change to using blocking ques
        if (ready()) {
            try {
                
                System.out.println("ServerReady");
                NetworkPacket packetIn = NetworkPacket.parse(in.readLine());
               // System.out.println(packetIn);
                System.out.println("after in read");
                
            System.out.println(packetIn.toString());
            //gs.setVx(np.getVx());
            gs.setError(packetIn.getError());
            
            //TODO:^^^use read pcaket, not reimpliment in.readLine parsing
            }
            catch (Exception e){
e.printStackTrace(System.err);
            }
            
        }
     }
    
    }

    
    catch (IOException e) {
       e.printStackTrace(System.err);
    }

}
    
}
