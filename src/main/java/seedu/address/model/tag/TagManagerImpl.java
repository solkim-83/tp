package seedu.address.model.tag;

import seedu.address.model.person.Person;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Concrete implementation class of {@code TagManager} that uses a {@code HashMap}.
 */
public class TagManagerImpl implements TagManager {

    private static final String MESSAGE_ERROR_PERSON_NOT_FOUND = "Person instance not found in tag manager!";

    private final Map<Tag, Set<Person>> tagPersonSetMap;

    public TagManagerImpl() {
        tagPersonSetMap = new HashMap<>();
    }

    public Set<Person> getPersonsUnderTag(Tag tag) {
        return tagPersonSetMap.get(tag);
    }

    public void updateExistingPersonTags(Person oldPerson, Person newPerson) {
        for (Tag oldTag : oldPerson.getTags()) {
            Set<Person> tagSet = tagPersonSetMap.get(oldTag);
            Objects.requireNonNull(tagSet);
            if (!tagSet.contains(oldPerson)) {
                throw new NoSuchElementException(MESSAGE_ERROR_PERSON_NOT_FOUND);
            }

            tagSet.remove(oldPerson);
        }

        for (Tag newTag : newPerson.getTags()) {
            Optional.ofNullable(tagPersonSetMap.get(newTag))
                    .ifPresentOrElse(
                            set -> set.add(newPerson),
                            () -> tagPersonSetMap.put(newTag, new HashSet<>(List.of(newPerson)))
                    );
        }
    }

    public void updateNewPersonTags(Person person) {
        for (Tag newTag : person.getTags()) {
            Optional.ofNullable(tagPersonSetMap.get(newTag))
                    .ifPresentOrElse(
                            set -> set.add(person),
                            () -> tagPersonSetMap.put(newTag, new HashSet<>(List.of(person)))
                    );
        }
    }



}
