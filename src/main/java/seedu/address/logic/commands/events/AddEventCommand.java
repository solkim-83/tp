package seedu.address.logic.commands.events;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
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
 * Adds an event to Athena.
 */
public class AddEventCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.ADD.toString();

    public static final String COMMAND_TYPE = CommandType.EVENT.toString();

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_TYPE
            + ": Adds an event to Athena.\n\n"
            + "Parameters:\n"
            + PREFIX_DESCRIPTION + "DESCRIPTION\n"
            + PREFIX_DATETIME + "DATE_TIME\n"
            + "[" + PREFIX_ADD_PERSON + "CONTACT_INDEX_LIST]\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_TYPE + " "
            + PREFIX_DESCRIPTION + "CS2103 Team meeting" + " "
            + PREFIX_DATETIME + "12-10-2020 12:00" + " "
            + PREFIX_ADD_PERSON + "1,2,3";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in Athena";
    public static final String MESSAGE_CLASHING_EVENT = "An event exists at this time in Athena";
    public static final String MESSAGE_FAILURE = "Please enter a description for this event.\n"
            + "Refer to the command format below.\n" + MESSAGE_USAGE;

    private final AddEventDescriptor addEventDescriptor;

    /**
     * Creates an AddEventCommand to add the specified {@code Event} modified with {@code AddEventDescriptor}
     */
    public AddEventCommand(AddEventDescriptor addEventDescriptor) {
        requireNonNull(addEventDescriptor);
        this.addEventDescriptor = addEventDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Event toAdd;
        try {
            toAdd = createEvent(addEventDescriptor, model.getSortedFilteredPersonList());
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }
        if (model.hasClashingEvent(toAdd)) {
            throw new CommandException(MESSAGE_CLASHING_EVENT);
        }

        model.addEvent(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * edited with {@code addEventDescriptor}.
     */
    private static Event createEvent(AddEventDescriptor addEventDescriptor,
                                           List<Person> lastShownPersonList) throws IndexOutOfBoundsException {

        Description updatedDescription = addEventDescriptor.getDescription().get();
        Time updatedTime = addEventDescriptor.getTime().get();

        // updatedAssociatedPersons' part
        ArrayList<FauxPerson> tempAssociatedPersons = new ArrayList<>();

        tempAssociatedPersons = addFauxPersons(tempAssociatedPersons, addEventDescriptor, lastShownPersonList);

        Set<FauxPerson> updatedAssociatedPersons = new HashSet<>(tempAssociatedPersons);

        return new Event(updatedDescription, updatedTime, updatedAssociatedPersons);
    }

    private static ArrayList<FauxPerson> addFauxPersons(
            ArrayList<FauxPerson> tempAssociatedPersons,
            AddEventDescriptor addEventDescriptor,
            List<Person> lastShownPersonList) throws IndexOutOfBoundsException {

        // wildCard check
        if (addEventDescriptor.isWildCardAdd()) {
            for (Person person : lastShownPersonList) {
                FauxPerson newFauxPerson = new FauxPerson(person);
                if (!tempAssociatedPersons.contains(newFauxPerson)) {
                    tempAssociatedPersons.add(newFauxPerson);
                }
            }
            return tempAssociatedPersons;
        }

        // add FauxPersons to event
        if (addEventDescriptor.getPersonsToAdd().isPresent()) {
            for (Index index : addEventDescriptor.getPersonsToAdd().get()) {
                assert index.getZeroBased() < lastShownPersonList.size() : "No person at given index";

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
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && addEventDescriptor.equals(((AddEventCommand) other).addEventDescriptor));
    }

    public static class AddEventDescriptor {
        private Description description;
        private Time time;
        private ArrayList<Index> personsToAdd = new ArrayList<>();
        private boolean wildCardAdd = false;

        public AddEventDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public AddEventDescriptor(AddEventDescriptor toCopy) {
            setDescription(toCopy.description);
            setTime(toCopy.time);
            toCopy.getPersonsToAdd().ifPresent(this::setPersonsToAdd);
            this.wildCardAdd = toCopy.wildCardAdd;
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

        /**
         * Sets wild card add to be true, meaning all displayed persons are to be added
         */
        public void setWildCardAdd() {
            this.wildCardAdd = true;
        }

        public boolean isWildCardAdd() {
            return wildCardAdd;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof AddEventDescriptor)) {
                return false;
            }

            // state check
            AddEventDescriptor e = (AddEventDescriptor) other;

            return getDescription().equals(e.getDescription())
                    && getTime().equals(e.getTime())
                    && getPersonsToAdd().equals(e.getPersonsToAdd())
                    && wildCardAdd == e.wildCardAdd;
        }
    }
}
