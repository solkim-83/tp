package seedu.address.logic.commands.contacts;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.model.Model;
import seedu.address.model.person.ContactContainsFieldsPredicate;

/**
 * Finds and lists all persons in Athena whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindContactCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.FIND.toString();

    public static final String COMMAND_TYPE = CommandType.CONTACT.toString();

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_TYPE
            + ": Finds all contacts whose fields contain any of "
            + "the specified keywords (case-insensitive) and matches the tags specified.\n"
            + "For fields that are not specified, all contacts are matched by default.\n\n"
            + "Parameters:\n"
            + "[" + PREFIX_NAME + "NAME KEYWORDS]\n"
            + "[" + PREFIX_PHONE + "PHONE KEYWORD]\n"
            + "[" + PREFIX_EMAIL + "EMAIL KEYWORD]\n"
            + "[" + PREFIX_ADDRESS + "ADDRESS KEYWORD]\n"
            + "[" + PREFIX_TAG + "TAG]...\n\n"
            + "Example: " + COMMAND_WORD + " n/alice bob charlie e/hotmail t/CS2103";

    private final ContactContainsFieldsPredicate predicate;

    public FindContactCommand(ContactContainsFieldsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getSortedFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindContactCommand // instanceof handles nulls
                && predicate.equals(((FindContactCommand) other).predicate)); // state check
    }

    public ContactContainsFieldsPredicate getPredicate() {
        return predicate;
    }



}
