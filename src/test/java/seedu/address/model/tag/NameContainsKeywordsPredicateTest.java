package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {

        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate();
        firstPredicate.setKeyword(firstPredicateKeyword);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate();
        secondPredicate.setKeyword(secondPredicateKeyword);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate();
        firstPredicateCopy.setKeyword(firstPredicateKeyword);
        assertEquals(firstPredicate, firstPredicateCopy);

        // different types -> returns false
        assertNotEquals(firstPredicate, 1);

        // null -> returns false
        assertNotEquals(firstPredicate, null);

        // different keyword -> returns false
        assertNotEquals(firstPredicate, secondPredicate);

    }

    @Test
    public void tagContainsKeyword_success() {

        Tag testTag = new Tag("first");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate();
        firstPredicate.setKeyword("firs");
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate();
        secondPredicate.setKeyword("first");
        NameContainsKeywordsPredicate thirdPredicate = new NameContainsKeywordsPredicate();
        thirdPredicate.setKeyword("FiRsT");

        // partial match
        assertTrue(firstPredicate.test(testTag));

        // full match
        assertTrue(secondPredicate.test(testTag));

        // case insensitive
        assertTrue(thirdPredicate.test(testTag));

    }

}
