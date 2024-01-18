import TCP.ClientTCP;
import TCP.ServerTCP;
import administration.HerstellerStorage;
import administration.VendingMachine;
import cli.Console;
import java.io.IOException;
/*

public class Server {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java Server <TCP/UDP> <capacity>");
            System.exit(1);
        }

        try {
            int capacity = Integer.parseInt(args[1]);
            VendingMachine vendingMachine = new VendingMachine(capacity);
            Console console = new Console(vendingMachine);

            if (args[0].equalsIgnoreCase("TCP")) {
                ServerTCP serverTCP = new ServerTCP(console);
                serverTCP.start();
            } else if (args[0].equalsIgnoreCase("UDP")) {
                // Implement UDP server logic if needed
                System.out.println("UDP is not implemented yet.");
            } else {
                System.err.println("Invalid protocol. Use TCP or UDP.");
                System.exit(1);
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid capacity. Please provide a numeric value.");
            System.exit(1);
        }
    }
}
*/

public class Server {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        String protocol = "TCP";
        int capacity = 10;

        if (args.length >= 1) {
            protocol = args[0];
        }

        if (args.length >= 2) {
            try {
                capacity = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid capacity. Please provide a numeric value.");
                System.exit(1);
            }
        }

        VendingMachine vendingMachine = new VendingMachine(capacity);
        Console console = new Console(vendingMachine);

        if (protocol.equalsIgnoreCase("TCP")) {
            ServerTCP serverTCP = new ServerTCP(console);
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

