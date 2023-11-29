package commands;


import java.util.Arrays;
import java.util.List;

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
    private final List<String> arguments;

    public Command(String text) {
        String[] parts = text.trim().split("\\s+");
        String op = parts[0].toUpperCase();
        this.arguments = Arrays.asList(parts).subList(1, parts.length);

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

    public List<String> getArguments() {
        return arguments;
    }
}