package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Time;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing event in the Athena.
 */
public class EditEventCommand extends Command {

    public static final String COMMAND_WORD = "editEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the displayed event list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Tag values to be added can be specified with t/ and removed with rt/.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_DATETIME + "DATETIME] "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_REMOVE_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + "New description "
            + PREFIX_DATETIME + "12-12-1234 12:34 "
            + PREFIX_TAG + "JOHN";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in Athena.";

    private final Index index;
    private final EditEventDescriptor editEventDescriptor;

    /**
     * @param index of the event in the filtered event list to edit
     * @param editEventDescriptor details to edit the event with
     */
    public EditEventCommand(Index index, EditEventDescriptor editEventDescriptor) {
        requireNonNull(index);
        requireNonNull(editEventDescriptor);

        this.index = index;
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getSortedFilteredEventList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToEdit = lastShownList.get(index.getZeroBased());
        Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);

        if (!eventToEdit.isSameEvent(editedEvent) && model.hasEvent(editedEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        // TODO: add setEvent method to model and subsequent methods needed for testing
        model.setEvent(eventToEdit, editedEvent);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     */
    private static Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        Description updatedDescription = editEventDescriptor.getDescription().orElse(eventToEdit.getDescription());
        Time updatedTime = editEventDescriptor.getTime().orElse(eventToEdit.getTime());
        Set<Tag> updatedTags = processTagUpdates(eventToEdit, editEventDescriptor.getTagsToAdd(),
                editEventDescriptor.getTagsToRemove());

        return new Event(updatedDescription, updatedTime, updatedTags);
    }

    /**
     *  Creates a new set of tags from the {@code Person} with the {@code tagsToRemove} removed BEFORE
     *  {@code tagsToAdd} are added.
     */
    private static Set<Tag> processTagUpdates(Event eventToEdit, Optional<Set<Tag>> tagsToAdd,
                                              Optional<Set<Tag>> tagsToRemove) {
        Set<Tag> finalTagSet = new HashSet<>(eventToEdit.getTags());
        tagsToRemove.ifPresent(set -> {
            if (set.contains(Tag.ALL_TAGS_TAG)) {
                finalTagSet.clear();
            } else {
                finalTagSet.removeAll(set);
            }
        });
        tagsToAdd.ifPresent(set -> finalTagSet.addAll(set));
        return finalTagSet;
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEventCommand)) {
            return false;
        }

        // state check
        EditEventCommand e = (EditEventCommand) other;
        return index.equals(e.index)
                && editEventDescriptor.equals(e.editEventDescriptor);
    }

    /**
     * Stores the details to edit the event with. Each non-empty field value will replace the
     * corresponding field value of the event.
     */
    public static class EditEventDescriptor {
        private Description description;
        private Time time;
        private Set<Tag> tagsToAdd;
        private Set<Tag> tagsToRemove;

        public EditEventDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            setDescription(toCopy.description);
            setTime(toCopy.time);
            setTagsToAdd(toCopy.tagsToAdd);
            setTagsToRemove(toCopy.tagsToRemove);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(description, time, tagsToAdd, tagsToRemove);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setTime(Time time) {
            this.time = time;
        }

        public Optional<Time> getTime() {
            return Optional.ofNullable(time);
        }

        /**
         * Adds {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTagsToAdd(Set<Tag> tagsToAdd) {
            this.tagsToAdd = (tagsToAdd != null) ? new HashSet<>(tagsToAdd) : null;
        }

        /**
         * Returns an unmodifiable tag set of tags to be added, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTagsToAdd() {
            return (tagsToAdd != null) ? Optional.of(Collections.unmodifiableSet(tagsToAdd)) : Optional.empty();
        }

        /**
         * Removes {@code tags} from this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTagsToRemove(Set<Tag> tagsToRemove) {
            this.tagsToRemove = (tagsToRemove != null) ? new HashSet<>(tagsToRemove) : null;
        }

        /**
         * Returns an unmodifiable tag set of tags to be removed, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTagsToRemove() {
            return (tagsToRemove != null) ? Optional.of(Collections.unmodifiableSet(tagsToRemove)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEventDescriptor)) {
                return false;
            }

            // state check
            EditEventDescriptor e = (EditEventDescriptor) other;

            return getDescription().equals(e.getDescription())
                    && getTime().equals(e.getTime())
                    && getTagsToAdd().equals(e.getTagsToAdd())
                    && (getTagsToRemove().equals(e.getTagsToRemove())
                    || checksForWildcardTagEquality(getTagsToRemove(), e.getTagsToRemove()));
        }
    }
    /**
     * Returns true if both tag sets contains the all_tags_tag.
     */
    private static boolean checksForWildcardTagEquality(Optional<Set<Tag>> tagSet1, Optional<Set<Tag>> tagSet2) {
        return tagSet1.isPresent() && tagSet1.get().contains(Tag.ALL_TAGS_TAG)
                && (tagSet2.isPresent() && tagSet2.get().contains(Tag.ALL_TAGS_TAG));
    }
}
