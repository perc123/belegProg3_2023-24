import TCP.ClientTCP;
import administration.VendingMachine;
import cli.Console;

import java.io.IOException;
import java.net.Socket;


public class Client {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000)){
            Console console = new Console(socket);
            ClientTCP clientTCP = new ClientTCP(console);
            clientTCP.start();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

}