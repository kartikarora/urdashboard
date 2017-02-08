package me.kartikarora.udacityreviewer.models.queue;

/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.models.queue
 * Project : UdacityReviewer
 * Date : 2/7/17
 */

public class Queue {

    private Integer project_id;
    private String name;
    private Integer position;

    public Queue() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
