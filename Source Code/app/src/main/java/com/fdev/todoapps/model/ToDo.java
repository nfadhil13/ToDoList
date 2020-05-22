package com.fdev.todoapps.model;

public class ToDo {

    private int id;
    private String title;
    private String description;
    private String urgentLevel;
    private long addedDate;
    private long deadlineDate;

    public ToDo() {
    }

    public ToDo(String title, String description, String urgentLevel, long addedDate, long deadlineDate) {
        this.title = title;
        this.description = description;
        this.urgentLevel = urgentLevel;
        this.addedDate = addedDate;
        this.deadlineDate = deadlineDate;
    }

    public ToDo(int id, String title, String description, String urgentLevel, long addedDate, long deadlineDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.urgentLevel = urgentLevel;
        this.addedDate = addedDate;
        this.deadlineDate = deadlineDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrgentLevel() {
        return urgentLevel;
    }

    public void setUrgentLevel(String urgentLevel) {
        this.urgentLevel = urgentLevel;
    }

    public long getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(long addedDate) {
        this.addedDate = addedDate;
    }

    public long getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(long deadlineDate) {
        this.deadlineDate = deadlineDate;
    }
}
