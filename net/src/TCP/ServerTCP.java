package TCP;


import administration.HerstellerImpl;
import cakes.KuchenImpl;
import commands.*;
import kuchen.Allergen;
import verwaltung.Hersteller;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServerTCP {


    private AddMode addMode;
    private RemoveMode removeMode;
    private UpdateMode updateMode;
    private PrintMode printMode;

    private Mode currentMode;

    String respondToClient = "";

    private SerializationMode serializationMode;


    public void setSerializationMode(SerializationMode serializationMode) {
        this.serializationMode = serializationMode;
    }

    public void setAddMode(AddMode addMode) {
        this.addMode = addMode;
    }

    public void setRemoveMode(RemoveMode removeMode) {
        this.removeMode = removeMode;
    }

    public void setUpdateMode(UpdateMode updateMode) {
        this.updateMode = updateMode;
    }

    public void setPrintMode(PrintMode printMode) {
        this.printMode = printMode;
    }

    public void handleInput(String input) {
        if (input.startsWith(":")) {
            switch (input) {
                case ":c" -> currentMode = addMode;
                case ":r" -> currentMode = printMode;
                case ":d" -> currentMode = removeMode;
                case ":u" -> currentMode = updateMode;
                case ":p" -> currentMode = serializationMode;
                default -> respondToClient = "Unknown command. Please try again.";
            }
        } else {
            if(currentMode == null){
                currentMode = printMode;
            }
            currentMode.handleInput(input);
        }
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("TCP Server started");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected.");

                try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        handleInput(inputLine);
                        out.println(respondToClient);
                        respondToClient = "";
                    }
                } catch (IOException e) {
                    System.err.println("Fehler aufgetreten: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler aufgetreten: " + e.getMessage());
        }
    }

    public void sendHerstellerListToServer(List<HerstellerImpl> output) {
        StringBuilder result = new StringBuilder();
        for (Hersteller h : output) {
            result.append("[").append(h).append("] [Anzahl Kuchen: ").append(h.getCakeCount()).append("] || ");
        }
        this.respondToClient = result.toString();

    }

    public void sendKuchenListToServer(List<KuchenImpl> output){
        StringBuilder result = new StringBuilder();
        for(KuchenImpl k : output){
            result.append(k).append(" || ");
        }
        this.respondToClient = result.toString();
    }

    public void sendAllergenListToServer(List<Allergen> output){
        StringBuilder result = new StringBuilder();
        for(Allergen a : output){
            result.append(a.toString()).append(" || ");
        }
        this.respondToClient = result.toString();
    }

    public void sendInfoToServer(String output) {
        this.respondToClient = output;
    }

}
