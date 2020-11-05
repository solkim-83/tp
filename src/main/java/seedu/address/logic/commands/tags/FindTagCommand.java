package seedu.address.logic.commands.tags;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPERTAG_ONLY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.booleaninput.BooleanInput;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Finds and lists all tags in the system-display that contain any of the keywords used in the search.
 * Keyword matching is case insensitive.
 */
public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.FIND.toString();

    public static final String COMMAND_TYPE = CommandType.TAG.toString();

    public static final String MESSAGE_NO_TAGS_FOUND = "No tag assignments were found!";

    public static final String MESSAGE_TAGS_FOUND = "Listed all tags and contacts directly under these tags:";

    public static final String INDICATOR_SUPERTAG = " (supertag)";

    public static final String INDICATOR_NO_CONTACTS_TAGGED = "no contacts tagged";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_TYPE
            + ": Finds the tags which names (partially or entirely) contain the specified keyword (case-insensitive)."
            + "At least one of the optional fields must be provided."
            + "If the " + PREFIX_SUPERTAG_ONLY + " field is 1, it shows supertags only. If it is 0, it "
            + "shows regular tags only. If it is empty, it shows all tags.\n\n"
            + "Parameters:\n"
            + "[" + PREFIX_TAG + "KEYWORD]\n"
            + "[" + PREFIX_SUPERTAG_ONLY + "BOOLEAN]\n\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_TYPE + " t/CS2103 st/1";

    private final NameContainsKeywordsPredicate predicate;
    private final Optional<BooleanInput> showSuperTagOnly;

    public FindTagCommand(NameContainsKeywordsPredicate predicate, Optional<BooleanInput> showSuperTagOnly) {
        this.predicate = predicate;
        this.showSuperTagOnly = showSuperTagOnly;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Set<Tag> tagPersonSet = model.getPersonTags();
        Set<Tag> superTagSet = model.getSuperTags();
        String message = constructFilteredTagSummaryMessage(model, tagPersonSet, superTagSet);
        return new CommandResult(message);
    }

    /**
     * Returns one String containing a summary of all tags present in the system.
     * For a tag to be present, it has to have at least one {@code Person} with the tag OR has sub-tags.
     * Summary of each tag contains only the tag name and all contacts directly tagged with the tag.
     */
    protected String constructFilteredTagSummaryMessage(Model model, Set<Tag> tagPersonSet, Set<Tag> superTagSet) {
        Set<Tag> fullTagSet = new HashSet<>();
        showSuperTagOnly.ifPresentOrElse(bool -> {
            if (bool.getBooleanValue()) {
                fullTagSet.addAll(superTagSet);
            } else {
                fullTagSet.addAll(tagPersonSet);
            }
        }, () -> { fullTagSet.addAll(tagPersonSet); fullTagSet.addAll(superTagSet);});
        return fullTagSet.stream()
                .filter(predicate)
                .map(tag -> tag.toString() + (superTagSet.contains(tag) ? INDICATOR_SUPERTAG : "")
                        + ": " + parsePersonSetIntoString(model.getPersonsWithTag(tag), INDICATOR_NO_CONTACTS_TAGGED))
                .reduce((s1, s2) -> s1 + '\n' + s2)
                .map(string -> MESSAGE_TAGS_FOUND + '\n' + string)
                .orElse(MESSAGE_NO_TAGS_FOUND);
    }

    /**
     * Returns a string that combines the {@code Person} set by commas, then adds curly braces.
     * If the set is empty, returns {@code messageIfNoneFound}.
     */
    protected static String parsePersonSetIntoString(Set<Person> set, String messageIfNoneFound) {
        return set.stream()
                .map(t -> t.getName().toString())
                .reduce((s1, s2) -> s1 + ", " + s2)
                .map(string -> "{ " + string + " }")
                .orElse(messageIfNoneFound);
    }

}
