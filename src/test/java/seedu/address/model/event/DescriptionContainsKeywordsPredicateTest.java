package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EventBuilder;

public class DescriptionContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("test");
        List<String> secondPredicateKeywordList = Arrays.asList("test", "best");

        DescriptionContainsKeywordsPredicate firstPredicate = new DescriptionContainsKeywordsPredicate();
        firstPredicate.setKeywords(firstPredicateKeywordList);
        DescriptionContainsKeywordsPredicate secondPredicate = new DescriptionContainsKeywordsPredicate();
        secondPredicate.setKeywords(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DescriptionContainsKeywordsPredicate firstPredicateCopy = new DescriptionContainsKeywordsPredicate();
        firstPredicateCopy.setKeywords(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_descriptionContainsKeywords_returnsTrue() {
        EventBuilder testEvent1 = new EventBuilder().withDescription("Test Best");
        EventBuilder testEvent2 = new EventBuilder().withDescription("CS2103 Meeting");

        DescriptionContainsKeywordsPredicate predicate = new DescriptionContainsKeywordsPredicate();
        predicate.setKeywords(List.of("Test"));

        // One keyword
        assertTrue(predicate.test(testEvent1.build()));

        // Multiple keywords
        predicate.setKeywords(List.of("Test", "meeting"));
        assertTrue(predicate.test(testEvent1.build()));
        assertTrue(predicate.test(testEvent2.build()));
    }

    @Test
    public void test_descriptionDoesNotContainKeywords_returnsFalse() {
        EventBuilder testEvent1 = new EventBuilder().withDescription("Test Best");
        EventBuilder testEvent2 = new EventBuilder().withDescription("CS2103 Meeting");

        DescriptionContainsKeywordsPredicate predicate = new DescriptionContainsKeywordsPredicate();
        predicate.setKeywords(List.of("random"));

        // No matching description
        assertFalse(predicate.test(testEvent1.build()));
        assertFalse(predicate.test(testEvent2.build()));

    }
}
