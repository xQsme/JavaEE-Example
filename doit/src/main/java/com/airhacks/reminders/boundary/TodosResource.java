/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.reminders.boundary;

import com.airhacks.reminders.entity.ToDo;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author jabroni
 */
@Stateless
@Path("todos")
public class TodosResource
{
    @Inject
    ToDoManager manager;
    
    @GET
    @Path("{id}")
    public ToDo find(@PathParam("id") long id)
    {
        return manager.findById(id);
    }
    
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") long id)
    {
        manager.delete(id);
    }
    
    @GET
    public List<ToDo> all(){
        return manager.getAll();
    }
    
    @POST
    public void save(ToDo todo){
        manager.save(todo);
    }
}
