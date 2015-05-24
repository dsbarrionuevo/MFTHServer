package mfthserver.server;

import mfthserver.server.commands.Command;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Barrionuevo Diego
 */
public class DatagramController extends CommunicationController {

    private DatagramSocket socket;
    private InetAddress address;
    private int port;

    public DatagramController(DatagramSocket socket, InetAddress address, int port) {
        this.socket = socket;
        this.address = address;
        this.port = port;
    }

    @Override
    public void send(Command message) throws IOException {
        byte[] buffer = new byte[256];
        byte[] auxiliarBuffer = message.getRepresentation().getBytes(Charset.forName("UTF-8"));
        System.arraycopy(auxiliarBuffer, 0, buffer, 0, auxiliarBuffer.length);
        for (int i = auxiliarBuffer.length; i < 256; i++) {
            buffer[i] = 0;
        }
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(packet);
    }

    @Override
    public Command receive() throws IOException {
        byte[] buffer = new byte[256];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        String response = new String(buffer, Charset.forName("UTF-8"));
        return null;
    }

}
