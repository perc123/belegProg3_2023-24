package singletonPattern;

import controller.GUIcontroller;

public class SingletonController {
    private static SingletonController instance;
    private GUIcontroller GUIcontroller;

    private SingletonController() {}

    public static SingletonController getInstance() {
        if (instance == null) {
            instance = new SingletonController();
        }
        return instance;
    }

    public GUIcontroller getController() {
        return GUIcontroller;
    }

    public void setController(GUIcontroller guiController) {
        this.GUIcontroller = guiController;
    }

}
