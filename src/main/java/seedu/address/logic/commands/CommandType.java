package seedu.address.logic.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Enumeration for the type of command.
 */
public enum CommandType {

    /**
     * Types of commands by the functionality it targets, along with the inputString to look for. DEFAULT is for
     * commands that should not have an inputString.
     */
    CONTACT("-c"),
    EVENT("-e"),
    TAG("-t"),
    DEFAULT("");

    /**
     * Unfortunately, {@code switch} statements do not permit the use of cases that are not constant at compile-time.
     * This means that the actual enumerations should be used in the cases instead. As such, there needs to be
     * a way to retrieve an enumeration given an input - this is done in the {@code HashMap} below, and with
     * {@code get}.
     */
    private static final Map<String, CommandType> inputs;

    static {
        inputs = new HashMap<>();
        for (CommandType commandType : CommandType.values()) {
            inputs.put(commandType.inputString, commandType);
        }
    }
    private final String inputString;

    CommandType(String inputString) {
        this.inputString = inputString;
    }

    public static CommandType get(String inputString) {
        return Optional.ofNullable(inputs.get(inputString)).orElse(DEFAULT);
    }

    @Override
    public String toString() {
        return inputString;
    }

}
