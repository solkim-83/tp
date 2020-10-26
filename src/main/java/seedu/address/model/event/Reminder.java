package seedu.address.model.event;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.storage.ReminderStorage;


public class Reminder {

    private static String home = System.getProperty("user.dir");
    private static Path savePath = Paths.get(home, "data", "reminders.txt");
    private static ReminderStorage reminderStorage = new ReminderStorage(savePath);
    private static ArrayList<Reminder> reminders;

    private final Event eventToRemind;
    private final LocalDateTime reminderDate;


    static {
        try {
            reminders = reminderStorage.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param eventToRemind the event that you want the reminder for
     * @param daysInAdvance how many days in advance do you want to start getting reminders for this event
     */
    public Reminder(Event eventToRemind, int daysInAdvance) {
        this.eventToRemind = eventToRemind;
        this.reminderDate = eventToRemind.getTime().time.minusDays(daysInAdvance);
    }

    /**
     * @param eventToRemind the index of the event that you want the reminder for
     * @param date the starting date of when you will start getting reminders for this event
     */
    public Reminder(Event eventToRemind, String date) {
        this.eventToRemind = eventToRemind;
        this.reminderDate = LocalDateTime.parse(date);
    }

    /**
     * Adding and writing a reminder into the reminders arraylist and the storage concurrently.
     */
    public static void addReminder(Reminder reminder) throws IOException {
        reminders.add(reminder);
        reminderStorage.write(reminder);
    }

    /**
     * Calculates the difference between the current date and the date of the event.
     */
    public String countdown() {
        LocalDate now = LocalDate.now();
        long diff = ChronoUnit.DAYS.between(now, eventToRemind.getTime().time.toLocalDate());
        return String.valueOf(diff);
    }

    /**
     * Deletes reminders that are outdated.
     */
    public static void deleteObsolete() throws CommandException {
        for (Reminder r: reminders) {
            if (r.eventToRemind.getTime().time.isBefore(LocalDateTime.now())) {
                reminders.remove(r);
            }
        }
        try {
            reminderStorage.overwrite(reminders);
        } catch (IOException e) {
            throw new CommandException("Storage error within reminders");
        }
    }


    @Override
    public String toString() {
        return eventToRemind.toString() + " in " + countdown() + " days" + '\n';
    }

    /**
     * Prepares the reminder to be written into the reminders storage.
     */
    public String splitToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return eventToRemind.getDescription().fullDescription + "/" + eventToRemind.getTime().time.format(formatter)
                + "/" + reminderDate + "\n";
    }

    /**
     * Lists out all the reminders in a string form.
     */
    public static String remindersToShow() {
        reminders.sort(new Comparator<Reminder>() {
            @Override
            public int compare(Reminder o1, Reminder o2) {
                return o1.eventToRemind.getTime().time.compareTo(o2.eventToRemind.getTime().time);
            }
        });

        String result = "";
        int count = 1;
        for (Reminder r: reminders) {
            result += count + ". " + r.toString();
            count++;
        }
        return result;
    }

    /**
     * Returns true if there is any reminders currently active
     */
    public static boolean hasRemindersDue() {
        for (Reminder r: reminders) {
            if (r.reminderDate.toLocalDate().isEqual(LocalDate.now())
                    || r.reminderDate.toLocalDate().isBefore(LocalDate.now())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a string for the pop up reminder window to display
     */
    public static String remindersToPopUp() {
        reminders.sort(new Comparator<Reminder>() {
            @Override
            public int compare(Reminder o1, Reminder o2) {
                return o1.eventToRemind.getTime().time.compareTo(o2.eventToRemind.getTime().time);
            }
        });

        String result = "";
        int count = 1;
        for (Reminder r: reminders) {
            if (r.reminderDate.toLocalDate().isEqual(LocalDate.now())
                    || r.reminderDate.toLocalDate().isBefore(LocalDate.now())) {
                result += count + ". " + r.toString();
                count++;
            }
        }
        return result;
    }
}
