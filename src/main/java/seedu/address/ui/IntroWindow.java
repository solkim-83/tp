package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

public class IntroWindow extends UiPart<Stage> {

    public static final String INTRO_MESSAGE = "placeholder";

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
     * Returns true if the intro window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
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
