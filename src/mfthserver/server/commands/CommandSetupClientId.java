package mfthserver.server.commands;

import static mfthserver.server.commands.CommandFactory.COMMAND_CLIENT_ID;
import org.json.JSONObject;

/**
 *
 * @author Barrionuevo Diego
 */
public class CommandSetupClientId extends Command {

    private int clientId;

    public CommandSetupClientId() {
        super(COMMAND_CLIENT_ID);
    }

    public CommandSetupClientId(int clientId) {
        super(COMMAND_CLIENT_ID);
        this.clientId = clientId;
    }

    @Override
    public String getRepresentation() {
        return "{command:"+COMMAND_CLIENT_ID+", id_client:" + clientId + "}";
    }

    @Override
    public Command fromRepresentation(String representation) {
        CommandSetupClientId result;
        JSONObject rootJson = new JSONObject(representation);
        result = new CommandSetupClientId(rootJson.getInt("id_client"));
        return result;
    }

    public int getClientId() {
        return clientId;
    }
}
