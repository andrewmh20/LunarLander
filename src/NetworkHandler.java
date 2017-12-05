
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

public class NetworkHandler implements Runnable {

    
    //Jsut one network handler in 1 game.
    private static int LISTEN_PORT = 8080;
    //Only need to worry about one connection per Game...
    //TODO:Remember to handle multiple connectins attempted.
    private ServerSocket ss;
    private BufferedReader in;
    private PrintWriter pw;
    
    //TODO:Printing and threads.....?!
    
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
               
        }

        
        catch (Exception IOException) {
            throw new RuntimeException();
        }

    }
        //TODO:Doesnt really return connected, just if a reader exists
        public boolean ready() {
            if (in == null){
                return false;
            }
            else {
                return true;
            }
        }

        
        public void writePacket(NetworkPacket packet){
            pw.write(packet.getPacket());
            
        }
        //TODO:handle exception if possible
        public NetworkPacket readPacket() throws IOException {
            String r;
            
               r = in.readLine();
            String packetStr = "";
            while(r != null) {
                r = in.readLine();
                packetStr = packetStr + r;
                }
            return new NetworkPacket(packetStr);
            
        }
        
        //TODO:Fix scope of all the methods in here...what is constructor?
        
        //TODO:get from a dialog box
        private final static String HOST = "localhost";
        private final static int CONNECTION_PORT = LISTEN_PORT;

        //TODO:THIS SHOULD BE SOEMWHER EELSE. the handler is just for serve side stuff, writing BACK to the client
        public void connect() throws IOException {
            Socket s = new Socket(HOST, CONNECTION_PORT);
            
        }
        
    
    
    
}
