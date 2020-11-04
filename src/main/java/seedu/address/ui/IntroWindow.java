package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for an introduction page
 */
public class IntroWindow extends UiPart<Stage> {

    public static final String INTRO_MESSAGE =
            "Hi! I'm Athena! As this is your first time opening the app, I will give you a"
            + " short run-through of some commands to get you started.\n\n"
            + "Although Athena provides storage and management for contacts and events, the methods"
            + " detailed below apply to the contacts component.\n\n"
            + "Let's add a person named Jane Doe to the list; do: \"add -c n/Jane Doe\"\n"
            + "Hmm... it seems like we have quite a few contacts to sift through. Let's narrow the"
            + " list down to her alone; do: \"find -c Jane Doe\"\n"
            + "Great! It seems like you've managed to get Jane's number. Let's add it to her contact;"
            + " do: \"edit -c 1 p/91234567\"\n"
            + "Looks like you no longer need Jane's contact - let's find her first, then delete it"
            + " with \"delete -c 1\"\n\n"
            + "Good job! You've just learnt how to add, find, edit and delete a contact.\n\n"
            + "If you'd like a more detailed guide, click on the \"Help\" button found in"
            + " the toolbar.\n";

    private static final Logger logger = LogsCenter.getLogger(IntroWindow.class);
    private static final String FXML = "IntroWindow.fxml";

    @FXML
    private Label introMessage;

    /**
     * Creates a new IntroWindow.
     *
     * @param root Stage to use as the root of the IntroWindow.
     */
    public IntroWindow(Stage root) {
        super(FXML, root);
        introMessage.setWrapText(true);
        introMessage.setText(INTRO_MESSAGE);
    }

    /**
     * Creates a new IntroWindow.
     */
    public IntroWindow() {
        this(new Stage());
    }

    /**
     * Shows the intro window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing introduction page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Hides the intro window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the intro window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}
