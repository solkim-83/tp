package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


public class TagManagerImplTest {

    public static final Tag TAG_FRIENDS = new Tag("friends");
    public static final Tag TAG_OWES_MONEY = new Tag("owesMoney");
    public static final Tag TAG_MODULE = new Tag("CS2103");
    public static final Tag TAG_NOT_FOUND = new Tag("asdimsad");
    public static final Tag TAG_SCHOOL = new Tag("NUS");
    public static final Person BENSON_EDITED = new PersonBuilder(BENSON).withTags("friends", "CS2103", "NUS").build();

    public static TagManagerImpl createEmptyTagManager() {
        return new TagManagerImpl();
    }

    /**
     * Contains the tags "friends" (ALICE & BENSON) and "owesMoney" (BENSON)
     */
    public static TagManagerImpl createNonEmptyTagManager() {
        TagManagerImpl tagManager = new TagManagerImpl();
        tagManager.addNewPersonTags(ALICE);
        tagManager.addNewPersonTags(BENSON);
        return tagManager;
    }

    @Test
    public void getTags_nonExistentTag_emptyCollectionReturn() {
        assertTrue(createNonEmptyTagManager().getPersonsUnderTag(TAG_NOT_FOUND).isEmpty());
    }

    @Test
    public void getTags_existingTag_correctCollectionReturn() {
        Set<Person> personSet = createNonEmptyTagManager().getPersonsUnderTag(TAG_FRIENDS);
        assertTrue(personSet.contains(ALICE));
        assertTrue(personSet.contains(BENSON));

        personSet = createNonEmptyTagManager().getPersonsUnderTag(TAG_OWES_MONEY);
        assertTrue(personSet.contains(BENSON));
        assertFalse(personSet.contains(ALICE));

    }

    @Test
    public void addNewPerson_newTags_success() {
        // This relies on the fact that GEORGE's tag CS2103 is not inside the non-empty tag manager.
        TagManagerImpl tagManager = createEmptyTagManager();
        tagManager.addNewPersonTags(GEORGE);
        assertTrue(tagManager.getPersonsUnderTag(TAG_MODULE).contains(GEORGE));

        tagManager = createNonEmptyTagManager();
        tagManager.addNewPersonTags(GEORGE);
        assertTrue(tagManager.getPersonsUnderTag(TAG_MODULE).contains(GEORGE));
    }

    @Test
    public void deletePerson_validPersonWithTags_success() {
        TagManagerImpl tagManager = createNonEmptyTagManager();
        tagManager.deletePersonTags(BENSON);
        assertFalse(tagManager.getPersonsUnderTag(TAG_FRIENDS).contains(BENSON));
        assertTrue(tagManager.getPersonsUnderTag(TAG_OWES_MONEY).isEmpty());
    }

    @Test
    public void editPerson_personWithChangedTags_success() {
        TagManagerImpl tagManager = createNonEmptyTagManager();
        tagManager.updateExistingPersonTags(BENSON, BENSON_EDITED);

        assertTrue(tagManager.getPersonsUnderTag(TAG_OWES_MONEY).isEmpty());
        assertTrue(tagManager.getPersonsUnderTag(TAG_MODULE).contains(BENSON_EDITED));

        // Checks that the Person object under the friends tag is the new edited Person object instead of the old.
        assertFalse(tagManager.getPersonsUnderTag(TAG_FRIENDS).contains(BENSON));
        assertTrue(tagManager.getPersonsUnderTag(TAG_FRIENDS).contains(BENSON_EDITED));
    }

    @Test
    public void allMethods_personWithNoTags_noChange() {
        Person personNoTags = new PersonBuilder(FIONA).withTags().build();
        Person personNoTagsNewAddress = new PersonBuilder(personNoTags).withAddress("some place").build();
        TagManagerImpl tagManager = createNonEmptyTagManager();

        // add makes no change
        tagManager.addNewPersonTags(personNoTags);
        assertEquals(tagManager, createNonEmptyTagManager());

        // edit makes no change
        tagManager.updateExistingPersonTags(personNoTags, personNoTagsNewAddress);
        assertEquals(tagManager, createNonEmptyTagManager());

        // delete makes no change
        tagManager.deletePersonTags(personNoTagsNewAddress);
        assertEquals(tagManager, createNonEmptyTagManager());
    }


}
