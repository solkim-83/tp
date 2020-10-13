package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given and
 * any of the {@code Person}'s {@code phone}, {@code email}, {@code address} and {@code tags}
 * contains the specified search fields.
 * If a field is missing, it is considered 'matched' by default.
 */
public class ContactContainsFieldsPredicate implements Predicate<Person> {

    public static final String NON_TAG_CONSTRAINTS = "Search specifiers cannot be empty! "
            + "Specify the field or remove the prefix!";
    private static final String EMPTY_FIELD = "\0";

    private List<String> nameKeywords = new ArrayList<>();
    private String phoneKeyword = EMPTY_FIELD;
    private String emailKeyword = EMPTY_FIELD;
    private String addressKeyword = EMPTY_FIELD;
    private List<String> tags = new ArrayList<>();

    public ContactContainsFieldsPredicate() {}

    @Override
    public boolean test(Person person) {
        return hasNameMatch(person)
                && hasNonTagMatch(person)
                && hasTagMatch(person);
    }

    /**
     * Checks for FULL name keyword matches between the person's name and the provided keywords.
     * True if list is empty.
     */
    private boolean hasNameMatch(Person person) {
        return nameKeywords.isEmpty() || nameKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
    }

    /**
     * Checks that the field's string representation contains matchField. If matchField is empty, true by default.
     */
    private boolean checkFieldMatch(Object field, String matchField) {
        return matchField.equals(EMPTY_FIELD) || field.toString().toLowerCase().contains(matchField.toLowerCase());
    }

    /**
     * Checks that the person's phone, email and address matches the provided keywords.
     */
    private boolean hasNonTagMatch(Person person) {
        return checkFieldMatch(person.getPhone(), phoneKeyword)
                && checkFieldMatch(person.getEmail(), emailKeyword)
                && checkFieldMatch(person.getAddress(), addressKeyword);
    }

    /**
     * Checks that the person contains all the tags specified
     */
    private boolean hasTagMatch(Person person) {
        return tags.stream()
                .map(Tag::new)
                .allMatch(tag -> person.getTags().contains(tag));
    }

    public void setNameKeywords(List<String> nameKeywords) {
        this.nameKeywords = nameKeywords;
    }

    public void setPhoneKeyword(String phoneKeyword) {
        this.phoneKeyword = phoneKeyword;
    }

    public void setEmailKeyword(String emailKeyword) {
        this.emailKeyword = emailKeyword;
    }

    public void setAddressKeyword(String addressKeyword) {
        this.addressKeyword = addressKeyword;
    }

    public void setTagKeywords(List<String> tags) {
        this.tags = tags;
    }

    /**
     * Returns true if all of the fields are the default settings, indicating nothing to test.
     */
    public boolean isEmptyPredicate() {
        return nameKeywords.size() == 0
                && phoneKeyword.equals(EMPTY_FIELD)
                && emailKeyword.equals(EMPTY_FIELD)
                && addressKeyword.equals(EMPTY_FIELD)
                && tags.size() == 0;
    }

    /**
     * Checks the validity of the predicate field
     */
    public static boolean isValidPredicateField(String predicateField) {
        return !predicateField.isBlank();
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
