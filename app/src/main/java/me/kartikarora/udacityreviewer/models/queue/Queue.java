package me.kartikarora.udacityreviewer.models.queue;

/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.models.queue
 * Project : UdacityReviewer
 * Date : 2/7/17
 */

public class Queue {

    private long project_id;
    private String name;
    private long position;

    public Queue() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getProject_id() {
        return project_id;
    }

    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }
}
