package seedu.address.testutil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagTreeImpl;

/**
 * Utility class to help testing of tag trees.
 */
public class TagTreeUtil {

    public static final Tag TAG_NUS = new Tag("nus");
    public static final Set<Tag> SET_NUS = new HashSet<>(Set.of(TAG_NUS));

    public static final Tag TAG_COMPUTING = new Tag("computing");
    public static final Tag TAG_SCIENCE = new Tag("science");
    public static final Tag TAG_ARCHITECTURE = new Tag("architecture");
    public static final Set<Tag> SET_ARCHITECTURE = new HashSet<>(Set.of(TAG_ARCHITECTURE));
    public static final Set<Tag> SET_FACULTIES = new HashSet<>(Set.of(TAG_COMPUTING, TAG_SCIENCE, TAG_ARCHITECTURE));
    public static final Set<Tag> SET_SCIENCE_COMP_SUPERTAGS = new HashSet<>(Set.of(TAG_SCIENCE, TAG_COMPUTING));

    public static final Tag TAG_SCIENCE_COMP = new Tag("sciencecomp");
    public static final Set<Tag> SET_SCIENCE_COMP = new HashSet<>(Set.of(TAG_SCIENCE_COMP));

    public static final Tag TAG_MA1101R = new Tag("ma1101r");
    public static final Tag TAG_CS1231S = new Tag("cs1231s");
    public static final Set<Tag> SET_MODULES = new HashSet<>(Set.of(TAG_MA1101R, TAG_CS1231S));

    public static final Tag TAG_ARCHITECTURE_MOD = new Tag("architecturemod");
    public static final Set<Tag> SET_ARCHITECTURE_MOD = new HashSet<>(Set.of(TAG_ARCHITECTURE_MOD));

    public static final Tag TAG_NOT_IN_TREE = new Tag("notintree");
    public static final Tag TAG_CS2040S_NOT_TREE = new Tag("cs2040s");

    /**
     * Returns a sample tag tree for testing.
     */
    public static TagTreeImpl buildTestTree() {
        Map<Tag, Set<Tag>> mapSubTag = new HashMap<>();
        Map<Tag, Set<Tag>> mapSuperTag = new HashMap<>();

        mapSubTag.put(TAG_NUS, new HashSet<>(SET_FACULTIES));

        mapSubTag.put(TAG_ARCHITECTURE, new HashSet<>(SET_ARCHITECTURE_MOD));

        mapSubTag.put(TAG_COMPUTING, new HashSet<>(SET_SCIENCE_COMP));
        mapSubTag.put(TAG_SCIENCE, new HashSet<>(SET_SCIENCE_COMP));

        mapSubTag.put(TAG_SCIENCE_COMP, new HashSet<>(SET_MODULES));

        mapSuperTag.put(TAG_CS1231S, new HashSet<>(SET_SCIENCE_COMP));
        mapSuperTag.put(TAG_MA1101R, new HashSet<>(SET_SCIENCE_COMP));

        mapSuperTag.put(TAG_ARCHITECTURE_MOD, new HashSet<>(SET_ARCHITECTURE));

        mapSuperTag.put(TAG_SCIENCE_COMP, new HashSet<>(SET_SCIENCE_COMP_SUPERTAGS));

        mapSuperTag.put(TAG_ARCHITECTURE, new HashSet<>(SET_NUS));
        mapSuperTag.put(TAG_COMPUTING, new HashSet<>(SET_NUS));
        mapSuperTag.put(TAG_SCIENCE, new HashSet<>(SET_NUS));

        return new TagTreeImpl(new HashMap<>(mapSubTag), new HashMap<>(mapSuperTag));
    }

}
