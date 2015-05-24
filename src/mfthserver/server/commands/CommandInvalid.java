package mfthserver.server.commands;

/**
 *
 * @author Barrionuevo Diego
 */
public class CommandInvalid extends Command {

    public CommandInvalid() {
        super(CommandFactory.COMMAND_INVALID);
    }

    @Override
    public String getRepresentation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Command fromRepresentation(String representation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
