package seedu.address.logic.commands.tags;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.ContactTagIntegrationManagerTest.PERSON_MA1101R;
import static seedu.address.model.ContactTagIntegrationManagerTest.buildTestContactTagIntegrationManager;
import static seedu.address.testutil.TagTreeUtil.TAG_CS1231S;
import static seedu.address.testutil.TagTreeUtil.TAG_CS2040S_NOT_TREE;
import static seedu.address.testutil.TagTreeUtil.TAG_MA1101R;
import static seedu.address.testutil.TagTreeUtil.TAG_SCIENCE;
import static seedu.address.testutil.TagTreeUtil.TAG_SCIENCE_COMP;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.booleaninput.BooleanInput;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ContactTagIntegrationManager;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagTree;
import seedu.address.testutil.ModelStub;

public class DeleteTagCommandTest {

    @Test
    public void execute_tagNotPresent_throwsCommandException() {
        ModelStubWithTagsAndPersons model = new ModelStubWithTagsAndPersons();
        DeleteTagCommand command = new DeleteTagCommand(TAG_CS2040S_NOT_TREE, BooleanInput.isFalse());
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_deleteTag_success() throws CommandException {
        ModelStubWithTagsAndPersons model = new ModelStubWithTagsAndPersons();
        DeleteTagCommand command = new DeleteTagCommand(TAG_SCIENCE_COMP, BooleanInput.isFalse());

        Person person = model.getPersonsWithTag(TAG_SCIENCE_COMP).stream().findAny().get();
        Set<Tag> newTagSet = new HashSet<>(person.getTags());
        newTagSet.remove(TAG_SCIENCE_COMP);
        Person expectedPerson = new Person(person.getName(),
                person.getPhone(),
                person.getEmail(),
                person.getAddress(),
                newTagSet);

        command.execute(model);

        // Checks if the parent-tag child-tag reassignment has been done.
        assertTrue(model.getTagTree().getSubTagsOf(TAG_SCIENCE).containsAll(Set.of(TAG_CS1231S, TAG_MA1101R)));
        // Checks if tag is removed
        assertFalse(model.hasTag(TAG_SCIENCE_COMP));
        // Checks if person has tag removed
        assertTrue(model.hasPerson(expectedPerson));
    }

    @Test
    public void execute_deleteTagRecursive_success() throws CommandException {
        ModelStubWithTagsAndPersons model = new ModelStubWithTagsAndPersons();
        DeleteTagCommand command = new DeleteTagCommand(TAG_SCIENCE, BooleanInput.isTrue());

        command.execute(model);

        assertFalse(model.hasTag(TAG_SCIENCE)); //tag deleted
        assertFalse(model.hasTag(TAG_SCIENCE_COMP)); //1 level away deleted
        assertFalse(model.hasTag(TAG_MA1101R)); //2 levels away deleted

        // Check if a person with a recursively deleted tag has the tag removed
        Set<Tag> newTagSet = new HashSet<>(PERSON_MA1101R.getTags());
        newTagSet.remove(TAG_MA1101R);
        Person expectedMA1101RPersonWithoutTag = new Person(PERSON_MA1101R.getName(),
                PERSON_MA1101R.getPhone(),
                PERSON_MA1101R.getEmail(),
                PERSON_MA1101R.getAddress(),
                newTagSet);
        assertTrue(model.hasPerson(expectedMA1101RPersonWithoutTag));
    }


    private class ModelStubWithTagsAndPersons extends ModelStub {
        private ContactTagIntegrationManager manager = buildTestContactTagIntegrationManager();
        private AddressBook addressBook = manager.getAddressBook();
        private TagTree tagTree = manager.getTagTree();

        @Override
        public boolean hasTag(Tag tag) {
            return manager.hasTag(tag);
        }

        @Override
        public TagTree getTagTree() {
            return tagTree;
        }

        @Override
        public AddressBook getAddressBook() {
            return addressBook;
        }

        @Override
        public void deleteTag(Tag tag) {
            manager.deleteTag(tag);
        }

        @Override
        public void deleteTagRecursive(Tag tag) {
            manager.deleteTagRecursive(tag);
        }

        @Override
        public Set<Person> getPersonsWithTag(Tag tag) {
            return addressBook.getPersonsWithTag(tag);
        }

        @Override
        public Set<Tag> getSubTagsRecursive(Tag tag) {
            return tagTree.getSubTagsRecursive(tag);
        }

        @Override
        public boolean hasPerson(Person person) {
            return addressBook.hasPerson(person);
        }
    }

}
