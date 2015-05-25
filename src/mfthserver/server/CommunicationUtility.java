package mfthserver.server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;

/**
 *
 * @author Barrionuevo Diego
 */
public class CommunicationUtility {

    /**
     * Checks to see if a specific port is available. Source:
     * http://stackoverflow.com/questions/434718/sockets-discover-port-availability-using-java
     *
     * @param port the port to check for availability
     * @return true if the port given as parameter is free, false otherwise.
     */
    public static boolean isPortAvailable(int port) {
        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        } catch (IOException e) {
        } finally {
            if (ds != null) {
                ds.close();
            }
            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    /* should not be thrown */
                }
            }
        }
        return false;
    }
}
