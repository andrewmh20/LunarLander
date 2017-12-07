import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.Timer;

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
//    try {
        
    try {
        ss = new ServerSocket(8080);
    
    //Blocks waiting for a connection
    Socket connectedSocket = ss.accept();
    InputStream nis = connectedSocket.getInputStream();
    OutputStream nos = connectedSocket.getOutputStream();
    in = new BufferedReader(new InputStreamReader(nis));
    pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(nos)));
    
    //TODO:Do stuff, can use network handler....
           
    //THIS is just testing the dynamic dispatch, not the netwroking. 
    //TODO: reall do network parsing/start to use packets.
    }
    catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    while(!ready()) {
     //Only start(below) when connection is ready   
    }
    
    Thread sw = new Thread((new ServerWriter(gs, pw)));
    sw.start();
    Thread sr = new Thread((new ServerReader(gs, in)));
    sr.start();

    
    }
    
//    while(true) {
//        //TODO:change to using blocking ques
//        if (ready()) {
//            try {
//                
////                System.out.println("ServerReady");
//                if (in.ready()) {
//                    
//               
//                NetworkPacket packetIn = NetworkPacket.parse(in.readLine());
//               // System.out.println(packetIn);
//                System.out.println("after in read");
//                //Test printing out only fater each read in
////                pw.print("THIS IS A TEST");
////                pw.flush();
//
//            System.out.println(packetIn.toString());
//            //gs.setVx(np.getVx());
//            gs.setError(packetIn.getError());
//                }
//            //TODO:^^^use read pcaket, not reimpliment in.readLine parsing
//            }
//            catch (Exception e){
//e.printStackTrace(System.err);
//            }
//            
//        }
//        //Send packets
//                    try {
////                System.out.println("SERVERREADYYYYY");
////                System.out.print(new NetworkPacket(gs.getVx(),gs.getVy(),gs.getVw(),null).getPacket());
////                pw.print(new NetworkPacket(gs.getVx(),gs.getVy(),gs.getVw(),null).getPacket());
////                pw.flush();
////                  nos.write(new NetworkPacket(gs.getVx(),gs.getVy(),gs.getVw(),null).getPacket().getBytes());
//                    }
////                    
////
////            
////
//            
//
//            
//            catch (Exception e) {
//                e.printStackTrace(System.err);
//            }   
//
//     }
//    
//    }
//
//    
//    catch (IOException e) {
//       e.printStackTrace(System.err);
//    }
////    
////    Timer timer = new Timer(1000, new ActionListener() {
////        public void actionPerformed(ActionEvent e) {
////            pw.print(new NetworkPacket(gs.getVx(),0,0,null).getPacket());
////            pw.flush();
////            System.out.println("TIMER");
////
////        }
////
//////
////    });
////    timer.start();
//
//
//}
    
 
    
}
