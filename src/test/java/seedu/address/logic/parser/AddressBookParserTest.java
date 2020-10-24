package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddContactCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.ClearContactCommand;
import seedu.address.logic.commands.DeleteContactCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.EditContactCommand;
import seedu.address.logic.commands.EditContactCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindContactCommand;
import seedu.address.logic.commands.FindEventCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListContactCommand;
import seedu.address.logic.commands.ListEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.DescriptionContainsKeywordsPredicate;
import seedu.address.model.event.Event;
import seedu.address.model.person.ContactContainsFieldsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.EventUtil;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_addContact() throws Exception {
        Person person = new PersonBuilder().build();
        AddContactCommand command = (AddContactCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddContactCommand(person), command);
    }

    @Test
    public void parseCommand_clearContact() throws Exception {
        assertTrue(parser.parseCommand(ClearContactCommand.COMMAND_WORD + " " + ClearContactCommand.COMMAND_TYPE)
                instanceof ClearContactCommand);
        assertTrue(parser.parseCommand(
                ClearContactCommand.COMMAND_WORD + " " + ClearContactCommand.COMMAND_TYPE + " 3")
                instanceof ClearContactCommand);
    }

    @Test
    public void parseCommand_deleteContact() throws Exception {
        DeleteContactCommand command = (DeleteContactCommand) parser.parseCommand(
                DeleteContactCommand.COMMAND_WORD + " " + DeleteContactCommand.COMMAND_TYPE + " "
                        + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteContactCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_editContact() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditContactCommand command = (EditContactCommand) parser.parseCommand(EditContactCommand.COMMAND_WORD + " "
                + EditContactCommand.COMMAND_TYPE + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditContactCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_findContact() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        String phoneKeyword = "96789";
        String emailKeyword = "hotmail";
        FindContactCommand command = (FindContactCommand) parser.parseCommand(
                FindContactCommand.COMMAND_WORD + " "
                        + FindContactCommand.COMMAND_TYPE + " "
                        + PREFIX_NAME + keywords.stream().collect(Collectors.joining(" ")) + " "
                        + PREFIX_PHONE + phoneKeyword + " "
                        + PREFIX_EMAIL + emailKeyword);

        ContactContainsFieldsPredicate builtPredicate = new ContactContainsFieldsPredicate();
        builtPredicate.setNameKeywords(keywords);
        builtPredicate.setPhoneKeyword(phoneKeyword);
        builtPredicate.setEmailKeyword(emailKeyword);

        assertEquals(new FindContactCommand(builtPredicate), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_listContact() throws Exception {
        assertTrue(parser.parseCommand(ListContactCommand.COMMAND_WORD + " " + ListContactCommand.COMMAND_TYPE)
                instanceof ListContactCommand);
        assertTrue(parser.parseCommand(
                ListContactCommand.COMMAND_WORD + " " + ListContactCommand.COMMAND_TYPE + " 3")
                instanceof ListContactCommand);
    }

    @Test
    public void parseCommand_addEvent() throws Exception {
        Event event = new EventBuilder().build();
        AddEventCommand command = (AddEventCommand) parser.parseCommand(EventUtil.getAddCommand(event));
        assertEquals(new AddEventCommand(event), command);
    }

    @Test
    public void parseCommand_deleteEvent() throws Exception {
        DeleteEventCommand command = (DeleteEventCommand) parser.parseCommand(
                DeleteEventCommand.COMMAND_WORD + " " + DeleteEventCommand.COMMAND_TYPE + " "
                        + INDEX_FIRST_EVENT.getOneBased());
        assertEquals(new DeleteEventCommand(INDEX_FIRST_EVENT), command);
    }

    @Test
    public void parseCommand_editEvent() throws Exception {
        Event event = new EventBuilder().build();
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(event).build();
        EditEventCommand command = (EditEventCommand) parser.parseCommand(EditEventCommand.COMMAND_WORD + " "
                + EditEventCommand.COMMAND_TYPE + " " + INDEX_FIRST_EVENT.getOneBased() + " "
                + EventUtil.getEditEventDescriptorDetails(descriptor));
        assertEquals(new EditEventCommand(INDEX_FIRST_EVENT, descriptor), command);
    }

    @Test
    public void parseCommand_findEvent() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindEventCommand command = (FindEventCommand) parser.parseCommand(
                FindEventCommand.COMMAND_WORD + " "
                        + FindEventCommand.COMMAND_TYPE + " "
                        + String.join(" ", keywords));

        DescriptionContainsKeywordsPredicate builtPredicate = new DescriptionContainsKeywordsPredicate();
        builtPredicate.setKeywords(keywords);

        assertEquals(new FindEventCommand(builtPredicate), command);
    }

    @Test
    public void parseCommand_listEvent() throws Exception {
        assertTrue(parser.parseCommand(ListEventCommand.COMMAND_WORD + " " + ListEventCommand.COMMAND_TYPE)
                instanceof ListEventCommand);
        assertTrue(parser.parseCommand(
                ListEventCommand.COMMAND_WORD + " " + ListEventCommand.COMMAND_TYPE + " 3")
                instanceof ListEventCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
