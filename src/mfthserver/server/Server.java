package mfthserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mfthserver.ServerClient;
import mfthserver.map.Map;

/**
 *
 * @author Barrionuevo Diego
 */
public class Server {

    private int port;
    private ServerSocket socket;
    private ArrayList<ServerClient> clients;
    //
    private Map map;

    public Server(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
    }

    public void start() {
        try {
            socket = new ServerSocket(port);
            //1. Crea el mapa a partir del map.json.
            createMap();
            System.out.println("Map created!");
            //2. Entra en loop escuchando clientes:
            while (true) {
                Socket incomingSocket = socket.accept();
                ServerClient newClient = new ServerClient(incomingSocket, clients.size() + 1);
                newClient.setMap(map);
                clients.add(newClient);
                new Thread(newClient).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createMap() {
        map = new Map("res/maps/map1.txt");
    }

}
