package TCP;


import administration.HerstellerStorage;
import administration.VendingMachine;
import commands.Command;
import cli.Console;
import kuchen.Allergen;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServerTCP {
    private VendingMachine vendingMachine;
    private static String nachrichtAnClient = "";

    public ServerTCP(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("TCP Server gestartet");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client hat sich verbunden.");

                try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    // Send the capacity to the client
                    out.println("CAPACITY:" + vendingMachine.getCapacity());

                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        // Do not handle input on the server side
                        // Send the output to the client
                        if (!nachrichtAnClient.isEmpty()) {
                            out.println(nachrichtAnClient);
                        }

                        nachrichtAnClient = "";
                    }
                } catch (IOException e) {
                    System.err.println("Fehler aufgetreten: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler aufgetreten: " + e.getMessage());
        }
    }
}
