package seedu.address.model.event;

import seedu.address.storage.ReminderStorage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

public class Reminder {

    private final Event eventToRemind;

    private final int daysInAdvance;


    private static String home = System.getProperty("user.dir");
    private static Path savePath = Paths.get(home, "text", "reminders.txt");
    private static ReminderStorage reminderStorage = new ReminderStorage(savePath);

    private static ArrayList<Reminder> reminders;

    static {
        try {
            reminders = reminderStorage.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Reminder(Event eventToRemind, int daysInAdvance) {
        this.eventToRemind = eventToRemind;
        this.daysInAdvance = daysInAdvance;
    }

    public static void addReminder(Reminder reminder) throws IOException {
        reminders.add(reminder);
        reminderStorage.write(reminder);
    }

    @Override
    public String toString() {
        return eventToRemind.toString() + " in " + daysInAdvance + " days" + '\n';
    }

    public String splitToString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return eventToRemind.getDescription().fullDescription + "/" + eventToRemind.getTime().time.format(formatter) + "/" + daysInAdvance + "\n";
    }

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
            count ++;
        }
        return result;
    }
}
