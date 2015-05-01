package mfthserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Barrionuevo Diego
 */
public class Server {

    private int port;
    private ServerSocket socket;
    private ArrayList<ServerClient> clients;

    public Server(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
    }

    public void start() {
        try {
            socket = new ServerSocket(port);
            while (true) {
                Socket incomingSocket = socket.accept();
                ServerClient newClient = new ServerClient(incomingSocket);
                clients.add(newClient);
                new Thread(newClient).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
