package singletonPattern;

import controller.Controller;

public class SingletonController {
    private static SingletonController instance;
    private ModuleLayer.Controller Controller;

    private SingletonController() {}

    public static SingletonController getInstance() {
        if (instance == null) {
            instance = new SingletonController();
        }
        return instance;
    }

    public ModuleLayer.Controller getController() {
        return Controller;
    }

    public void setController(ModuleLayer.Controller SceneController) {
        this.Controller = SceneController;
    }

}
