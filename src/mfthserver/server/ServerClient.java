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
import mfthserver.server.Server;
import org.json.JSONObject;
import system.SystemIO;

/**
 *
 * @author Barrionuevo Diego
 */
public class ServerClient implements Runnable {

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean connected;
    //
    private int idClient;
    private Player player;
    private Map map;

    public ServerClient(Socket socket, int idClient) {
        this.connected = true;
        this.socket = socket;
        this.idClient = idClient;
        this.map = Server.getInstance().getMap();
    }

    @Override
    public void run() {
        try {
            introduceMyself();
            waitForResponse();
            //2.1 Enviar id de cliente.
            sendIdClient();
            //2.2 Ubicarlo en alguna sala que no tenga el tesoro (y que preferentemente no tenga otro jugador).
            //2.3 Ubicarlo en un bloque de la sala.
            //2.4 Enviar habitacion actual para ese cliente.
            System.out.println("Try to place player " + idClient + " in room...");
            setupPlayer();
            //now I have the both channels open: to listen and speak
            while (connected) {
                //game loop
                String jsonCommandString = inputStream.readUTF();
                JSONObject jsonCommand = new JSONObject(jsonCommandString);
                if (jsonCommand.getString("command").equals("move")) {
                    //set the id for client
                    int clientId = jsonCommand.getInt("client_id");
                    int direction = jsonCommand.getInt("direction");
                    System.out.println("Player (" + clientId + ") is trying to move to direction " + direction);
                    boolean canMove = player.move(direction);
                    //el server debe actualizar la posicion y mandarsela al cliente
                    sendJson("{command:'response_move', can_move:" + canMove + ", position: {x:" + player.getPosition().x + ", y:" + player.getPosition().y + "}}");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeSocket();
        }
    }

    private void introduceMyself() throws IOException {
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        String greeting = "Hello!, I'm the server. Your id client is: " + idClient;
        this.outputStream.writeUTF(greeting);
        this.outputStream.flush();
        System.out.println("Me: " + greeting);
    }

    private void waitForResponse() throws IOException {
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        String response = inputStream.readUTF();
        System.out.println("Client: " + response);
    }

    private void closeSocket() {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
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
        player = new Player();
    }

    private void sendJson(String json) {
        try {
            this.outputStream.writeUTF(json);
            this.outputStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void setupPlayer() {
        //create player
        player = new Player();
        //seek an empty room and select a begining tile for the player
        Room chosenRoom = map.getRoomForPlayer();
        Tile emptyTile = chosenRoom.getEmptyTile();
        String source = chosenRoom.getJsonString();
        //set x and y positions for player, also give him the room as reference
        chosenRoom.addObject(player, emptyTile.getTileX(), emptyTile.getTileY());
        String toSend = "{command:'init', id_room:" + chosenRoom.getRoomId() + ", room_source: \"" + source + "\", tile: {x: " + emptyTile.getTileX() + ", y: " + emptyTile.getTileY() + "} }";
        sendJson(toSend);
    }

    public int getIdClient() {
        return idClient;
    }

}
