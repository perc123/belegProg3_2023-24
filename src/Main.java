import administration.VendingMachine;
import cli.Console;


public class Main {
    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachine(10);
        Console console = new Console(vendingMachine);
        console.start();
    }
}
