package singletonPattern;

import administration.VendingMachine;

public class SingletonVendingMachine {
    private static SingletonVendingMachine instance;
    private VendingMachine vendingMachine;

    private SingletonVendingMachine() {}

    public static SingletonVendingMachine getInstance() {
        if (instance == null) {
            instance = new SingletonVendingMachine();
        }
        return instance;
    }

    public VendingMachine getVendingMachine() {
        return vendingMachine;
    }

    public void setVendingMachine(VendingMachine model) {
        this.vendingMachine = model;
    }
}
