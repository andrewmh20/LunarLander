

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class NetworkPacket {

    public static NetworkPacket parse(String packet) throws IOException {
        if (!packet.contains("$")) {

            throw new IOException("Packet does not contain seperator!");
        }

        final StringTokenizer packetT = new StringTokenizer(packet, "$");
        // these are the fields to send to gameState
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

        } catch (final NoSuchElementException e) {
            throw new IOException("Not enough tokens in network Packet");
        } catch (final NumberFormatException e) {
            throw new IOException("Bad data format in netwotk Packet");
        } catch (final Exception e) {
            throw new IOException("Issue with your network packet");
        }

        return new NetworkPacket(Vx, Vy, Vw, angle, altitude, error, fuel);

    }

    private final float altitude;
    private final float Vx;
    private final float Vy;
    private final float Vw;
    private final Error error;
    private final float angle;

    private final float fuel;

    NetworkPacket(float Vx, float Vy, float Vw, float angle, float altitude, Error error,
            float fuel) {
        this.Vx = Vx;
        this.Vy = Vy;
        this.Vw = Vw;
        this.angle = angle;
        this.altitude = altitude;
        this.error = error;
        this.fuel = fuel;
    }

    public float getAltitude() {
        return altitude;
    }

    public float getAngle() {
        return angle;
    }

    public Error getError() {

        int errorCode;
        if (error == null) {
            errorCode = 0;
        } else {
            errorCode = error.getErrorCode();
        }

        return Error.getError(errorCode);
    }

    public float getFuel() {
        return fuel;
    }

    public String getPacket() {
        int errorCode;

        if (error == null) {
            errorCode = 0;
        } else {
            errorCode = error.getErrorCode();
        }

        final String packet = "$" + Vx + "$" + Vy + "$" + Vw + "$" + angle + "$" + altitude + "$"
                + errorCode + "$" + fuel + "\n";

        return packet;

    }

    public float getVw() {
        return Vw;
    }

    public float getVx() {
        return Vx;
    }

    public float getVy() {
        return Vy;
    }

}
