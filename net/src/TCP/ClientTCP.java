package TCP;
import cli.Console;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientTCP {
    private Console console;

    public ClientTCP(Console console) {
        this.console = console;
    }

    public void start() throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String inputLine;
        System.out.println("connected to Server");

        while (true) {
            inputLine = scanner.nextLine();
            handleInput(inputLine);
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
