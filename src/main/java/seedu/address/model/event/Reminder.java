package seedu.address.model.event;

import java.util.ArrayList;
import java.util.Comparator;

public class Reminder {

    private final Event eventToRemind;

    private final int daysInAdvance;

    private static ArrayList<Reminder> reminders = new ArrayList<Reminder>();

    public Reminder(Event eventToRemind, int daysInAdvance) {
        this.eventToRemind = eventToRemind;
        this.daysInAdvance = daysInAdvance;
    }

    public static void addReminder(Reminder reminder) {
        reminders.add(reminder);
    }

    @Override
    public String toString() {
        return eventToRemind.toString() + " in " + daysInAdvance + " days" + '\n';
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
