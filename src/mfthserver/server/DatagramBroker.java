package mfthserver.server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import mfthserver.server.commands.Command;
import mfthserver.server.commands.Identificable;

/**
 *
 * @author Barrionuevo Diego
 */
public class DatagramBroker {

    private DatagramController datagramController;
    private boolean connected;

    public DatagramBroker(DatagramSocket datagramSocket) {
        this.datagramController = new DatagramController(datagramSocket);
        this.connected = true;
    }

    public void listen() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    while (connected) {
                        Identificable command = (Identificable)datagramController.receive();
                        int idClientReceptor = command.getClientId();
                        ServerClient receptor = Server.getInstance().getClient(idClientReceptor);
                        if (receptor != null) {
                            //valid client
                            receptor.receiveDatagram(command);
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(DatagramBroker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }

}
