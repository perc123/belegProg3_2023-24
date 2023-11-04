package commands;

public enum CommandType {
    SWITCH_INSERT_MODE(":c"),
    SWITCH_DELETE_MODE(":d"),
    SWITCH_DISPLAY_MODE(":r"),
    SWITCH_UPDATE_MODE(":u"),
    SWITCH_PERSISTENCE_MODE(":p"),
    INSERT_HERSTELLER("insert-hersteller"),
    INSERT_KUCHEN("insert-kuchen"),
    DISPLAY_HERSTELLER("display-hersteller"),
    DISPLAY_KUCHEN("display-kuchen"),
    DISPLAY_ALLERGENE("display-allergene"),
    DELETE_HERSTELLER("delete-hersteller"),
    REMOVE_KUCHEN("remove-kuchen"),
    UPDATE_INSPECTION_DATE("update-inspection-date"),
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
