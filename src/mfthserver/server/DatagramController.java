package mfthserver.server;

import mfthserver.server.commands.Command;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.Charset;

/**
 *
 * @author Barrionuevo Diego
 */
public class DatagramController extends CommunicationController {

    protected static final int INVALID_PORT = -1;

    protected DatagramSocket socket;
    protected InetAddress receptorAddress;
    protected int receptorPort;

    public DatagramController(DatagramSocket socket) {
        this.socket = socket;
        this.receptorAddress = null;
        this.receptorPort = INVALID_PORT;
    }

    @Override
    public void send(Command command) throws IOException {
        byte[] buffer = new byte[256];
        String message = command.getRepresentation();
        byte[] auxiliarBuffer = message.getBytes(Charset.forName("UTF-8"));
        System.arraycopy(auxiliarBuffer, 0, buffer, 0, auxiliarBuffer.length);
        for (int i = auxiliarBuffer.length; i < 256; i++) {
            buffer[i] = 0;
        }
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, receptorAddress, receptorPort);
        socket.send(packet);
        //console(message);
    }

    @Override
    public Command receive() throws IOException {
        byte[] buffer = new byte[256];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        String response = new String(buffer, Charset.forName("UTF-8"));
        return Command.create(response);
    }

    public void setReceptorAddress(InetAddress receptorAddress) {
        this.receptorAddress = receptorAddress;
    }

    public void setReceptorPort(int receptorPort) {
        this.receptorPort = receptorPort;
    }

    public boolean isReady() {
        return (receptorPort != INVALID_PORT && receptorAddress != null);
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public InetAddress getReceptorAddress() {
        return receptorAddress;
    }

    public int getReceptorPort() {
        return receptorPort;
    }

}
