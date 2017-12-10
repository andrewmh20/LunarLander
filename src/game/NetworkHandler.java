package game;

/**Class for handling the "server" side, i.e. lunarLander game networking

 * @author Tzvi
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.concurrent.BlockingQueue;

//TODO: unecessary
public abstract class NetworkHandler {

    
    //Jsut one network handler in 1 game.
    protected static int LISTEN_PORT = 8080;

    //Only need to worry about one connection per Game...
    //TODO:Remember to handle multiple connectins attempted.
    protected ServerSocket ss;
    protected Socket s;
    protected BufferedReader in;
    protected PrintWriter pw;
    
    //BC whole point is that ots the subclass instance that really uses these fields
    
    //TODO: Abstract bc it implements stuff, but cannot do anything until a thread is run which
    //sets up its fields.
    
    
    //TODO:Printing and threads.....?!
    
        
        //TODO:Doesnt really return connected, just if a reader exists
        public boolean ready() {
            if (in == null){
                return false;
            }
            else {
                return true;
            }
        }
////
////        
////        public void writePacket(NetworkPacket packet){
////            pw.write(packet.getPacket());
////            
////        }
////        //TODO:handle exception if possible
////        public NetworkPacket readPacket() throws IOException {
////            String r;
////            
////               r = in.readLine();
////            String packetStr = "";
////            while(r != null) {
////                packetStr = packetStr + r;
////                return new NetworkPacket(packetStr);
////
////                //TODO:Handle the packet such that it can have newlines.....
////                //r = in.readLine();
////                }
////            return null;
////            
////            //TODO:Fix the packet that's actually created in NetworkPacket....!
////            
//        }
        
        //TODO:Fix scope of all the methods in here...what is constructor?
        
        //TODO:get from a dialog box
        private final static String HOST = "localhost";
        private final static int CONNECTION_PORT = LISTEN_PORT;

     
        
    
    
    
}
