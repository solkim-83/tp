package seedu.address.model.tag;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Tag}'s {@code tagName} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Tag> {

    public static final String NON_TAG_CONSTRAINTS = "Search specifiers cannot be empty! "
            + "Specify the field or remove the prefix!";
    private static final String EMPTY_FIELD = "\0";

    private String keyword = EMPTY_FIELD;

    public NameContainsKeywordsPredicate() {}

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Tag tag) {
        return hasKeywordMatch(tag);
    }

    private boolean hasKeywordMatch(Tag tag) {
        return keyword.isEmpty() || StringUtil.containsPartialMatchIgnoreCase(tag.tagName, keyword);
    }

    public static boolean isValidKeyword(String predicateField) {
        return !predicateField.isBlank();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keyword.equals(((NameContainsKeywordsPredicate) other).keyword)); // state check
    }

}
