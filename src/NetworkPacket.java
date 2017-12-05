
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
        new NetworkPacket(100000,1000000,10000);
    }
    
    //TODO:Other setters for other fields
    
    public static NetworkPacket parse(String packet) {
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
