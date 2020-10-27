package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.Calendar;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Time;
import seedu.address.model.event.association.FauxPerson;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }
    // TODO: add associated persons to the following
    public static Event[] getSampleEvents() {
        Set<FauxPerson> associatedPersonsTest1 = new HashSet<>();
        associatedPersonsTest1.add(new FauxPerson(getSamplePersons()[2]));
        associatedPersonsTest1.add(new FauxPerson(getSamplePersons()[0]));
        associatedPersonsTest1.add(new FauxPerson(getSamplePersons()[4]));
        Set<FauxPerson> associatedPersonsTest2 = new HashSet<>();
        associatedPersonsTest2.add(new FauxPerson(getSamplePersons()[5]));
        associatedPersonsTest2.add(new FauxPerson(getSamplePersons()[3]));
        associatedPersonsTest2.add(new FauxPerson(getSamplePersons()[1]));
        Set<FauxPerson> associatedPersonsTest3 = new HashSet<>();
        associatedPersonsTest3.add(new FauxPerson(getSamplePersons()[1]));
        associatedPersonsTest3.add(new FauxPerson(getSamplePersons()[3]));
        associatedPersonsTest3.add(new FauxPerson(getSamplePersons()[5]));
        return new Event[] {
            new Event(new Description("Night run"), new Time("25-10-2020 21:30"), associatedPersonsTest1),
            new Event(new Description("CS2103 Meeting"), new Time("30-10-2020 14:00"), associatedPersonsTest2),
            new Event(new Description("CCA outing"), new Time("03-11-2020 20:00"), associatedPersonsTest3)
        };
    }

    public static ReadOnlyCalendar getSampleCalendar() {
        Calendar sampleCalendar = new Calendar();
        for (Event sampleEvent : getSampleEvents()) {
            sampleCalendar.addEvent(sampleEvent);
        }
        return sampleCalendar;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
