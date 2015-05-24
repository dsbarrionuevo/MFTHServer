package mfthserver.server.commands;

import org.json.JSONObject;

/**
 *
 * @author Barrionuevo Diego
 */
public class CommandDisconnect extends Command {

    private int clientId;
    private int roomId;

    public CommandDisconnect() {
        super(CommandFactory.COMMAND_DISCONNECT);
    }

    public CommandDisconnect(int clientId, int roomId) {
        this();
        this.clientId = clientId;
        this.roomId = roomId;
    }

    @Override
    public String getRepresentation() {
        return "{command:"+CommandFactory.COMMAND_DISCONNECT+", client_id: " + clientId + ", room_id: " + roomId + "}";
    }

    @Override
    public Command fromRepresentation(String representation) {
        CommandDisconnect result = null;
        JSONObject jsonCommand = new JSONObject(representation);
        result = new CommandDisconnect(jsonCommand.getInt("client_id"), jsonCommand.getInt("room_id"));
        return result;
    }

    public int getClientId() {
        return clientId;
    }

    public int getRoomId() {
        return roomId;
    }

}
