package seedu.address.model;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagTree;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public class ContactTagIntegrationManager {

    private AddressBook addressBook;
    private TagTree tagTree;

    public ContactTagIntegrationManager(AddressBook addressBook, TagTree tagTree) {
        this.addressBook = addressBook;
        this.tagTree = tagTree;
    }

    public Set<Person> getAllPersonsUnderTag(Tag tag) {
        Set<Person> finalSet = new HashSet<>();
        BiConsumer<Set<Person>, Tag> biConsumer = new BiConsumer<Set<Person>, Tag>() {
            @Override
            public void accept(Set<Person> personSet, Tag tag) {
                personSet.addAll(addressBook.getPersonsWithTag(tag));
                tagTree.getSubTagsOf(tag).stream().forEach(subTag -> accept(finalSet, subTag));
            }
        };
        return finalSet;
    }

    public void deleteTag(Tag tag) {
        tagTree.deleteTag(tag);
        removeTagFromContactsInAddressBook(tag);
    }

    private void removeTagFromContactsInAddressBook(Tag tag) {
        addressBook.getPersonsWithTag(tag).stream()
                .forEach(person -> addressBook.setPerson(person, copyPersonWithoutTag(person, tag)));
    }

    public void deleteTagRecursive(Tag tag) {
        removeTagFromContactsInAddressBook(tag);
        tagTree.getSubTagsOf(tag).stream().forEach(subTag -> deleteTagRecursive(subTag));
        tagTree.deleteTagAndAllSubTags(tag);
    }

    public void deleteTagAndDirectContacts(Tag tag) {
        tagTree.deleteTag(tag);
        removeContactsUnderTagFromAddressBook(tag);
    }

    private void removeContactsUnderTagFromAddressBook(Tag tag) {
        addressBook.getPersonsWithTag(tag).stream()
                .forEach(person -> addressBook.removePerson(person));
    }

    public void deleteTagAndDirectContactsRecursive(Tag tag) {
        removeContactsUnderTagFromAddressBook(tag);
        tagTree.getSubTagsOf(tag).stream().forEach(subTag -> deleteTagAndDirectContactsRecursive(tag));
        tagTree.deleteTagAndAllSubTags(tag);
    }

    private Person copyPersonWithoutTag(Person personToCopy, Tag tagRemoved) {
        HashSet<Tag> newTagSet = new HashSet<>(personToCopy.getTags());
        newTagSet.remove(tagRemoved);
        return new Person(personToCopy.getName(),
                personToCopy.getPhone(),
                personToCopy.getEmail(),
                personToCopy.getAddress(),
                newTagSet);
    }

    private Person copyPersonWithTag(Person personToCopy, Tag tagAdded) {
        HashSet<Tag> newTagSet = new HashSet<>(personToCopy.getTags());
        newTagSet.add(tagAdded);
        return new Person(personToCopy.getName(),
                personToCopy.getPhone(),
                personToCopy.getEmail(),
                personToCopy.getAddress(),
                newTagSet);
    }

}
