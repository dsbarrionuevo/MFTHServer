package mfthserver.server.commands;

import org.json.JSONObject;

/**
 *
 * @author Barrionuevo Diego
 */
public class CommandNetworkInformation extends Command {

    private int listenPortUDP;

    public CommandNetworkInformation() {
        super(CommandFactory.COMMAND_NETWORK_INFORMATION);
    }

    public CommandNetworkInformation(int listenPortUDP) {
        this();
        this.listenPortUDP = listenPortUDP;
    }

    @Override
    public String getRepresentation() {
        return "{command:" + CommandFactory.COMMAND_NETWORK_INFORMATION + ", listen_port_udp: " + listenPortUDP + "}";
    }

    @Override
    public Command fromRepresentation(String representation) {
        JSONObject jsonCommand = new JSONObject(representation);
        return new CommandNetworkInformation(jsonCommand.getInt("listen_port_udp"));
    }

    public int getListenPortUDP() {
        return listenPortUDP;
    }

}
