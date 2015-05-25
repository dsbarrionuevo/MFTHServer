package mfthserver.server.commands;

import org.json.JSONObject;

/**
 *
 * @author Barrionuevo Diego
 */
public class CommandFactory extends CommandFactoryMethod {

    public static final int COMMAND_INVALID = -1;
    public static final int COMMAND_CLIENT_ID = 0;
    public static final int COMMAND_INIT_PLAYER = 1;
    public static final int COMMAND_NEW_PLAYER = 2;
    public static final int COMMAND_MOVE = 3;
    public static final int COMMAND_MOVE_REPSONSE = 5;
    public static final int COMMAND_DISCONNECT = 4;
    public static final int COMMAND_DISCONNECT_RESPONSE = 6;
    public static final int COMMAND_NETWORK_INFORMATION = 7;
    //
    public static CommandFactory me;

    private CommandFactory() {

    }

    public static CommandFactory getInstance() {
        if (me == null) {
            me = new CommandFactory();
        }
        return me;
    }

    @Override
    public Command create(String representation) {
        Command result;
        JSONObject rootJson = new JSONObject(representation);
        int type = rootJson.getInt("command");
        switch (type) {
            case (COMMAND_CLIENT_ID):
                result = new CommandSetupClientId();
                break;
            case (COMMAND_INIT_PLAYER):
                result = new CommandInitPlayer();
                break;
            case (COMMAND_NEW_PLAYER):
                result = new CommandNewPlayer();
                break;
            case (COMMAND_MOVE):
                result = new CommandMove();
                break;
            case (COMMAND_MOVE_REPSONSE):
                result = new CommandMoveResponse();
                break;
            case (COMMAND_DISCONNECT):
                result = new CommandDisconnect();
                break;
            case (COMMAND_DISCONNECT_RESPONSE):
                result = new CommandDisconnectResponse();
                break;
            case (COMMAND_NETWORK_INFORMATION):
                result = new CommandNetworkInformation();
                break;
            case (COMMAND_INVALID):
            default:
                result = new CommandInvalid();
                break;
        }
        result = result.fromRepresentation(representation);
        return result;
    }

}
