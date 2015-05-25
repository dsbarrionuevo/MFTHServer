package mfthserver.server;

import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Barrionuevo Diego
 */
public class SingletonDatagramController extends DatagramController {

    public SingletonDatagramController(DatagramSocket socket, InetAddress receptorAddress) {
        super(socket);
        this.receptorAddress = receptorAddress;
        this.receptorPort = INVALID_PORT;
    }
    
    

}
