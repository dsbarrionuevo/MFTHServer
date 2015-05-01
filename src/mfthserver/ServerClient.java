package mfthserver;

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
public class ServerClient implements Runnable {

    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private boolean connected;

    public ServerClient(Socket socket) {
        this.connected = true;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            introduceMyself();
            waitForResponse();
            //now I hace the both channels open: to listen and speak
            while (connected) {
                //escucho comandos y actuo en consecuencia
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeSocket();
        }
    }

    private void introduceMyself() throws IOException {
        this.output = new ObjectOutputStream(socket.getOutputStream());
        String greeting = "Hello!, I'm the server.";
        this.output.writeUTF(greeting);
        this.output.flush();
        System.out.println("Me: " + greeting);
    }

    private void waitForResponse() throws IOException {
        this.input = new ObjectInputStream(socket.getInputStream());
        String response = input.readUTF();
        System.out.println("Client: " + response);
    }

    private void closeSocket() {
        try {
            if (output != null) {
                output.close();
            }
            if (input != null) {
                input.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
