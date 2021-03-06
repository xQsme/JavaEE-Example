/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.presentation;

import com.airhacks.business.reminders.boundary.ToDoManager;
import com.airhacks.business.reminders.entity.ToDo;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author jabroni
 */
@Model
public class ToDoPage {

    @Inject
    ToDoManager boundary;

    private ToDo todo;

    private ToDo todoToDelete;

    private ToDo todoToChange;

    @Inject
    Validator validator;

    @PostConstruct
    public void init()
    {
        this.todo = new ToDo();
    }

    public ToDo getTodo()
    {
        return this.todo;
    }

    public void setTodo(ToDo todo) {
        this.todo = todo;
    }

    public void setTodoToChange(ToDo todoToChange) {
        this.todoToChange = todoToChange;
    }

    public void setTodoToDelete(ToDo todoToDelete) {
        this.todoToDelete = todoToDelete;
    }

    public List<ToDo> getToDos()
    {
        List<ToDo> list = this.boundary.getAll();
        long index = 0;
        for (ToDo t : list)
        {
            t.setIndex(index++);
        }
        return list;
    }

    private void showValidationError(String content)
    {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Validation", content);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public Object save()
    {
        Set<ConstraintViolation<ToDo>> violations = this.validator.validate(this.todo);
        for(ConstraintViolation<ToDo> violation : violations)
        {
            this.showValidationError(violation.getMessage());
        }
        if(violations.isEmpty())
        {
            todo.setId(0);
            this.boundary.save(todo);
            this.addMessage("Save Attempt", "Data Saved");
        }
        return null;
    }

    public Object select()
    {
        this.addMessage("Edit", "Row Selected");
        System.out.println(todo);
        return null;
    }

    public Object cancel()
    {
        this.addMessage("Edit", "Edition Canceled");
        return null;
    }

    public Object edit()
    {
        Set<ConstraintViolation<ToDo>> violations = this.validator.validate(this.todo);
        for(ConstraintViolation<ToDo> violation : violations)
        {
            this.showValidationError(violation.getMessage());
        }
        if(violations.isEmpty()) {
            this.boundary.save(todo);
            this.addMessage("Edit", "Row Edited");
        }
        return null;
    }

    public Object delete()
    {
        this.boundary.delete(todoToDelete.getId());
        this.addMessage("Delete Attempt", "Row Deleted");
        return null;
    }

    public Object change()
    {
        todoToChange.setDone(!todoToChange.isDone());
        this.boundary.save(todoToChange);
        this.addMessage("Change", "Status Changed");
        return null;
    }

    private void addMessage(String title, String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, title, summary);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
