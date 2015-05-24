package mfthserver.server;

import mfthserver.server.commands.Command;
import java.io.IOException;

/**
 *
 * @author Barrionuevo Diego
 */
public abstract class CommunicationController {

    public abstract void send(Command communicable) throws IOException;

    public abstract Command receive() throws IOException;

    protected void console(String message) {
        System.out.println(message);
    }

}
