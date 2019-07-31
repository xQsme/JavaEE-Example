package com.airhacks.business.reminders.entity;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.PostPersist;

public class ToDOAuditor {

    @Inject
    Event<ToDo> events;

    @PostPersist
    public void onToDoUpdate(ToDo todo)
    {
        this.events.fire(todo);
    }

}
