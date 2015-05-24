package mfthserver.server.commands;

import org.json.JSONObject;

/**
 *
 * @author Barrionuevo Diego
 */
public abstract class Command {

    private final int type;
    private static CommandFactoryMethod factory = CommandFactory.getInstance();

    public Command(int type) {
        this.type = type;
    }

    public abstract String getRepresentation();

    public abstract Command fromRepresentation(String representation);

    public int getType() {
        return type;
    }

    public static Command create(String representation) {
        return factory.create(representation);
    }

    public static void setFactory(CommandFactoryMethod factory) {
        Command.factory = factory;
    }

    public static CommandFactoryMethod getFactory() {
        return factory;
    }

}
