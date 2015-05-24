package mfthserver.server.commands;

import org.json.JSONObject;

/**
 *
 * @author Barrionuevo Diego
 */
public class CommandDisconnectResponse extends Command {

    private int clientId;
    private int roomId;

    public CommandDisconnectResponse() {
        super(CommandFactory.COMMAND_DISCONNECT_RESPONSE);
    }

    public CommandDisconnectResponse(int clientId, int roomId) {
        this();
        this.clientId = clientId;
        this.roomId = roomId;
    }

    @Override
    public String getRepresentation() {
        return "{command:" + CommandFactory.COMMAND_DISCONNECT_RESPONSE + ", client_id: " + clientId + ", room_id: " + roomId + "}";
    }

    @Override
    public Command fromRepresentation(String representation) {
        JSONObject jsonCommand = new JSONObject(representation);
        return new CommandDisconnectResponse(jsonCommand.getInt("client_id"), jsonCommand.getInt("room_id"));
    }

    public int getClientId() {
        return clientId;
    }

    public int getRoomId() {
        return roomId;
    }

}
