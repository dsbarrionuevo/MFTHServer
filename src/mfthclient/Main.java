package mfthclient;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Barrionuevo Diego
 */
public class Main {

    public static void main(String[] args) {
        try {
            System.out.println("CLIENT");
            Client client = new Client(new Socket("localhost", 4646));
            new Thread(client).start();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
