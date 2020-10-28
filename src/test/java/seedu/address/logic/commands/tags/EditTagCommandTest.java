package seedu.address.logic.commands.tags;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.ContactTagIntegrationManagerTest.PERSON_CS1231S_1;
import static seedu.address.model.ContactTagIntegrationManagerTest.buildTestContactTagIntegrationManager;
import static seedu.address.testutil.TagTreeUtil.TAG_ARCHITECTURE;
import static seedu.address.testutil.TagTreeUtil.TAG_CS1231S;
import static seedu.address.testutil.TagTreeUtil.TAG_CS2040S_NOT_TREE;
import static seedu.address.testutil.TagTreeUtil.TAG_MA1101R;
import static seedu.address.testutil.TagTreeUtil.TAG_NUS;
import static seedu.address.testutil.TagTreeUtil.TAG_SCIENCE;
import static seedu.address.testutil.TagTreeUtil.TAG_SCIENCE_COMP;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ContactTagIntegrationManager;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagTree;
import seedu.address.testutil.ModelStub;


public class EditTagCommandTest {

    @Test
    public void execute_tagToEditNotPresent_throwsCommandException() {
        ModelStubWithTagAndPersons model = new ModelStubWithTagAndPersons();
        EditTagCommand command = new EditTagCommand(TAG_CS2040S_NOT_TREE, Set.of(), Set.of(),
                Set.of(TAG_CS1231S), Set.of());
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_argumentNotValid_throwsCommandException() {
        ModelStubWithTagAndPersons model = new ModelStubWithTagAndPersons();

        // Remove person who already does not have the tag
        EditTagCommand command = new EditTagCommand(TAG_MA1101R, Set.of(), Set.of(Index.fromZeroBased(0)),
                Set.of(), Set.of());
        assertThrows(CommandException.class, () -> command.execute(model));
        // Add person who already has the tag
        EditTagCommand command2 = new EditTagCommand(TAG_CS1231S, Set.of(Index.fromZeroBased(0)), Set.of(),
                Set.of(), Set.of());
        assertThrows(CommandException.class, () -> command2.execute(model));
        // Remove child-tag that is not a child-tag
        EditTagCommand command3 = new EditTagCommand(TAG_SCIENCE, Set.of(), Set.of(),
                Set.of(), Set.of(TAG_NUS));
        assertThrows(CommandException.class, () -> command3.execute(model));
        // Add child-tag that is already a child-tag
        EditTagCommand command4 = new EditTagCommand(TAG_SCIENCE, Set.of(), Set.of(),
                Set.of(TAG_SCIENCE_COMP), Set.of());
        assertThrows(CommandException.class, () -> command4.execute(model));
    }

    @Test
    public void execute_attemptedCyclicTagAdded_throwsCommandException() {
        ModelStubWithTagAndPersons model = new ModelStubWithTagAndPersons();
        EditTagCommand command = new EditTagCommand(TAG_SCIENCE, Set.of(), Set.of(),
                Set.of(TAG_NUS), Set.of());
        EditTagCommand command2 = new EditTagCommand(TAG_MA1101R, Set.of(), Set.of(),
                Set.of(TAG_NUS), Set.of());
        assertThrows(CommandException.class, () -> command.execute(model));
        assertThrows(CommandException.class, () -> command2.execute(model));
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        ModelStubWithTagAndPersons model = new ModelStubWithTagAndPersons();
        EditTagCommand command = new EditTagCommand(TAG_CS1231S, Set.of(Index.fromZeroBased(2)),
                Set.of(Index.fromZeroBased(0)), Set.of(TAG_MA1101R), Set.of());
        command.execute(model);
        // Test case depends on the underlying order in ContactTagIntegrationManagerTest
        assertFalse(model.getPersonsWithTag(TAG_CS1231S).contains(PERSON_CS1231S_1));
        assertTrue(model.getPersonsWithTag(TAG_CS1231S).size() == 2);
        assertTrue(model.getChildTags(TAG_CS1231S).contains(TAG_MA1101R));

        Set<Tag> newTagSet = new HashSet<>(PERSON_CS1231S_1.getTags());
        newTagSet.remove(TAG_CS1231S);
        Person expectedPerson = new Person(PERSON_CS1231S_1.getName(),
                PERSON_CS1231S_1.getPhone(),
                PERSON_CS1231S_1.getEmail(),
                PERSON_CS1231S_1.getAddress(),
                newTagSet);
        assertTrue(model.hasPerson(expectedPerson));

        command = new EditTagCommand(TAG_NUS, Set.of(), Set.of(), Set.of(), Set.of(TAG_ARCHITECTURE));
        command.execute(model);
        assertFalse(model.getChildTags(TAG_NUS).contains(TAG_ARCHITECTURE));
    }

    private class ModelStubWithTagAndPersons extends ModelStub {
        private ContactTagIntegrationManager manager = buildTestContactTagIntegrationManager();
        private AddressBook addressBook = manager.getAddressBook();
        private TagTree tagTree = manager.getTagTree();

        @Override
        public void addSubTagTo(Tag superTag, Tag subTag) {
            tagTree.addSubTagTo(superTag, subTag);
        }

        @Override
        public void addPersonToTag(Tag tag, Person person) {
            addressBook.addPersonToTag(tag, person);
        }

        @Override
        public boolean hasTag(Tag tag) {
            return manager.hasTag(tag);
        }

        @Override
        public void removePersonFromTag(Tag tag, Person person) {
            addressBook.removePersonFromTag(tag, person);
        }

        @Override
        public void removeChildTagFrom(Tag parentTag, Tag childTag) {
            tagTree.removeSubTagFrom(parentTag, childTag);
        }

        @Override
        public ObservableList<Person> getSortedFilteredPersonList() {
            return addressBook.getPersonList();
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
        public Set<Tag> getChildTags(Tag tag) {
            return tagTree.getSubTagsOf(tag);
        }

        @Override
        public boolean isSubTagOf(Tag superTag, Tag subTag) {
            return tagTree.isSubTagOf(superTag, subTag);
        }

        @Override
        public Set<Person> getPersonsWithTag(Tag tag) {
            return addressBook.getPersonsWithTag(tag);
        }

        @Override
        public boolean hasPerson(Person person) {
            return addressBook.hasPerson(person);
        }

    }


}
