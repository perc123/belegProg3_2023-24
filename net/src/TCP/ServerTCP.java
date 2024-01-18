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
    private Console console;

    public ServerTCP(Console console) {
        this.console = console;
    }

    private Command.Operator aktuellerModus;
    private String nachrichtAnClient = "";


/*

    private SerialisierungsModus serialisierungsModus;

    public void setSerialisierungsModus(SerialisierungsModus serialisierungsModus) {
        this.serialisierungsModus = serialisierungsModus;
    }
*/

    public void handleInput(String input) {
        if (input.startsWith(":")) {
            console.handleBuiltInCommand(input);
        } else {
            console.executeCommand(input);
        }
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("TCP Server gestartet");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client hat sich verbunden.");

                try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        handleInput(inputLine);
                        out.println(nachrichtAnClient);
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

    public void sendHerstellerListToServer(List<HerstellerStorage> output) {
        StringBuilder result = new StringBuilder();
        for (HerstellerStorage h : output) {
            result.append("[").append(h).append("] [Anzahl Kuchen: ").append(h).append("] || ");
        }
        this.nachrichtAnClient = result.toString();

    }

    public void sendKuchenListToServer(List<VendingMachine> output){
        StringBuilder result = new StringBuilder();
        for(VendingMachine k : output){
            result.append(k).append(" || ");
        }
        this.nachrichtAnClient = result.toString();
    }

    public void sendAllergenListToServer(List<Allergen> output){
        StringBuilder result = new StringBuilder();
        for(Allergen a : output){
            result.append(a.toString()).append(" || ");
        }
        this.nachrichtAnClient = result.toString();
    }

    public void sendInfoToServer(String output) {
        this.nachrichtAnClient = output;
    }

}