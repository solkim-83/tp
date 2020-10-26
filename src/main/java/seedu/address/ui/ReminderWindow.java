package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.Reminder;

import java.util.logging.Logger;

public class ReminderWindow extends UiPart<Stage> {
    public static final String REMINDER_MESSAGE = "Here are your upcoming reminders: \n" + Reminder.remindersToPopUp();


    private static final Logger logger = LogsCenter.getLogger(IntroWindow.class);
    private static final String FXML = "ReminderWindow.fxml";

    @javafx.fxml.FXML
    private Label reminderMessage;

    /**
     * Creates a new IntroWindow.
     *
     * @param root Stage to use as the root of the IntroWindow.
     */
    public ReminderWindow(Stage root) {
        super(FXML, root);
        reminderMessage.setWrapText(true);
        reminderMessage.setText(REMINDER_MESSAGE);
    }

    /**
     * Creates a new IntroWindow.
     */
    public ReminderWindow() {
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
