package de.nogaller.rest;

public class Task {

	private String text;
	private String dueDate;

	public Task() {
		this("", "");
	}

	public Task(String text, String dueDate) {
		this.text = text;
		this.dueDate = dueDate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	@Override
	public String toString() {
		return String.format("{\"text\":\"%s\",\"date\":\"%s\"}", text, dueDate);
	}
}
