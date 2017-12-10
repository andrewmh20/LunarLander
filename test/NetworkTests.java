import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

public class NetworkTests {
//    
    String r;
//    @Test
//    public void acceptTest() throws IOException {
//        ServerSocket ss = new ServerSocket(8080);
//        
//        Socket connectedSocket = ss.accept();
//        InputStream nis = connectedSocket.getInputStream();
//        OutputStream nos = connectedSocket.getOutputStream();
//
//
//        r = nis.read();
//
//        while(r!= -1) {
//        //System.out.println(ss.accept().toString());
//        System.out.print((char)r);
//        nos.write((char)r);
//        r = nis.read();
//
//      
//       
//        
//        }
//        
//    }
    
    //TODO:Start and stop connections safely! Rememver to close
    @Test
    public void newNetworkPacket() throws IOException{
        
        ServerSocket ss = new ServerSocket(8083);
        Socket connectedSocket = ss.accept();
        InputStream nis = connectedSocket.getInputStream();
        //OutputStream nos = connectedSocket.getOutputStream();

        BufferedReader in
        = new BufferedReader(new InputStreamReader(nis));
       
        String r = in.readLine();
        
        
        //NetworkPacket np = new NetworkPacket(1,1,1);
        //nos.write(np.getPacket());
        
        while(r != null) {
        System.out.println(r);
        r = in.readLine();
        }
        

        
        
//        //r = nis.read();
//
//        //while(r!= -1) {
//        //System.out.println(ss.accept().toString());
//        //System.out.print((char)r);
//        //nos.write((char)r);
//        //r[i] = nis.read(i);
//        nos.write(np.getPacket());
//        
//        r = nis.read();
//        String npIn = "";
//        //TODO:way to end the packet
//        while(!(Character.isWhitespace(r))) {
//            npIn = npIn+ (char)r;
//            r = nis.read();
//        
//        }
//        //System.out.println(npIn.toString());
//        System.out.println(NetworkPacket.parse("<Vx>"));
//        //float Vx = NetworkPacket.parse(npIn).getVx();
//        //System.out.println(Vx);
//        
        ss.close();
        }
        
        

}
