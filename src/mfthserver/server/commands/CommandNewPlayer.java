package mfthserver.server.commands;

import mfthserver.map.tiles.BlankTile;
import mfthserver.map.tiles.Tile;
import org.json.JSONObject;
import static sun.audio.AudioPlayer.player;

/**
 *
 * @author Barrionuevo Diego
 */
public class CommandNewPlayer extends Command {

    private int clientId;
    private int tileX, tileY;
    private int roomId;

    public CommandNewPlayer() {
        super(CommandFactory.COMMAND_NEW_PLAYER);
    }

    public CommandNewPlayer(int clientId, int tileX, int tileY, int roomId) {
        super(CommandFactory.COMMAND_NEW_PLAYER);
        this.clientId = clientId;
        this.tileX = tileX;
        this.tileY = tileY;
        this.roomId = roomId;
    }

    @Override
    public String getRepresentation() {
        return "{command:" + CommandFactory.COMMAND_NEW_PLAYER + ",client_id:" + clientId + ",tile: {x:'+" + tileX + "',y:'+" + tileY + "'}, room_id: " + roomId + "}";
    }

    @Override
    public Command fromRepresentation(String representation) {
        CommandNewPlayer result;
        JSONObject rootJson = new JSONObject(representation);
        JSONObject tileJson = rootJson.getJSONObject("tile");
        result = new CommandNewPlayer(rootJson.getInt("client_id"),
                tileJson.getInt("x"),
                tileJson.getInt("y"),
                rootJson.getInt("room_id"));
        return result;
    }

    public int getClientId() {
        return clientId;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public int getRoomId() {
        return roomId;
    }

}
