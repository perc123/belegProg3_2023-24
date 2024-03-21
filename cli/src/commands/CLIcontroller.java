package commands;

import com.sun.jdi.VMCannotBeModifiedException;
import commands.*;

import java.util.Scanner;

public class CLIcontroller implements Runnable {
    private final AddMode addMode;
    private final RemoveMode removeMode;
    private final UpdateMode updateMode;
    private final PrintMode printMode;

    private final SerializationMode serializationMode;

    public CLIcontroller(AddMode addMode, RemoveMode removeMode, UpdateMode updateMode, PrintMode printMode, SerializationMode serializationMode) {
        this.addMode = addMode;
        this.removeMode = removeMode;
        this.updateMode = updateMode;
        this.printMode = printMode;
        this.serializationMode = serializationMode;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String input;
        Mode mode = printMode;

        while (true) {
            input = scanner.nextLine();

            if (input.startsWith(":")) {
                switch (input) {
                    case ":c" -> mode = addMode;
                    case ":r" -> mode = printMode;
                    case ":d" -> mode = removeMode;
                    case ":u" -> mode = updateMode;
                    case ":p" -> mode = serializationMode;
                    default -> System.out.println("Unknown command. Please try again.");
                }
            } else {
                mode.handleInput(input);
            }
        }
    }


    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input;
        Mode mode = printMode;

        while (true) {
            input = scanner.nextLine();

            if (input.startsWith(":")) {
                switch (input) {
                    case ":c" -> mode = addMode;
                    case ":r" -> mode = printMode;
                    case ":d" -> mode = removeMode;
                    case ":u" -> mode = updateMode;
                    case ":p" -> mode = serializationMode;
                    default -> System.out.println("Unknown command. Please try again.");
                }
            } else {
                mode.handleInput(input);
            }
        }
    }
}
