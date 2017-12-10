package game;
import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import errors.Error;

public class NetworkPacket {
        
    
    private float altitude;
    private float Vx;
    private float Vy;
    private float Vw;
    private Error error;
    private float angle;
    private float fuel;
    

    NetworkPacket(float Vx, float Vy, float Vw, float angle, float altitude, Error error,
            float fuel){
        this.Vx = Vx;
        this.Vy = Vy;
        this.Vw = Vw;
        this.angle = angle;
        this.altitude = altitude;
        this.error = error;
        this.fuel = fuel;
    }
    
    public static NetworkPacket parse(String packet) throws IOException {
        if(!packet.contains("$")) {

            throw new IOException("Packet does not contain seperator!");
        }
        
        //TODO:Remember to handle this somehwere....IE in server where it calls this.
        StringTokenizer packetT = new StringTokenizer(packet,"$");
        //these are the fields to send to gameState
        float Vx = 0;
        float Vy = 0;
        float Vw = 0;
        float altitude = 0;
        float angle = 0;
        Error error = null;
        float fuel = 0;

        try {
            //TODO:Deal with when its malformed somehow, i.e. throw an exception
            Vx = Float.parseFloat(packetT.nextToken());
            Vy = Float.parseFloat(packetT.nextToken());
            Vw = Float.parseFloat(packetT.nextToken());
            angle = Float.parseFloat(packetT.nextToken());
            altitude = Float.parseFloat(packetT.nextToken());
            error = Error.getError(Integer.parseInt(packetT.nextToken()));
            fuel = Float.parseFloat(packetT.nextToken());

        }
        //TODO:make this better
        catch(Exception e) {
            System.out.println("not enough tokens");
            }
        
        //TODO: I have an issue with this parsing and null pointers, when error doesnt get set properly...
            
        //TODO:this scoping might be wrong, need to local versions
        //WILL BE all zeros if parsing failed
        return new NetworkPacket(Vx,Vy, Vw, angle, altitude, error, fuel);
        
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
        
        String packet = 
                "$"+Vx+"$"+Vy+"$"+Vw+"$"+angle+"$"+altitude+"$"+errorCode+"$"+fuel+"\n";
        
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
        int errorCode;
        //Encapsulate the error object
        //TODO:just change this whole logic to make it a constructor that takes an int..less
        //convoluted but now just work on what I need to
        if (error == null) {
            errorCode = 0;
        }
        else {
            errorCode = error.getErrorCode();
        }
        
        return Error.getError(errorCode);
    }
    
    //TODO:make this more informative and getPacket exactly how I want it sent
    public float getAltitude() {
        // TODO Auto-generated method stub
        return altitude;
    }
    public float getFuel() {
        // TODO Auto-generated method stub
        return fuel;
    }
    public float getAngle() {
        // TODO Auto-generated method stub
        return angle;
    }
    
}
