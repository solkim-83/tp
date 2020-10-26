package seedu.address.storage;

import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Reminder;
import seedu.address.model.event.Time;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ReminderStorage {
    Path filepath;

    /**
     * Constructor for a new driver system.
     *
     * @param filepath  the file path of the schedule text file
     */
    public ReminderStorage(Path filepath) {
        this.filepath = filepath;
    }

    /**
     * Writes tasks into the schedule file.
     *
     * @param reminder      the task that's to be written into the text file
     */
    public void write(Reminder reminder) throws IOException {

        File saveFile = filepath.toFile();
        try {
            if (!saveFile.exists()) {
                System.out.println("Save file does not exist, creating one at " + this.filepath);
                saveFile.getParentFile().mkdirs();
            }
            FileWriter todoWriter = new FileWriter(saveFile, true);
            todoWriter.write(reminder.splitToString());
            todoWriter.close();
        } catch (IOException ioe) {
            System.out.println("Couldn't save to file");
        }
    }


    /**
     * Loads a schedule file.
     *
     * @return  a TaskList that has all the tasks in the schedule text file
     */
    public ArrayList<Reminder> load() throws IOException {
        ArrayList<Reminder> reminders = new ArrayList<Reminder>();
        File saveFile = filepath.toFile();
        if(!saveFile.exists()) {
            return new ArrayList<Reminder>();
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(saveFile));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] reminderArr = line.split("/");
                Event event = new Event(new Description(reminderArr[0]), new Time(reminderArr[1]));
                Reminder reminder = new Reminder(event, reminderArr[2]);
                reminders.add(reminder);
            }
        } catch (IOException e) {
           throw new IOException("File couldn't be read properly");
        }

        return reminders;
    }

    /**
     * Writes tasks into the schedule file.
     *
     * @param reminders      the TaskList that's to be over written into the text file
     */
    public void overwrite(ArrayList<Reminder> reminders) throws IOException {
        File saveFile = filepath.toFile();
        FileWriter todoWriter = new FileWriter(saveFile, false);
        for (Reminder reminder: reminders) {
            todoWriter.write(reminder.splitToString());
        }
        todoWriter.close();
    }


}
