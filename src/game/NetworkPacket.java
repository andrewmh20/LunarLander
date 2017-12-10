package game;
import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;
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
            Vx = Float.parseFloat(packetT.nextToken());
            Vy = Float.parseFloat(packetT.nextToken());
            Vw = Float.parseFloat(packetT.nextToken());
            angle = Float.parseFloat(packetT.nextToken());
            altitude = Float.parseFloat(packetT.nextToken());
            error = Error.getError(Integer.parseInt(packetT.nextToken()));
            fuel = Float.parseFloat(packetT.nextToken());

        }
        catch(NoSuchElementException e) {
            throw new IOException("Not enough tokens in network Packet");
            }
        catch(NumberFormatException e) {
            throw new IOException("Bad data format in netwotk Packet");
            }
        catch(Exception e) {
            throw new IOException("Issue with your network packet");
            }

           return new NetworkPacket(Vx,Vy, Vw, angle, altitude, error, fuel);
        
           }
    
    
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
    
    public Error getError() {

        int errorCode;
        if (error == null) {
            errorCode = 0;
        }
        else {
            errorCode = error.getErrorCode();
        }
        
        return Error.getError(errorCode);
    }
    
    public float getAltitude() {
        return altitude;
    }
    public float getFuel() {
        return fuel;
    }
    public float getAngle() {
        return angle;
    }
    
}
