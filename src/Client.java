import TCP.ClientTCP;

import java.io.IOException;


public class Client {

    public static void main(String[] args) {
        try {
            ClientTCP clientTCP = new ClientTCP();
            clientTCP.start();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}