/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.reminders.boundary;

import com.airhacks.reminders.entity.ToDo;
import java.util.ArrayList;
import java.util.List;
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
@Path("todos")
public class TodosResource
{
    @GET
    @Path("{id}")
    public ToDo find(@PathParam("id") long id)
    {
        return new ToDo("implement REST endpoint " + id, "...", 100);
    }
    
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") long id)
    {
        System.out.println("deleted = " + id);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ToDo> all(){
        List<ToDo> all = new ArrayList<>();
        all.add(find(42));
        return all;
    }
    
    @POST
    public void save(ToDo todo){
        System.out.println("todo = " + todo);
    }
}
