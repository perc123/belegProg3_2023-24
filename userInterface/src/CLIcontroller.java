import commands.*;

import java.util.Scanner;

public class CLIcontroller implements Runnable {
    private final AddMode einfuegeModus;
    private final RemoveMode loeschModus;
    private final UpdateMode aenderungsModus;
    private final PrintMode anzeigeModus;

    private final SerializationMode serialisierungsModus;

    public CLIcontroller(AddMode einfuegeModus, RemoveMode loeschModus, UpdateMode aenderungsModus, PrintMode anzeigeModus, SerializationMode serialisierungsModus) {
        this.einfuegeModus = einfuegeModus;
        this.loeschModus = loeschModus;
        this.aenderungsModus = aenderungsModus;
        this.anzeigeModus = anzeigeModus;
        this.serialisierungsModus = serialisierungsModus;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        String input;
        Mode aktuellerModus = anzeigeModus;

        while (true) {
            input = scanner.nextLine();

            if (input.startsWith(":")) {
                switch (input) {
                    case ":c" -> aktuellerModus = einfuegeModus;
                    case ":r" -> aktuellerModus = anzeigeModus;
                    case ":d" -> aktuellerModus = loeschModus;
                    case ":u" -> aktuellerModus = aenderungsModus;
                    case ":p" -> aktuellerModus = serialisierungsModus;
                    default -> System.out.println("Unknown command. Please try again.");
                }
            } else {
                aktuellerModus.handleInput(input);
            }
        }
    }


    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input;
        Mode aktuellerModus = anzeigeModus;

        while (true) {
            input = scanner.nextLine();

            if (input.startsWith(":")) {
                switch (input) {
                    case ":c" -> aktuellerModus = einfuegeModus;
                    case ":r" -> aktuellerModus = anzeigeModus;
                    case ":d" -> aktuellerModus = loeschModus;
                    case ":u" -> aktuellerModus = aenderungsModus;
                    case ":p" -> aktuellerModus = serialisierungsModus;
                    default -> System.out.println("Unknown command. Please try again.");
                }
            } else {
                aktuellerModus.handleInput(input);
            }
        }
    }
}
