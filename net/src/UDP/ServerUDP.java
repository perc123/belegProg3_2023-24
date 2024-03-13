package UDP;

import administration.HerstellerImpl;
import cakes.KuchenImpl;
import commands.*;
import kuchen.Allergen;
import verwaltung.Hersteller;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

public class ServerUDP {

    private AddMode addMode;
    private RemoveMode removeMode;
    private UpdateMode updateMode;
    private PrintMode printMode;

    private Mode currentMode;

    String respondToClient = "";

    DatagramPacket datagramPacket;

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

    public void start() throws IOException {
        int port = 12345;
        byte[] buffer = new byte[1024];

        try (DatagramSocket serverSocket = new DatagramSocket(port)) {
            System.out.println("UDP Server started.");

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(receivePacket);
                datagramPacket = receivePacket;

                String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                respondToClient = "";
                handleInput(receivedMessage);

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();

                String responseMessage = respondToClient;
                byte[] responseData = responseMessage.getBytes();
                DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, clientAddress, clientPort);
                serverSocket.send(responsePacket);
            }
        }
    }

    public void sendHerstellerListToServer(List<HerstellerImpl> output){
        StringBuilder result = new StringBuilder();
        for (HerstellerImpl h : output) {
            result.append("[").append(h).append("] [Cake count: ").append(h.getCakeCount()).append("] || ");
        }
        respondToClient = result.toString();
    }

    public void sendKuchenListToServer(List<KuchenImpl> output){
        StringBuilder result = new StringBuilder();
        for (KuchenImpl k : output) {
            result.append(k.getKuchenTyp()).append(" || ");
        }
        respondToClient = result.toString();
    }

    public void sendAllergenListToServer(List<Allergen> output){
        StringBuilder result = new StringBuilder();
        for (Allergen a : output) {
            result.append(a.toString()).append(" || ");
        }
        respondToClient = result.toString();
    }

    public void sendInfoToServer(String output){
        respondToClient = output;
    }
}

