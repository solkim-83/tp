package seedu.address.logic.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Enumeration for the type of command.
 */
public enum CommandType {

    /**
     * Types of commands by the functionality it targets, along with the inputString to look for and a typeString that
     * describes the type of functionality targeted. DEFAULT is for commands that should neither have an inputString or
     * a typeString (i.e. an invalid command type).
     */
    CONTACT("-c", "contacts"),
    EVENT("-e", "events"),
    TAG("-t", "tags"),
    REMINDER("-r", "reminders"),
    DEFAULT("", "");

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
    private final String typeString;

    CommandType(String inputString, String typeString) {
        this.inputString = inputString;
        this.typeString = typeString;
    }

    public static CommandType get(String inputString) {
        return Optional.ofNullable(inputs.get(inputString)).orElse(DEFAULT);
    }

    public String getTypeString() {
        return String.format("%s for %s", inputString, typeString);
    }

    public boolean isInvalid() {
        return this.equals(DEFAULT);
    }

    @Override
    public String toString() {
        return inputString;
    }

}
