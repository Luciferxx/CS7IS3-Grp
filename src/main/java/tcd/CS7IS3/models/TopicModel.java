package tcd.CS7IS3.models;

public class TopicModel {
    private int number;
    private String title;
    private String description;
    private String narrative;

    public TopicModel(int number, String title, String description, String narrative) {
        this.number = number;
        this.title = title;
        this.description = description;
        this.narrative = narrative;
    }

    public int getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getNarrative() {
        return narrative;
    }
}
