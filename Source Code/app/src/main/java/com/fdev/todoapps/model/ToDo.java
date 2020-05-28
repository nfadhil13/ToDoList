package com.fdev.todoapps.model;

public class ToDo {

    private int id;
    private String title;
    private int hour;
    private int minute;
    private String urgentLevel;
    private long addedDate;
    private long deadlineDate;


    public ToDo() {
    }

    public ToDo(String title, String description, int hour, int minute, String urgentLevel, long addedDate, long deadlineDate) {
        this.title = title;
        this.hour = hour;
        this.minute = minute;
        this.urgentLevel = urgentLevel;
        this.addedDate = addedDate;
        this.deadlineDate = deadlineDate;
    }

    public ToDo(int id, String title, String description, int hour, int minute, String urgentLevel, long addedDate, long deadlineDate) {
        this.id = id;
        this.title = title;
        this.hour = hour;
        this.minute = minute;
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



    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
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
