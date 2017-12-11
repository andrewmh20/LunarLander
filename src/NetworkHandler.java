

/**Class for handling the "server" side, i.e. lunarLander game networking

 * @author Tzvi
 *
 */

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkHandler {

    // Just one network handler in 1 game.
    protected static int LISTEN_PORT = 8080;

    // Only need to worry about one connection per Game...
    // Have this as a superclass and protected scope, because idea is that the subclasses
    // (writers and readers) use the same sockets and readers/writers.
    // In the end don't really need it, but keeping for use of ready();
    protected ServerSocket ss;
    protected Socket s;
    protected BufferedReader in;
    protected PrintWriter pw;

    public boolean ready() {
        if (in == null) {
            return false;
        } else {
            return true;
        }
    }

}
