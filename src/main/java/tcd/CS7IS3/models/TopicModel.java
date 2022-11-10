package tcd.CS7IS3.models;

public class TopicModel {
	private int number;
	private String title;
	private String description;
	private String narrative;

	public TopicModel() {
	}

	public TopicModel(int number, String title, String description, String narrative) {
		this.number = number;
		this.title = title;
		this.description = description;
		this.narrative = narrative;
	}

	@Override
	public String toString() {
		return "TopicModel{" +
				"number=" + number +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", narrative='" + narrative + '\'' +
				'}';
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

	public void setNumber(int number) {
		this.number = number;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setNarrative(String narrative) {
		this.narrative = narrative;
	}
}
