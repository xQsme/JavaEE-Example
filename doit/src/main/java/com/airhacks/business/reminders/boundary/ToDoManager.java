/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.business.reminders.boundary;

import com.airhacks.business.logging.boundary.BoundaryLogger;
import com.airhacks.business.reminders.entity.ToDo;
import java.util.List;
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
