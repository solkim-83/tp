package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum CommandWord {

    /**
     * Types of commands by its word, along with the inputString to look for and a set of validTypes the command can
     * target. DEFAULT is for commands that should have neither an inputString nor validTypes (i.e. invalid commands).
     */
    ADD("add", List.of(CommandType.CONTACT, CommandType.EVENT, CommandType.TAG, CommandType.REMINDER)),
    CLEAR("clear", List.of(CommandType.CONTACT, CommandType.EVENT)),
    DELETE("delete", List.of(CommandType.CONTACT, CommandType.EVENT, CommandType.TAG, CommandType.REMINDER)),
    EDIT("edit", List.of(CommandType.CONTACT, CommandType.EVENT, CommandType.TAG)),
    EXIT("exit", List.of()),
    FIND("find", List.of(CommandType.CONTACT, CommandType.EVENT, CommandType.TAG)),
    HELP("help", List.of()),
    INTRO("intro", List.of()),
    LIST("list", List.of(CommandType.CONTACT, CommandType.EVENT, CommandType.TAG, CommandType.REMINDER)),
    VIEW("view", List.of(CommandType.EVENT, CommandType.TAG)),
    SHOW("show", List.of(CommandType.REMINDER)),
    SORT("sort", List.of(CommandType.CONTACT, CommandType.EVENT)),
    PERMASORT("psort", List.of(CommandType.CONTACT)),
    DELETE_BY_TAG("deletebytag", List.of(CommandType.CONTACT)),
    DEFAULT("", List.of());

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
    private final List<CommandType> validTypes;

    CommandWord(String inputString, List<CommandType> validTypes) {
        this.inputString = inputString;
        this.validTypes = new ArrayList<>(validTypes);
    }

    public static CommandWord get(String inputString) {
        return Optional.ofNullable(inputs.get(inputString)).orElse(DEFAULT);
    }

    public boolean requiresType() {
        return !validTypes.isEmpty();
    }

    public String listAcceptedTypesAsString() {
        return validTypes.stream().map(CommandType::getTypeString).reduce("", (x, y) -> x + "\n" + y);
    }

    public boolean containsType(CommandType commandType) {
        return validTypes.contains(commandType);
    }

    @Override
    public String toString() {
        return inputString;
    }

}
