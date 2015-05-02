package mfthserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import mfthserver.map.Map;
import mfthserver.map.room.Room;
import mfthserver.map.tiles.Tile;
import mfthserver.player.Player;
import system.SystemIO;

/**
 *
 * @author Barrionuevo Diego
 */
public class ServerClient implements Runnable {

    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private boolean connected;
    //
    private int idClient;
    private Player player;
    private Map map;

    public ServerClient(Socket socket, int idClient) {
        this.connected = true;
        this.socket = socket;
        this.idClient = idClient;
        this.map = null;
    }

    @Override
    public void run() {
        try {
            introduceMyself();
            waitForResponse();
            //2.1 Enviar id de cliente.
            sendIdClient();
            //enviar json de mapa
            sendMapData();
            //2.2 Ubicarlo en alguna sala que no tenga el tesoro (y que preferentemente no tenga otro jugador).
            //2.3 Ubicarlo en un bloque de la sala.
            //2.4 Enviar habitacion actual para ese cliente.
            System.out.println("Try to place player " + idClient + " in room...");
            placeInRoom();
            //now I have the both channels open: to listen and speak
            while (connected) {
                //...
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeSocket();
        }
    }

    private void introduceMyself() throws IOException {
        this.output = new ObjectOutputStream(socket.getOutputStream());
        String greeting = "Hello!, I'm the server. Your id client is: " + idClient;
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

    private void sendIdClient() {
        sendJson("{command:'id_client', id_client:" + idClient + "}");
    }

    private void sendJson(String json) {
        try {
            this.output.writeUTF(json);
            this.output.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setMap(Map map) {
        this.map = map;
    }

    private void placeInRoom() {
        Room chosenRoom = map.getRoomForPlayer();
        Tile emptyTile = chosenRoom.getEmptyTile();
        System.out.println("Json to send: " + "{command:'init', id_room:" + chosenRoom.getRoomId() + ", tile: {x: " + emptyTile.getTileX() + ", y: " + emptyTile.getTileY() + "} }");
        sendJson("{command:'init', id_room:" + chosenRoom.getRoomId() + ", tile: {x: " + emptyTile.getTileX() + ", y: " + emptyTile.getTileY() + "} }");
    }

    private void sendMapData() {
        try {
            String mapData = SystemIO.readFile(map.getMapSource());
            sendJson("{command:'map_source',mapData: " + mapData + "}");
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
