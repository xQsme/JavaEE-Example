/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.reminders.boundary;

import com.airhacks.reminders.entity.ToDo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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
    
    @PUT
    @Path("{id}")
    public ToDo update(@PathParam("id") long id, ToDo todo)
    {
        todo.setId(id);
        return manager.save(todo);
    }
    
    @PUT
    @Path("{id}/status")
    public Response status(@PathParam("id") long id, JsonObject statusUpdate)
    {
        if(!statusUpdate.containsKey("done"))
        {
            return Response.status(Response.Status.BAD_REQUEST).header("reason", "JSON should contain field done").build();
        }
        boolean done = statusUpdate.getBoolean("done");
        ToDo todo = manager.updateStatus(id,done);
        if(todo == null)
        {
            return Response.status(Response.Status.BAD_REQUEST).header("reason", "todo with id " + id + " does not exist").build();
        }
        return Response.ok(todo).build();
    }
    
    @GET
    public List<ToDo> all(){
        return manager.getAll();
    }
    
    @POST
    public Response save(ToDo todo, @Context UriInfo info){
        ToDo saved = manager.save(todo);
        long id = saved.getId();
        URI uri = info.getAbsolutePathBuilder().path("/"+id).build();
        return Response.created(uri).build();
    }
}
