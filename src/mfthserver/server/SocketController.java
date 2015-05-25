package mfthserver.server;

import mfthserver.server.commands.Command;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Barrionuevo Diego
 */
public class SocketController extends CommunicationController {

    private static final String GREETINGS = "Hello!";
    public static final short HANDSHAKE_ORDER_FIRST_LISTEN_THEN_SPEAK = 0;
    public static final short HANDSHAKE_ORDER_FIRST_SPEAK_THEN_LISTEN = 1;
    //
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public SocketController(Socket socket) {
        this.socket = socket;
        this.outputStream = null;
        this.inputStream = null;
    }

    @Override
    public void send(Command communicable) throws IOException {
        String message = communicable.getRepresentation();
        this.outputStream.writeUTF(message);
        this.outputStream.flush();
        console(message);
    }

    @Override
    public Command receive() throws IOException {
        String response = inputStream.readUTF();
        return Command.create(response);
    }

    public void handshake() throws IOException {
        this.handshake(HANDSHAKE_ORDER_FIRST_SPEAK_THEN_LISTEN);
    }

    public void handshake(int order) throws IOException {
        if (order == HANDSHAKE_ORDER_FIRST_SPEAK_THEN_LISTEN) {
            introduceMyself();
            waitForResponse();
        } else {
            waitForResponse();
            introduceMyself();
        }
    }

    private void introduceMyself() throws IOException {
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        String greeting = GREETINGS;
        this.outputStream.writeUTF(greeting);
        this.outputStream.flush();
        console(greeting);
    }

    private void waitForResponse() throws IOException {
        //it blocks the thread till the other side awsner
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        String response = inputStream.readUTF();
        console(response);
    }

    public void close() throws IOException {
        if (outputStream != null) {
            outputStream.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
        if (socket != null) {
            socket.close();
        }
    }

    public Socket getSocket() {
        return socket;
    }

}
