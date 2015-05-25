package mfthserver.server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import mfthserver.map.Map;
import mfthserver.player.Player;
import mfthserver.server.commands.Command;

/**
 *
 * @author Barrionuevo Diego
 */
public class Server {

    public static final int DEFAULT_PORT = 4646;
    //
    private static Server me;
    private int port;
    private ServerSocket serverSocket;
    private DatagramSocket datagramSocket;
    private DatagramBroker datagramBroker;
    private ArrayList<ServerClient> clients;
    //
    private Map map;
    private static int playersCount;//gives the next id, without reuing old ones

    public static Server getInstance() {
        if (me == null) {
            me = new Server(DEFAULT_PORT);
        }
        return me;
    }

    private Server(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
        Server.playersCount = 0;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            datagramSocket = new DatagramSocket(port, InetAddress.getByName("localhost"));
            datagramBroker = new DatagramBroker(datagramSocket);
            datagramBroker.listen();
            //1. Crea el mapa a partir del map.json.
            createMap();
            System.out.println("Map created!");
            //2. Entra en loop escuchando clientes:
            while (true) {
                Socket incomingSocket = serverSocket.accept();
                ServerClient newClient = new ServerClient(incomingSocket, datagramSocket, Server.playersCount);
                Server.playersCount = Server.playersCount + 1;
                clients.add(newClient);
                newClient.start();
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
            if (current.getId() == id) {
                return current;
            }
        }
        return null;
    }

    public void sendPlayerInfo(Player player) throws IOException {
        Iterator<ServerClient> iterator = clients.iterator();
        while (iterator.hasNext()) {
            ServerClient current = iterator.next();
            if (current.getId() != player.getId() && current.getPlayer().getRoom().equals(player.getRoom())) {
                current.sendPlayerInfo(player);
            }
        }
    }

    public void sendPacketToAll(Command command) throws IOException {
        for (int i = 0; i < clients.size(); i++) {
            clients.get(i).sendPacket(command);
        }
    }

    public void sendPacketToAllExceptMe(Command command, ServerClient except) throws IOException {
        for (int i = 0; i < clients.size(); i++) {
            if (!clients.get(i).equals(except)) {
                clients.get(i).sendPacket(command);
            }
        }
    }

    public void sendDatagramToAll(Command command) throws IOException {
        for (int i = 0; i < clients.size(); i++) {
            clients.get(i).sendDatagram(command);
        }
    }

    public void sendDatagramToAllExceptMe(Command command, ServerClient except) throws IOException {
        for (int i = 0; i < clients.size(); i++) {
            if (!clients.get(i).equals(except)) {
                clients.get(i).sendDatagram(command);
            }
        }
    }

    public void removeClient(int clientId) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getId() == clientId) {
                System.out.println("CLIENT " + clientId + " 'S JUST DISCONNECTED FROM SERVER");
                clients.remove(clients.get(i));
            }
        }
    }

}
