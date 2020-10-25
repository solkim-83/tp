package seedu.address.logic.commands.tags;


import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

/**
 * Adds a new tag to the system. The tag can be assigned sub-tags and/or contacts.
 */
public class AddTagCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.ADD.toString();

    public static final String COMMAND_TYPE = CommandType.TAG.toString();

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_TYPE
            + ": Adds a new tag to Athena. "
            + "Parameters: "
            + "[" + PREFIX_INDEX + "CONTACT_INDEX]... "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_TYPE + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_INDEX + "3 "
            + PREFIX_TAG + "cs2103";

    public static final String MESSAGE_SUCCESS = "New tag added: %s";
    public static final String MESSAGE_DUPLICATE_TAG = "This tag has already been created!";
    public static final String MESSAGE_INDEX_FAILURE = "Assigned indices do not exist!";
    public static final String MESSAGE_TAG_FAILURE = "Assigned tags do not exist!";

    private final Tag tagToAdd;
    private final Set<Tag> subTagSet;
    private final Set<Person> personSet;

    /**
     * Creates an AddTagCommand to add the specified {@code tagToAdd} with {@code subTagSet} as its subtags and
     * {@code personSet} as persons directly under this tag.
     */
    public AddTagCommand(Tag tagToAdd, Set<Tag> subTagSet, Set<Person> personSet) {
        this.tagToAdd = tagToAdd;
        this.subTagSet = Set.copyOf(subTagSet);
        this.personSet = Set.copyOf(personSet);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
//TODO
        return null;
    }





}
