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
import java.util.List;
import java.util.Set;

/**
 *
 * @author jabroni
 */
@Model
public class Index {

    @Inject
    ToDoManager boundary;

    ToDo todo;

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

    public List<ToDo> getToDos()
    {
        return this.boundary.getAll();
    }

    public void showValidationError(String content)
    {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, content, content);
        FacesContext.getCurrentInstance().addMessage("", message);
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
            this.boundary.save(todo);
        }
        return null;
    }

}
