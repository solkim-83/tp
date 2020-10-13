package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class ContactContainsFieldsPredicate implements Predicate<Person> {

    private static final String EMPTY_FIELD = "\0";

    private final List<String> nameKeywords;
    private final String phoneKeyword;
    private final String emailKeyword;
    private final String addressKeyword;
    private final List<String> tags;

    public ContactContainsFieldsPredicate(List<String> nameKeywords) {
        this.nameKeywords = nameKeywords;
        this.phoneKeyword = EMPTY_FIELD;
        this.emailKeyword = EMPTY_FIELD;
        this.addressKeyword = EMPTY_FIELD;
        this.tags = new ArrayList<>();
    }

    public ContactContainsFieldsPredicate(List<String> nameKeywords, String phoneKeyword,
              String emailKeyword, String addressKeyword, List<String> tags) {
        this.nameKeywords = nameKeywords;
        this.phoneKeyword = phoneKeyword;
        this.emailKeyword = emailKeyword;
        this.addressKeyword = addressKeyword;
        this.tags = tags;
    }

    @Override
    public boolean test(Person person) {
        return hasNameMatch(person)
                && hasNonTagMatch(person)
                && hasTagMatch(person);
    }

    private boolean hasNameMatch(Person person) {
        return nameKeywords.isEmpty() || nameKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
    }

    private boolean checkFieldMatch(Object field, String matchField) {
        return matchField.equals(EMPTY_FIELD) || field.toString().contains(matchField);
    }

    private boolean hasNonTagMatch(Person person) {
        return checkFieldMatch(person.getPhone(), phoneKeyword)
                && checkFieldMatch(person.getEmail(), emailKeyword)
                && checkFieldMatch(person.getAddress(), addressKeyword);
    }

    private boolean hasTagMatch(Person person) {
        return tags.stream()
                .map(Tag::new)
                .allMatch(tag -> person.getTags().contains(tag));
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        } else if (!(object instanceof ContactContainsFieldsPredicate)) {
            return false;
        }

        ContactContainsFieldsPredicate otherObject = (ContactContainsFieldsPredicate) object;
        return this.nameKeywords.equals(otherObject.nameKeywords)
                && phoneKeyword.equals(otherObject.phoneKeyword)
                && emailKeyword.equals(otherObject.emailKeyword)
                && addressKeyword.equals(otherObject.addressKeyword)
                && tags.equals(otherObject.tags);
    }

}
