package seedu.address.logic.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum CommandWord {

    /**
     * Types of commands by its word, along with the inputString to look for. DEFAULT is for commands that should not
     * have an inputString.
     */
    ADD("add"),
    CLEAR("clear"),
    DELETE("delete"),
    EDIT("edit"),
    EXIT("exit"),
    FIND("find"),
    HELP("help"),
    INTRO("intro"),
    LIST("list"),
    SORT("sort"),
    PERMASORT("psort"),
    DEFAULT("");

    /**
     * Unfortunately, {@code switch} statements do not permit the use of cases that are not constant at compile-time.
     * This means that the actual enumerations should be used in the cases instead. As such, there needs to be
     * a way to retrieve an enumeration given an input - this is done in the {@code HashMap} below, and with
     * {@code get}.
     */
    private static final Map<String, CommandWord> inputs;

    static {
        inputs = new HashMap<>();
        for (CommandWord commandWord : CommandWord.values()) {
            inputs.put(commandWord.inputString, commandWord);
        }
    }
    private final String inputString;

    CommandWord(String inputString) {
        this.inputString = inputString;
    }

    public static CommandWord get(String inputString) {
        return Optional.ofNullable(inputs.get(inputString)).orElse(DEFAULT);
    }

    @Override
    public String toString() {
        return inputString;
    }

}
