package mfthserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import mfthserver.ServerClient;
import mfthserver.map.Map;
import mfthserver.player.Player;

/**
 *
 * @author Barrionuevo Diego
 */
public class Server {

    public static final int DEFAULT_PORT = 4646;
    private static Server me;
    private int port;
    private ServerSocket socket;
    private ArrayList<ServerClient> clients;
    //
    private Map map;

    public static Server getInstance() {
        if (me == null) {
            me = new Server(DEFAULT_PORT);
        }
        return me;
    }

    private Server(int port) {
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

    public Map getMap() {
        return map;
    }

    public int getClientsCount() {
        return clients.size();
    }

    public ServerClient getClient(int id) {
        Iterator<ServerClient> iterator = clients.iterator();
        while (iterator.hasNext()) {
            ServerClient current = iterator.next();
            if (current.getIdClient() == id) {
                return current;
            }
        }
        return null;
    }

    public void sendPlayerInfo(int clientId, Player player) {
        Iterator<ServerClient> iterator = clients.iterator();
        while (iterator.hasNext()) {
            ServerClient current = iterator.next();
            if (current.getIdClient() != clientId && current.getPlayer().getRoom().equals(player.getRoom())) {
                current.sendPlayerInfo(clientId, player);
            }
        }
    }

}
