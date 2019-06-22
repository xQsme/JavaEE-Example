/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.reminders.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jabroni
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ToDo {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    private long id;
    private String caption;
    private String description;
    private int priority;

    public ToDo() {
    }

    public ToDo(String caption, String description, int priority) {
        this.caption = caption;
        this.description = description;
        this.priority = priority;
    }
}
