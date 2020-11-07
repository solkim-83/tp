package seedu.address.logic.commands.tags;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPERTAG_ONLY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.util.TagUtil.INDICATOR_NO_CONTACTS_TAGGED;
import static seedu.address.model.util.TagUtil.INDICATOR_SUPERTAG;
import static seedu.address.model.util.TagUtil.MESSAGE_NO_TAGS_FOUND;
import static seedu.address.model.util.TagUtil.MESSAGE_TAGS_FOUND;
import static seedu.address.model.util.TagUtil.parsePersonSetIntoString;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.booleaninput.BooleanInput;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.model.Model;
import seedu.address.model.tag.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Finds and lists all tags in the system-display that either partially match the keyword used in the search
 * or is a super/regular tag.
 * Keyword matching is case insensitive.
 */
public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.FIND.toString();

    public static final String COMMAND_TYPE = CommandType.TAG.toString();

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_TYPE
            + ": Finds the tags which names (partially or entirely) contain the specified keyword (case-insensitive)."
            + "At least one of the optional fields must be provided."
            + "If the " + PREFIX_SUPERTAG_ONLY + " field is 1, it shows supertags only. If it is 0, it "
            + "shows regular tags only. If it is empty, it shows all tags.\n\n"
            + "Parameters:\n"
            + "[" + PREFIX_TAG + "KEYWORD]\n"
            + "[" + PREFIX_SUPERTAG_ONLY + "BOOLEAN]\n\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_TYPE + " t/CS2103 st/1";

    private final Optional<NameContainsKeywordsPredicate> predicate;
    private final Optional<BooleanInput> showSuperTagOnly;

    /**
     * Creates a FindTagCommand object that finds tags based on the given keyword or the given supertag only boolean.
     * @param predicate NameContainsKeywordsPrediate that contains the keyword to test keywords against
     * @param showSuperTagOnly BooleanInput that determines whether super tag or regular tags should be displayed
     */
    public FindTagCommand(Optional<NameContainsKeywordsPredicate> predicate, Optional<BooleanInput> showSuperTagOnly) {
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
     * Returns one String containing a summary of all tags that match the user's specification.
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
        }, () -> {
                fullTagSet.addAll(tagPersonSet);
                fullTagSet.addAll(superTagSet);
            });
        return fullTagSet.stream()
                .filter(tag -> predicate.map(p -> p.test(tag)).orElse(true))
                .map(tag -> tag.toString() + (superTagSet.contains(tag) ? INDICATOR_SUPERTAG : "")
                        + ": " + parsePersonSetIntoString(model.getPersonsWithTag(tag), INDICATOR_NO_CONTACTS_TAGGED))
                .reduce((s1, s2) -> s1 + '\n' + s2)
                .map(string -> MESSAGE_TAGS_FOUND + '\n' + string)
                .orElse(MESSAGE_NO_TAGS_FOUND);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTagCommand // instanceof handles nulls
                && predicate.equals(((FindTagCommand) other).predicate)) // state check
                && showSuperTagOnly.equals(((FindTagCommand) other).showSuperTagOnly);
    }

}
