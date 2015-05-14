package mfthserver.server;

/**
 *
 * @author Barrionuevo Diego
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("SERVER");
        Server server = Server.getInstance();
        server.start();
    }
}
