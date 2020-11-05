package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_REMOVE_TAG = new Prefix("rt/");
    public static final Prefix PREFIX_INDEX = new Prefix("i/");
    public static final Prefix PREFIX_REMOVE_INDEX = new Prefix("ri/");
    public static final Prefix PREFIX_RECURSIVE = new Prefix("r/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_DATETIME = new Prefix("at/");
    public static final Prefix PREFIX_REMIND_IN = new Prefix("in/");
    public static final Prefix PREFIX_ADD_PERSON = new Prefix("ap/");
    public static final Prefix PREFIX_REMOVE_PERSON = new Prefix("rp/");
    public static final Prefix PREFIX_SUPERTAG_ONLY = new Prefix("st/");

    /* Symbol definitions */
    public static final String SYMBOL_WILDCARD = "*";

}
