import java.io.IOException;
import java.util.StringTokenizer;

public class NetworkPacket {
    
    private float altitude;
    private float Vx;
    private float Vy;
    private float Vw;

    NetworkPacket(float Vx, float Vy, float Vw){
        this.Vx = Vx;
        this.Vy = Vy;
        this.Vw = Vw;
    }
    NetworkPacket(String packetStr){
        //TODO:make this parse the packet, throw exceptions as necessary 
        this.Vx = 10000;
        this.Vy = Float.parseFloat(packetStr);
        this.Vw = 10000;
    }
    
    //TODO:Other setters for other fields
    
    public static NetworkPacket parse(String packet) throws IOException {
        if(!packet.contains("$")) {
            //TODO:Or make these a different type of exception
            throw new IOException("Packet does not contain seperator!");
        }
        
        //TODO:Remember to handle this somehwere....IE in server where it calls this.
        StringTokenizer packetT = new StringTokenizer(packet,"$");
        
        while(packetT.hasMoreTokens()) {
            System.out.println(packetT.nextToken());
            //TODO:
        }
        
        
       
        
        return null;
           }
    
    public String getPacket(){
        String packet = "<Vx>"+Vx+"</Vx>"+
                        "<Vy>"+Vy+"</Vy>"+
                        "<Vw>"+Vw+"</Vw>";
        
        return packet;
        
        
    }
    
    public float getVx() {
        return Vx;
    }
    @Override
    public String toString() {
        return getPacket();
        
    }
    
}
