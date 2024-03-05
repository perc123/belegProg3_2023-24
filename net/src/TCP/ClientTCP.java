package TCP;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientTCP {

    public void start() throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String inputLine;

        try (Socket socket = new Socket("localhost", 5000);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("conected to Server");

            while (true) {
                inputLine = scanner.nextLine();
                out.println(inputLine);
                String response = in.readLine();
                if (response != null) {
                    System.out.println(response);
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + "localhost");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}

