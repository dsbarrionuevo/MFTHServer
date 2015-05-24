package mfthserver.server.commands;

import java.util.ArrayList;
import mfthserver.map.room.Room;
import mfthserver.player.Player;
import static mfthserver.server.commands.CommandFactory.COMMAND_INIT_PLAYER;
import org.json.JSONArray;
import org.json.JSONObject;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author Barrionuevo Diego
 */
public class CommandInitPlayer extends Command {

    private int roomId;
    private String roomSource;
    private int tileX, tileY;
    private ArrayList<Player> players;

    public CommandInitPlayer() {
        super(COMMAND_INIT_PLAYER);
    }

    public CommandInitPlayer(int roomId, String roomSource, int tileX, int tileY, ArrayList<Player> players) {
        this();
        this.roomId = roomId;
        this.roomSource = roomSource;
        this.tileX = tileX;
        this.tileY = tileY;
        this.players = players;
    }

    @Override
    public String getRepresentation() {
        String playersString = "[";
        for (int i = 0; i < players.size(); i++) {
            Player otherPlayer = players.get(i);
            playersString += "{id: " + otherPlayer.getId() + ", position:{x:" + otherPlayer.getPosition().x + ",y:" + otherPlayer.getPosition().y + "}}";
            if (i != players.size() - 1) {
                playersString += ",";
            }
        }
        playersString += "]";
        String toSend = "{command:"+COMMAND_INIT_PLAYER+", "
                + "room_id:" + roomId + ", "
                + "room_source: \"" + roomSource + "\", tile: {x: " + tileX + ", y: " + tileY + "},"
                + "room_players:" + playersString + " }";
        return toSend;
    }

    @Override
    public Command fromRepresentation(String representation) {
        JSONObject jsonCommand = new JSONObject(representation);
        JSONObject jsonTile = jsonCommand.getJSONObject("tile");
        JSONArray jsonPlayers = jsonCommand.getJSONArray("room_players");
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < jsonPlayers.length(); i++) {
            JSONObject jsonPlayer = jsonPlayers.getJSONObject(i);
            Player otherPlayer = new Player(jsonPlayer.getInt("id"));
            JSONObject jsonPosition = jsonPlayer.getJSONObject("position");
            otherPlayer.setPosition(new Vector2f((float) jsonPosition.getDouble("x"), (float) jsonPosition.getDouble("y")));
            players.add(otherPlayer);
        }
        return new CommandInitPlayer(jsonCommand.getInt("room_id"),
                jsonCommand.getString("room_source"),
                jsonTile.getInt("x"),
                jsonTile.getInt("y"),
                players);
    }

    public int getRoomId() {
        return roomId;
    }

    public String getRoomSource() {
        return roomSource;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

}
