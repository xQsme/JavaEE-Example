/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.business.reminders.boundary;

import com.airhacks.business.logging.boundary.BoundaryLogger;
import com.airhacks.business.reminders.entity.ToDo;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jabroni
 */
@Stateless
@Interceptors(BoundaryLogger.class)
public class ToDoManager {

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void init()
    {
        save(new ToDo("Create Project","Using Maven ¯\\_(ツ)_/¯",1, true));
        save(new ToDo("Implement CRUD","For the ToDo Class",10, true));
        save(new ToDo("Test CRUD","Including version tag, using REST API",15, true));
        save(new ToDo("Implement Logging","Log every call to the ToDoManager",2, true));
        save(new ToDo("Implement Statistics","Simple API to check stats",0, true));
        save(new ToDo("Implement Index","Simple reminder creation and data table",20, true));
        save(new ToDo("Use Primefaces","Different themes and components",15, true));
        save(new ToDo("Implement Second Class","With the required pages and controllers",5, true));
        save(new ToDo("Make a Better Home Page","Using a banner and some other components",15, false));
        save(new ToDo("Make a Third Class","No idea what to use but should involve different procedures",5, false));
    }

    public ToDo findById(long id) {
        return this.em.find(ToDo.class, id);
    }

    public void delete(long id) {
        try{
        ToDo reference = this.em.getReference(ToDo.class, id);
        this.em.remove(reference);
        } catch(EntityNotFoundException e) {
            //do nothing
        }
    }

    public List<ToDo> getAll() {
        return this.em.createNamedQuery(ToDo.findAll, ToDo.class).getResultList();
    }

    public ToDo save(ToDo todo) {
        return this.em.merge(todo);
    }

    public ToDo updateStatus(long id, boolean done) {
        ToDo todo = this.findById(id);
        if(todo == null)
        {
            return null;
        }
        todo.setDone(done);
        return todo;
    }
    
}
