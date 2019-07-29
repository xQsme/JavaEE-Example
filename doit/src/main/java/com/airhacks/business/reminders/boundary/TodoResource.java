/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.business.reminders.boundary;

import com.airhacks.business.reminders.entity.ToDo;
import javax.json.JsonObject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author jabroni
 */
public class TodoResource {
    long id;
    ToDoManager manager;

    public TodoResource(long id, ToDoManager manager) {
        this.id = id;
        this.manager = manager;
    }
    
    @DELETE
    public void delete()
    {
        manager.delete(id);
    }
    
    @PUT
    @Path("/status")
    public Response status(JsonObject statusUpdate)
    {
        if(!statusUpdate.containsKey("done"))
        {
            return Response.status(Response.Status.BAD_REQUEST).header("reason", "JSON should contain field done").build();
        }
        boolean done = statusUpdate.getBoolean("done");
        ToDo todo = manager.updateStatus(id, done);
        if(todo == null)
        {
            return Response.status(Response.Status.BAD_REQUEST).header("reason", "todo with id " + id + " does not exist").build();
        }
        return Response.ok(todo).build();
    }
    
    @GET
    public ToDo find()
    {
        return manager.findById(id);
    }
    
    @PUT
    public ToDo update(ToDo todo)
    {
        todo.setId(id);
        return manager.save(todo);
    }
}


