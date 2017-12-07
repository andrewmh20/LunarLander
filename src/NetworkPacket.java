import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class NetworkPacket {
        
    
    private float altitude;
    private float Vx;
    private float Vy;
    private float Vw;
    private Error error;
    

    NetworkPacket(float Vx, float Vy, float Vw, Error error){
        this.Vx = Vx;
        this.Vy = Vy;
        this.Vw = Vw;
        this.error = error;
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
         //TODO:add more fields
        //these are the fields to send to gameState
         float Vx=0;
         float Vy=0;
         float Vw=0;
         Error error = null;

        
        try {
            //TODO:Handle when its malformed
            Vx = Float.parseFloat(packetT.nextToken());
            Vy = Float.parseFloat(packetT.nextToken());
            Vw = Float.parseFloat(packetT.nextToken());
            error = Error.getError(Integer.parseInt(packetT.nextToken()));
            
            //TODO:
        }
        //TODO:make this better
        catch(Exception e) {
            System.out.println("not enough tokens");
            }
        
        //TODO: I have an issue with this parsing and null pointers, when error doesnt get set properly...
            
        //TODO:this scoping might be wrong, need to local versions
        //WILL BE all zeros if parsing failed
        return new NetworkPacket(Vx,Vy, Vw, error);
        
           }
    
    //TODO:this needs to be really updated
    
    public String getPacket(){
        int errorCode;
        
        if (error == null) {
            errorCode = 0;
        }
        else {
            errorCode = error.getErrorCode();
        }
        //TODO:think I can remove this newline
        String packet = "$"+Vx+"$"+Vy+"$"+Vw+"$"+errorCode+"\n";
        
        return packet;
        
        
    }
    
    public float getVx() {
        return Vx;
    }
    
    public float getVy() {
        return Vy;
    }
    public float getVw() {
        return Vw;
    }
    
    //TODO:Okay to return the actual error here?
    public Error getError() {
        return error;
    }
    @Override
    //TODO:make this more informative and getPacket exactly how I want it sent
    public String toString() {
        return getPacket();
        
        /*String packet = "<Vx>"+Vx+"</Vx>"+
                        "<Vy>"+Vy+"</Vy>"+
                        "<Vw>"+Vw+"</Vw>"+
                        "<error>"+error.getErrorCode()+"</error>";
        
        return packet;*/
        
    }
    
}
