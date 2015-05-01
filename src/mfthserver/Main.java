package mfthserver;

import java.io.IOException;

/**
 *
 * @author Barrionuevo Diego
 */
public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("SERVER");
        Server server = new Server(4646);
        server.start();
    }
}
