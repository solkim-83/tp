package seedu.address.model.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Tag}'s {@code tagName} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Tag> {

    public static final String NON_TAG_CONSTRAINTS = "Search specifiers cannot be empty! "
            + "Specify the field or remove the prefix!";

    private List<String> keywords = new ArrayList<>();

    public NameContainsKeywordsPredicate() {}

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Tag tag) {
        return hasKeywordMatch(tag);
    }

    private boolean hasKeywordMatch(Tag tag) {
        return keywords.isEmpty() || keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(tag.tagName, keyword));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
