import TCP.ServerTCP;
import administration.VendingMachine;
import java.io.IOException;

import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the capacity:");
        int capacity = scanner.nextInt();

        System.out.println("Enter the protocol (TCP/UDP):");
        String protocol = scanner.next();

        VendingMachine vendingMachine = new VendingMachine(capacity);

        if (protocol.equalsIgnoreCase("TCP")) {
            ServerTCP serverTCP = new ServerTCP(vendingMachine);
            serverTCP.start();
        } else if (protocol.equalsIgnoreCase("UDP")) {
            // Implement UDP server logic if needed
            System.out.println("UDP is not implemented yet.");
        } else {
            System.err.println("Invalid protocol. Use TCP or UDP.");
            System.exit(1);
        }
    }
}
