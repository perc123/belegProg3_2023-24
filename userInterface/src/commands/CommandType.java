package commands;

public enum CommandType {
    INSERT_MODE(":c"),
    DELETE_MODE(":d"),
    DISPLAY_MODE(":r"),
    UPDATE_MODE(":u"),
    PERSISTENCE_MODE(":p"),
    SAVE_JOS("saveJOS"),
    LOAD_JOS("loadJOS"),
    SAVE_JBP("saveJBP"),
    LOAD_JBP("loadJBP");

    private final String value;

    CommandType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
