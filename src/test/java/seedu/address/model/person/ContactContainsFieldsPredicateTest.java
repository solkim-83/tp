package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class ContactContainsFieldsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ContactContainsFieldsPredicate firstPredicate = new ContactContainsFieldsPredicate();
        firstPredicate.setNameKeywords(firstPredicateKeywordList);
        ContactContainsFieldsPredicate secondPredicate = new ContactContainsFieldsPredicate();
        secondPredicate.setNameKeywords(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContactContainsFieldsPredicate firstPredicateCopy = new ContactContainsFieldsPredicate();
        firstPredicateCopy.setNameKeywords(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        PersonBuilder testPerson1 = new PersonBuilder().withName("Alice John");
        PersonBuilder testPerson2 = new PersonBuilder().withName("Doe Dark");

        ContactContainsFieldsPredicate predicate = new ContactContainsFieldsPredicate();
        predicate.setNameKeywords(List.of("Alice"));

        // One keyword
        assertTrue(predicate.test(testPerson1.build()));

        // Multiple keywords
        predicate.setNameKeywords(List.of("Alice", "Doe"));
        assertTrue(predicate.test(testPerson1.build()));
        assertTrue(predicate.test(testPerson2.build()));

        // Matching email with matching name
        predicate.setEmailKeyword("hotmail");
        testPerson1.withEmail("abcde@hotmail");
        assertTrue(predicate.test(testPerson1.build()));

        // Matching email with no name keywords specified
        testPerson2.withEmail("bcdef@hotmail.com");
        predicate.setNameKeywords(List.of());
        assertTrue(predicate.test(testPerson1.build()));
        assertTrue(predicate.test(testPerson2.build()));

        // Match tags contained by person1
        predicate.setTagKeywords(List.of("CS2103", "CS2101"));
        testPerson1.withTags("CS2103", "CS2100", "CS2101");
        assertTrue(predicate.test(testPerson1.build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        PersonBuilder testPerson1 = new PersonBuilder().withName("Alice John");
        PersonBuilder testPerson2 = new PersonBuilder().withName("Doe Dark");

        ContactContainsFieldsPredicate predicate = new ContactContainsFieldsPredicate();
        predicate.setNameKeywords(List.of("Alice"));

        // Matching email without matching name
        testPerson2.withEmail("bcdef@hotmail.com");
        predicate.setEmailKeyword("hotmail");
        assertFalse(predicate.test(testPerson2.build()));

        // Matching name and email without matching phone
        testPerson1.withEmail("abcde@hotmail");
        testPerson1.withPhone("91234567");
        predicate.setPhoneKeyword("7654");
        assertFalse(predicate.test(testPerson1.build()));

    }
}
