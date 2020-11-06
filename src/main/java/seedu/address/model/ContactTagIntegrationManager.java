package seedu.address.model;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagTree;

/**
 * Class that controls {@code Tag} relationships and interactions between {@code Tag}s and {@Code Person}s.
 */
public class ContactTagIntegrationManager {

    private AddressBook addressBook;
    private TagTree tagTree;

    /**
     * Creates a ContactTagIntegrationManager from the given {@code addressBook} and {@code tagTree}.
     */
    public ContactTagIntegrationManager(AddressBook addressBook, TagTree tagTree) {
        this.addressBook = addressBook;
        this.tagTree = tagTree;
    }

    public AddressBook getAddressBook() {
        return addressBook;
    }

    public TagTree getTagTree() {
        return tagTree;
    }

    /**
     * Returns true if either the addressBook has {@code tag} or the tagTree has {@code tag}.
     * For a tag to exist, it must have at least one contact with the tag OR at least one child-tag assigned to it.
     */
    public boolean hasTag(Tag tag) {
        return addressBook.hasTag(tag) || tagTree.hasTag(tag);
    }

    /**
     * Returns a {@code set} of {@code person}s that falls under the given {@code tag} or any of its sub-tags.
     *
     * @param tag {@code tag} to begin search for matching {@code person}s.
     * @return Set of Persons falling under either the argument {@code tag} or its sub-tags.
     */
    public Set<Person> getAllPersonsUnderTag(Tag tag) {
        Set<Person> finalSet = new HashSet<>();
        Consumer<Tag> consumer = new Consumer<Tag>() {
            @Override
            public void accept(Tag tag) {
                if (addressBook.getPersonsWithTag(tag) == null) {
                    return;
                }
                finalSet.addAll(addressBook.getPersonsWithTag(tag));
                tagTree.getSubTagsOf(tag).stream().forEach(subTag -> accept(subTag));
            }
        };
        consumer.accept(tag);
        return finalSet;
    }


    /**
     * Deletes a {@code tag} from the existing tag tree.
     * {@code Person}s with the deleted {@code tag} will have the {@code tag} removed.
     *
     * @param tag {@code tag} to be deleted.
     */
    public void deleteTag(Tag tag) {
        assert tag != null;

        Set<Tag> superTagSet = tagTree.getSuperTagsOf(tag);
        tagTree.deleteTag(tag);
        superTagSet.stream().filter(superTag -> !hasTag(superTag))
                .forEach(superTag -> deleteTag(superTag));
        removeTagFromContactsInAddressBook(tag);
    }

    /**
     * Replaces each instance of a {@code person} with {@code tag} with a {@code person} without it.
     */
    private void removeTagFromContactsInAddressBook(Tag tag) {
        Set<Person> setCopy = Set.copyOf(addressBook.getPersonsWithTag(tag));
        setCopy.stream()
                .forEach(person -> addressBook.setPerson(person, copyPersonWithoutTag(person, tag)));
    }

    /**
     * Deletes {@code tag} and all its sub-{@code tag}s directly below {@code tag} in the tag hierarchy.
     * For example, given {@code CS2030 -> COMPUTING -> NUS}, deleting NUS with this method
     * will delete {@code CS2030} and {@code COMPUTING}.
     * Affected {@code Person}s will be updated accordingly.
     *
     * @param tag {@code tag} to begin the deletion.
     */
    public void deleteTagRecursive(Tag tag) {
        removeTagFromContactsInAddressBook(tag);
        Set<Tag> superTagSet = tagTree.getSuperTagsOf(tag);
        tagTree.getSubTagsOf(tag).forEach(subTag -> deleteTagRecursive(subTag));
        tagTree.deleteTagAndAllSubTags(tag);
        superTagSet.stream().filter(superTag -> !hasTag(superTag))
                .forEach(superTag -> deleteTag(superTag));
    }

    /**
     * Deletes {@code tag} and all {@code person} objects directly assigned to it.
     *
     * @param tag {@code tag} to be deleted together with the {@code person}s directly under this {@code tag}.
     */
    public void deleteTagAndDirectContacts(Tag tag) {
        removeContactsUnderTagFromAddressBook(tag);
        deleteTag(tag);
    }

    /**
     * Removes all {@code person}s directly under {@code tag} from the {@code addressbook}.
     */
    private void removeContactsUnderTagFromAddressBook(Tag tag) {
        Set<Person> setCopy = Set.copyOf(addressBook.getPersonsWithTag(tag));
        setCopy.stream()
                .forEach(person -> addressBook.removePerson(person));
    }

    /**
     * Deletes {@code tag} and all its sub-{@code tag}s directly below {@code tag} in the tag hierarchy.
     * All {@code person}s under any deleted {@code tag} will also be deleted.
     *
     * @param tag {@code tag} to begin recursive deletion.
     */
    public void deleteTagAndDirectContactsRecursive(Tag tag) {
        removeContactsUnderTagFromAddressBook(tag);
        Set<Tag> superTagSet = tagTree.getSuperTagsOf(tag);
        tagTree.getSubTagsOf(tag).stream().forEach(subTag -> deleteTagAndDirectContactsRecursive(subTag));
        tagTree.deleteTagAndAllSubTags(tag);
        superTagSet.stream().filter(superTag -> !hasTag(superTag))
                .forEach(superTag -> deleteTag(superTag));
    }

    /**
     * Deletes the {@code person} from the addressbook.
     * If this {@code person} is the only contact belonging to a specific tag, that tag is also deleted.
     */
    public void deletePerson(Person person) {
        Set<Tag> personTags = person.getTags();
        addressBook.removePerson(person);
        for (Tag tag : personTags) {
            if (!hasTag(tag)) {
                deleteTag(tag);
            }
        }
    }

    /**
     * Changes {@code person} in the addressbook to {@code editedPerson}.
     * If {@code person} is the only contact in a tag and {@code editedPerson} has them removed,
     * the tag is deleted as well.
     */
    public void setPerson(Person person, Person editedPerson) {
        Set<Tag> tagsRemoved = new HashSet<>(person.getTags());
        tagsRemoved.removeAll(editedPerson.getTags());
        addressBook.setPerson(person, editedPerson);
        for (Tag tag : tagsRemoved) {
            if (!hasTag(tag)) {
                deleteTag(tag);
            }
        }
    }

    /**
     * Removes the {@code tag} from a {@code person}.
     * If the {@code tag} no longer exists, the {@code tag} is deleted.
     */
    public void removePersonFromTag(Tag tag, Person person) {
        addressBook.removePersonFromTag(tag, person);
        if (!hasTag(tag)) {
            deleteTag(tag);
        }
    }

    /**
     * Returns a new {@code person} object which is nearly identical to {@code personToCopy} except with
     * {@code tagRemoved} removed.
     */
    private Person copyPersonWithoutTag(Person personToCopy, Tag tagRemoved) {
        HashSet<Tag> newTagSet = new HashSet<>(personToCopy.getTags());
        newTagSet.remove(tagRemoved);
        return new Person(personToCopy.getName(),
                personToCopy.getPhone(),
                personToCopy.getEmail(),
                personToCopy.getAddress(),
                newTagSet);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ContactTagIntegrationManager)) {
            return false;
        } else {
            ContactTagIntegrationManager other = (ContactTagIntegrationManager) o;
            return other.addressBook.equals(addressBook) && other.tagTree.equals(tagTree);
        }
    }

}
