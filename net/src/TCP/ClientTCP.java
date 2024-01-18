package TCP;
import administration.VendingMachine;
import cli.Console;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientTCP {
    private Console console;
    private Socket socket;
    private VendingMachine vendingMachine;

    public ClientTCP() {
    }

    public void connectToServer() throws IOException {
        // Create a socket and connect to the server
        socket = new Socket("localhost", 5000);
        System.out.println("Connected to Server");
        //TODO: the capacity should come from the server
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            // Receive the capacity from the server
            String capacityMessage = in.readLine();
            if (capacityMessage != null && capacityMessage.startsWith("CAPACITY:")) {
                int capacity = Integer.parseInt(capacityMessage.substring("CAPACITY:".length()));
                //TODO: Use the capacity to initialize the vending machine
                vendingMachine = new VendingMachine(capacity);
                console = new Console(vendingMachine);
            }

            // Start communication in a separate thread
            Thread communicationThread = new Thread(() -> {
                try {
                    startCommunication(in, out);
                } catch (IOException e) {
                    System.err.println("Error in communication thread: " + e.getMessage());
                }
            });
            communicationThread.start();

            // Keep the main thread alive
            communicationThread.join();
        } catch (InterruptedException e) {
            System.err.println("Error in main thread: " + e.getMessage());
        }
    }

    private void startCommunication(BufferedReader in, PrintWriter out) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String inputLine;

        while (true) {
            inputLine = scanner.nextLine();
            handleInput(inputLine);
            String response = in.readLine();
            if (response != null) {
                System.out.println(response);
            }
        }
    }

    public void start() {
        try {
            connectToServer();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    public void handleInput(String input) {
        if (input.startsWith(":")) {
            console.handleBuiltInCommand(input);
        } else {
            console.executeCommand(input);
        }
    }
}
