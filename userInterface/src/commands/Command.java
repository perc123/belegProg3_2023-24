package commands;


public class Command {
    public enum Operator {
        INSERT_MODE(":c"),
        DELETE_MODE(":d"),
        DISPLAY_MODE(":r"),
        UPDATE_MODE(":u"),
        PERSISTENCE_MODE(":p"),
        SAVE_JOS("saveJOS"),
        LOAD_JOS("loadJOS"),
        SAVE_JBP("saveJBP"),
        LOAD_JBP("loadJBP"),
        EXIT("exit"),
        ERROR("error");

        private final String value;

        Operator(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private final Operator operator;

    public Command(String text) {
        String op = text.trim().toUpperCase(); // Convert to uppercase for case-insensitive comparison
        for (Operator value : Operator.values()) {
            if (op.equals(value.getValue().toUpperCase())) {
                this.operator = value;
                return;
            }
        }
        this.operator = Operator.ERROR;
    }

    public Operator getOperator() {
        return operator;
    }
}
