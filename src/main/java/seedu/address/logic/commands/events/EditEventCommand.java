package seedu.address.logic.commands.events;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_PERSON;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.CommandWord;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Time;
import seedu.address.model.event.association.FauxPerson;
import seedu.address.model.person.Person;

/**
 * Edits the details of an existing event in the Athena.
 */
public class EditEventCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.EDIT.toString();

    public static final String COMMAND_TYPE = CommandType.EVENT.toString();

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_TYPE
            + ": Edits the details of the event identified "
            + "by the index number used in the displayed event list. "
            + "Description and time will be overwritten by the input values.\n\n"
            + "Parameters:\nINDEX (must be a positive integer)\n"
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]\n"
            + "[" + PREFIX_DATETIME + "DATE_TIME]\n"
            + "[" + PREFIX_ADD_PERSON + "CONTACT_INDEX_LIST]\n"
            + "[" + PREFIX_REMOVE_PERSON + "ATTENDEE_INDEX_LIST]\n\n"
            + "Example: \n" + COMMAND_WORD + " " + COMMAND_TYPE + " 1 "
            + PREFIX_DESCRIPTION + "New description "
            + PREFIX_DATETIME + "12-12-1234 12:34" + " "
            + PREFIX_ADD_PERSON + "1,2,3" + " "
            + PREFIX_REMOVE_PERSON + "1,2";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in Athena.";
    public static final String MESSAGE_CLASHING_EVENT = "An event exists at this time in Athena";

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
        List<Event> lastShownEventList = model.getSortedFilteredEventList();

        if (index.getZeroBased() >= lastShownEventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToEdit = lastShownEventList.get(index.getZeroBased());
        List<Person> lastShownPersonList = model.getSortedFilteredPersonList();

        Event editedEvent;
        try {
            editedEvent = createEditedEvent(eventToEdit, editEventDescriptor, lastShownPersonList);
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (!eventToEdit.isSameEvent(editedEvent) && model.hasEvent(editedEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }
        if (!eventToEdit.isSameEvent(editedEvent)
                && !eventToEdit.getTime().equals(editedEvent.getTime())
                && model.hasClashingEvent(editedEvent)) {
            throw new CommandException(MESSAGE_CLASHING_EVENT);
        }

        model.setEvent(eventToEdit, editedEvent);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     */
    private static Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor,
                                           List<Person> lastShownPersonList) throws IndexOutOfBoundsException {

        Description updatedDescription = editEventDescriptor.getDescription().orElse(eventToEdit.getDescription());
        Time updatedTime = editEventDescriptor.getTime().orElse(eventToEdit.getTime());

        // updatedAssociatedPersons' part, sorting is necessary to sync with the order of displayed names in the GUI
        // Sorting for display in the GUI component can be found in EventCard class
        ArrayList<FauxPerson> tempAssociatedPersons = new ArrayList<>(eventToEdit.getAssociatedPersons());
        tempAssociatedPersons.sort(Comparator.comparing(current -> current.displayName));

        tempAssociatedPersons = removeFauxPersons(tempAssociatedPersons, editEventDescriptor);
        tempAssociatedPersons = addFauxPersons(tempAssociatedPersons, editEventDescriptor, lastShownPersonList);

        Set<FauxPerson> updatedAssociatedPersons = new HashSet<>(tempAssociatedPersons);

        return new Event(updatedDescription, updatedTime, updatedAssociatedPersons);
    }

    private static ArrayList<FauxPerson> removeFauxPersons(
            ArrayList<FauxPerson> tempAssociatedPersons,
            EditEventDescriptor editEventDescriptor) throws IndexOutOfBoundsException {

        // wildCard check
        if (editEventDescriptor.isWildCardRemove()) {
            return new ArrayList<>();
        }

        // remove FauxPersons from event
        if (editEventDescriptor.getPersonsToRemove().isPresent()) {
            ArrayList<Index> indexArrayList = editEventDescriptor.getPersonsToRemove().get();
            for (Index index : indexArrayList) {
                tempAssociatedPersons.remove(index.getZeroBased());
            }
        }
        return tempAssociatedPersons;
    }

    private static ArrayList<FauxPerson> addFauxPersons(
            ArrayList<FauxPerson> tempAssociatedPersons,
            EditEventDescriptor editEventDescriptor,
            List<Person> lastShownPersonList) throws IndexOutOfBoundsException {

        // wildCard check
        if (editEventDescriptor.isWildCardAdd()) {
            for (Person person : lastShownPersonList) {
                FauxPerson newFauxPerson = new FauxPerson(person);
                if (!tempAssociatedPersons.contains(newFauxPerson)) {
                    tempAssociatedPersons.add(newFauxPerson);
                }
            }
            return tempAssociatedPersons;
        }

        // add FauxPersons to event, in user order, no sorting, duplicates are not added
        if (editEventDescriptor.getPersonsToAdd().isPresent()) {
            for (Index index : editEventDescriptor.getPersonsToAdd().get()) {

                Person personToAdd = lastShownPersonList.get(index.getZeroBased());
                FauxPerson newFauxPerson = new FauxPerson(personToAdd);

                // only new FauxPersons are added
                if (!tempAssociatedPersons.contains(newFauxPerson)) {
                    tempAssociatedPersons.add(newFauxPerson);
                }
            }
        }
        return tempAssociatedPersons;
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
        private ArrayList<Index> personsToAdd = new ArrayList<>();
        private ArrayList<Index> personsToRemove = new ArrayList<>();
        private boolean wildCardAdd = false;
        private boolean wildCardRemove = false;

        public EditEventDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            setDescription(toCopy.description);
            setTime(toCopy.time);
            toCopy.getPersonsToAdd().ifPresent(this::setPersonsToAdd);
            toCopy.getPersonsToRemove().ifPresent(this::setPersonsToRemove);
            this.wildCardAdd = toCopy.wildCardAdd;
            this.wildCardRemove = toCopy.wildCardRemove;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(description, time)
                    || !personsToAdd.isEmpty()
                    || !personsToRemove.isEmpty()
                    || wildCardAdd == true
                    || wildCardRemove == true;
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

        public void setPersonsToAdd(ArrayList<Index> personsToAdd) {
            this.personsToAdd.addAll(personsToAdd);
        }

        public Optional<ArrayList<Index>> getPersonsToAdd() {
            return Optional.ofNullable(personsToAdd);
        }

        public void setPersonsToRemove(ArrayList<Index> personsToRemove) {
            this.personsToRemove.addAll(personsToRemove);
        }

        public Optional<ArrayList<Index>> getPersonsToRemove() {
            return Optional.ofNullable(personsToRemove);
        }

        /**
         * Sets wild card add to be true, meaning all displayed persons are to be added
         */
        public void setWildCardAdd() {
            this.wildCardAdd = true;
        }

        public boolean isWildCardAdd() {
            return wildCardAdd;
        }

        /**
         * Sets wild card remove to be true, meaning all currently associated persons are to be removed
         */
        public void setWildCardRemove() {
            this.wildCardRemove = true;
        }

        public boolean isWildCardRemove() {
            return wildCardRemove;
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
                    && getPersonsToAdd().equals(e.getPersonsToAdd())
                    && getPersonsToRemove().equals(e.getPersonsToRemove())
                    && wildCardAdd == e.wildCardAdd
                    && wildCardRemove == e.wildCardRemove;
        }
    }
}
