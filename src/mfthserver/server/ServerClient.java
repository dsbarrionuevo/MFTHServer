package mfthserver.server;

import mfthserver.server.commands.*;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mfthserver.map.room.Room;
import mfthserver.map.tiles.Tile;
import mfthserver.player.Player;
import static mfthserver.server.commands.CommandFactory.*;
import mfthserver.server.commands.CommandNewPlayer;

/**
 *
 * @author Barrionuevo Diego
 */
public class ServerClient {

    private SocketController socketController;
    private DatagramController datagramController;
    private boolean connected;
    //
    private Player player;

    public ServerClient(Socket socket, DatagramSocket datagramSocket, int clientId) throws SocketException, UnknownHostException {
        this.socketController = new SocketController(socket);
        this.datagramController = new DatagramController(datagramSocket);
        this.connected = true;
        this.player = new Player(clientId);
    }

    public void start() {
        //TCP listen thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //handshake for tcp
                    socketController.handshake();
                    //send id client/player
                    //2.1 Enviar id de cliente.
                    sendIdClient();
                    //place player on a random room and send info about that room (his position, players, enemies)
                    //2.2 Ubicarlo en alguna sala que no tenga el tesoro (y que preferentemente no tenga otro jugador).
                    //2.3 Ubicarlo en un bloque de la sala.
                    //2.4 Enviar habitacion actual para ese cliente.
                    setupPlayer();
                    //send player data to all of the other players
                    Server.getInstance().sendPlayerInfo(player);
                    while (connected) {
                        Command command = socketController.receive();
                        switch (command.getType()) {
                            case (COMMAND_NETWORK_INFORMATION):
                                CommandNetworkInformation commandNetworkInformation = (CommandNetworkInformation) command;
                                datagramController.setReceptorAddress(socketController.getSocket().getInetAddress());
                                datagramController.setReceptorPort(commandNetworkInformation.getListenPortUDP());
                                break;
                            case (COMMAND_DISCONNECT):
                                CommandDisconnect disconnectCommand = (CommandDisconnect) command;
                                Room roomWherePlayerStayed = Server.getInstance().getMap().findRoomById(disconnectCommand.getRoomId());
                                if (roomWherePlayerStayed != null) {
                                    roomWherePlayerStayed.removeObject(roomWherePlayerStayed.getPlayerById(disconnectCommand.getClientId()));
                                }
                                Server.getInstance().sendPacketToAllExceptMe(new CommandDisconnectResponse(disconnectCommand.getClientId(), disconnectCommand.getRoomId()), getMe());
                                disconnect();
                                break;
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    closeSocket();
                }
            }
        }
        ).start();
        //UDP listen thread here... instead use receiveDatagram
    }

    public void receiveDatagram(Identificable identificable) {
        try {
            Command command = (Command) identificable;
            switch (command.getType()) {
                case (COMMAND_MOVE):
                    boolean canMove = player.move(((CommandMove) command).getDirection());
                    if (canMove) {
                        sendDatagram(new CommandMoveResponse(getId(), canMove, player.getPosition()));
                        Server.getInstance().sendDatagramToAllExceptMe(new CommandMoveResponse(getId(), canMove, player.getPosition()), getMe());
                    }
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendIdClient() throws IOException {
        socketController.send(new CommandSetupClientId(getId()));
    }

    private void setupPlayer() throws IOException {
        //seek an empty room and select a begining tile for the player
        Room chosenRoom = Server.getInstance().getMap().getRoomForPlayer();
        Tile emptyTile = chosenRoom.getEmptyTile();
        //others in the room
        ArrayList<Player> others = chosenRoom.getPlayers();
        //set x and y positions for player, also give him the room as reference
        chosenRoom.addObject(player, emptyTile.getTileX(), emptyTile.getTileY());
        sendPacket(new CommandInitPlayer(chosenRoom.getRoomId(), chosenRoom.getJsonString(), emptyTile.getTileX(), emptyTile.getTileY(), others));
    }

    public void sendPacket(Command command) throws IOException {
        socketController.send(command);
    }

    public void sendDatagram(Command command) throws IOException {
        if (datagramController.isReady()) {
            datagramController.send(command);
        }
    }

    public void sendPlayerInfo(Player player) throws IOException {
        Tile tile = player.getRoom().getCurrentTile(player);
        this.sendPacket(new CommandNewPlayer(player.getId(), tile.getTileX(), tile.getTileY(), player.getRoom().getRoomId()));
    }

    public Player getPlayer() {
        return player;
    }

    public int getId() {
        return player.getId();
    }

    private ServerClient getMe() {
        return this;
    }

    private void disconnect() {
        connected = false;
        closeSocket();
        Server.getInstance().removeClient(getId());
    }

    @Override
    public boolean equals(Object obj) {
        return ((ServerClient) obj).getId() == getId();
    }

    private void closeSocket() {
        try {
            socketController.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerClient.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
