/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.presentation;

import com.airhacks.business.reminders.boundary.ToDoManager;
import com.airhacks.business.reminders.entity.ToDo;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

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

    private MenuModel model;

    @Inject
    ToDoManager boundary;

    ToDo todo;

    ToDo todoToDelete;

    @Inject
    Validator validator;

    @PostConstruct
    public void init()
    {
        this.todo = new ToDo();

        model = new DefaultMenuModel();
        DefaultSubMenu secondSubmenu = new DefaultSubMenu("Dynamic Actions");
        DefaultMenuItem item = new DefaultMenuItem("Save");
        item.setIcon("pi pi-save");
        item.setUpdate("messages");
        secondSubmenu.addElement(item);
        model.addElement(secondSubmenu);
    }

    public ToDo getTodo()
    {
        return this.todo;
    }

    public void setTodo(ToDo todo) {
        this.todo = todo;
    }

    public void setTodoToDelete(ToDo todoToDelete) {
        this.todoToDelete = todoToDelete;
    }

    public List<ToDo> getToDos()
    {
        return this.boundary.getAll();
    }

    public void showValidationError(String content)
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

    public void addMessage(String title, String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, title, summary);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
