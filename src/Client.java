import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JPanel;

public class Client extends NetworkHandler implements Runnable {

    private GameState gs;
    private Socket s;
    private JPanel telemetryPanel;

    Client(GameState gs, String hostname, JPanel telemetryPanel) throws UnknownHostException, IOException{
        super();
        this.gs = gs;
        s = new Socket(hostname, 8080);
        this.telemetryPanel = telemetryPanel;

        
    }
    
    //TODO: a main loop to read and write from the game_state every # of secs
    //TODO: client logic to handle that reading as well

    
    
    @Override
    public void run() {
        
    try {
        
            //TODO:use wait and notify to not need to have continuos loops just trying to send errors--wait until button actually pressed

    
        
    //Blocks waiting for a connection
    InputStream nis = s.getInputStream();
    OutputStream nos = s.getOutputStream();
    in = new BufferedReader(new InputStreamReader(nis));
    pw = new PrintWriter(nos);
    System.out.println("client setUp");
    
    //System.out.println(gs.getError());

    
    //TODO:Do stuff, can use network handler....
           
    //THIS is just testing the dynamic dispatch, not the netwroking. 
    //TODO: reall do network parsing/start to use packets.

    //TODO:this should realy use some sort of queue in gameState.
    System.out.println("here");

    
//Assume ready--unlike in server
    Thread cw = new Thread((new ClientWriter(gs, pw)));
    cw.start();
    Thread cr = new Thread((new ClientReader(gs, in, telemetryPanel)));
    cr.start();
    }
    
//while (true) {
//        try {
            
            //this blocks until there is an error avliable....
//            Error err = gs.getErrorAttempt();
//            System.out.println("Error:"+err);
//            if (err != null) {
//                
//
//            //TODO:more meaningful v's
//            //nos.write(new NetworkPacket(1,1,1,err).getPacket().getBytes());
//            pw.print(new NetworkPacket(1,1,1,err).getPacket());
//            pw.flush();
//            //System.out.println((new NetworkPacket(1,1,1,err)).getPacket().toString());
//            gs.setError(null);
//        
//        }
//        }
//
//        
//        catch (Exception e) {
//            e.printStackTrace(System.err);
//        }
        //get packets
//        if (ready()) {
//            try {
//              //This will hang if it's not receiving packets
//            if (in.ready()){
//                NetworkPacket packetIn = NetworkPacket.parse(in.readLine());
//                System.out.println(packetIn);
//            System.out.println("READY");
//
//            gs.setVx(packetIn.getVx());
//            gs.setVy(packetIn.getVy());
//            gs.setVw(packetIn.getVw());
//            
////
//            
//            //TODO:^^^use read pcaket, not reimpliment in.readLine parsing
//            }
//        }
//            catch (Exception e){
//e.printStackTrace(System.err);
//            }
//            
//        }
//}   

            



//    }

    
    catch (Exception e) {
       e.printStackTrace(System.err);
    }
    }
    //TODO:REmember to close the sockets correctly!
//}
    
}
