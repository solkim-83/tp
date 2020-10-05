package seedu.address.model.event;

public class Event {
    String description;

    Event(String description) {
        this.description = description;
    }

    String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDescription())
                .append(" Description: ");
        return builder.toString();
    }
}
