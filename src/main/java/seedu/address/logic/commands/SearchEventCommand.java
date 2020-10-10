package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.event.DescriptionContainsKeywordsPredicate;

/**
 * Finds and lists all events in calendar that have their descriptions containing any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchEventCommand extends Command {

    public static final String COMMAND_WORD = "searchEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events that contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " meeting";

    private final DescriptionContainsKeywordsPredicate predicate;

    public SearchEventCommand(DescriptionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEventList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, model.getFilteredEventList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchEventCommand // instanceof handles nulls
                && predicate.equals(((SearchEventCommand) other).predicate)); // state check
    }
}
