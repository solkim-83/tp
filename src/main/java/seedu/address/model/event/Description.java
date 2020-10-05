package seedu.address.model.event;

public class Description {
    public String fullDescription;

    public Description(String description) {
        fullDescription = description;
    }

    @Override
    public String toString() {
        return fullDescription;
    }

}
