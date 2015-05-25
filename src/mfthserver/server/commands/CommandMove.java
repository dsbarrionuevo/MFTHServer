package mfthserver.server.commands;

import org.json.JSONObject;

/**
 *
 * @author Barrionuevo Diego
 */
public class CommandMove extends Command implements Identificable{

    private int clientId;
    private int direction;

    public CommandMove() {
        super(CommandFactory.COMMAND_MOVE);
    }

    public CommandMove(int clientId, int direction) {
        this();
        this.clientId = clientId;
        this.direction = direction;
    }

    @Override
    public String getRepresentation() {
        return "{command:"+CommandFactory.COMMAND_MOVE+", client_id:" + clientId + ", direction:" + direction + "}";
    }

    @Override
    public Command fromRepresentation(String representation) {
        CommandMove result;
        JSONObject jsonCommand = new JSONObject(representation);
        result = new CommandMove(jsonCommand.getInt("client_id"), jsonCommand.getInt("direction"));
        return result;
    }

    @Override
    public int getClientId() {
        return clientId;
    }

    public int getDirection() {
        return direction;
    }

}
