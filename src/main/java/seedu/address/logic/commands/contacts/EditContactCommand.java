package seedu.address.logic.commands.contacts;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
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
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in Athena.
 */
public class EditContactCommand extends Command {

    public static final String COMMAND_WORD = CommandWord.EDIT.toString();

    public static final String COMMAND_TYPE = CommandType.CONTACT.toString();

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_TYPE
            + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Existing non-tag values will be overwritten by the input values.\n"
            + "Tag values to be added can be specified with t/ and removed with rt/.\n\n"
            + "Parameters:\nINDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME]\n"
            + "[" + PREFIX_PHONE + "PHONE]\n"
            + "[" + PREFIX_EMAIL + "EMAIL]\n"
            + "[" + PREFIX_ADDRESS + "ADDRESS]\n"
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "[" + PREFIX_REMOVE_TAG + "TAG]...\n\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_TYPE + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com "
            + PREFIX_TAG + "CS2103";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in Athena.";
    public static final String INDICATOR_TAG_TO_BE_REMOVED_NOT_PRESENT =
            "The specified contact does not have at least one of the tags designated for removal.";
    public static final String INDICATOR_TAG_TO_BE_ADDED_ALREADY_PRESENT =
            "The specified contact already has at least one of the tags designated for addition.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditContactCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getSortedFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor)
            throws CommandException {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = processTagUpdates(personToEdit, editPersonDescriptor.getTagsToAdd(),
                editPersonDescriptor.getTagsToRemove());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }

    /**
     *  Creates a new set of tags from the {@code Person} with the {@code tagsToRemove} removed BEFORE
     *  {@code tagsToAdd} are added.
     */
    private static Set<Tag> processTagUpdates(Person personToEdit, Optional<Set<Tag>> tagsToAdd,
            Optional<Set<Tag>> tagsToRemove) throws CommandException {
        Set<Tag> finalTagSet = new HashSet<>(personToEdit.getTags());

        boolean hasInvalidTagsToRemove = tagsToRemove.map(tagSet -> tagSet.stream()
                .filter(tag -> !tag.equals(Tag.ALL_TAGS_TAG))
                .anyMatch(tag -> !finalTagSet.contains(tag))).orElse(false);
        if (hasInvalidTagsToRemove) {
            throw new CommandException(INDICATOR_TAG_TO_BE_REMOVED_NOT_PRESENT);
        }

        tagsToRemove.ifPresent(set -> {
            if (set.contains(Tag.ALL_TAGS_TAG)) {
                finalTagSet.clear();
            } else {
                finalTagSet.removeAll(set);
            }
        });

        boolean hasInvalidTagsToAdd = tagsToAdd.map(tagSet -> tagSet.stream()
                .filter(tag -> !tag.equals(Tag.ALL_TAGS_TAG))
                .anyMatch(tag -> finalTagSet.contains(tag))).orElse(false);
        if (hasInvalidTagsToAdd) {
            throw new CommandException(INDICATOR_TAG_TO_BE_ADDED_ALREADY_PRESENT);
        }

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
        if (!(other instanceof EditContactCommand)) {
            return false;
        }

        // state check
        EditContactCommand e = (EditContactCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tagsToAdd = Set.of();
        private Set<Tag> tagsToRemove = Set.of();

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTagsToAdd(toCopy.tagsToAdd);
            setTagsToRemove(toCopy.tagsToRemove);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address)
                    || !tagsToAdd.isEmpty()
                    || !tagsToRemove.isEmpty();
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
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
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
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
