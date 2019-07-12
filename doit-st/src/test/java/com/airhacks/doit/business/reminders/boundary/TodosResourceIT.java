/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.doit.business.reminders.boundary;

import com.airhacks.rulz.jaxrsclient.JAXRSClientProvider;
import static com.airhacks.rulz.jaxrsclient.JAXRSClientProvider.buildWithURI;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;

/**
 *
 * @author jabroni
 */
public class TodosResourceIT {
    
    @Rule
    public JAXRSClientProvider provider = buildWithURI("http://localhost:8080/doit/api/todos");
    
    @Test
    public void crud()
    {
        //create
        JsonObjectBuilder todoBuilder = Json.createObjectBuilder();
        JsonObject todoToCreate = todoBuilder.add("caption", "implement").add("description", "REST").add("priority", (42)).build();
        Response postResponse = this.provider.target().request().post(Entity.json(todoToCreate));
        assertThat(postResponse.getStatus(), is(201));
        String location = postResponse.getHeaderString("Location");
 
        //find all
        Response response = this.provider.target().request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));
        
        JsonArray allTodos = response.readEntity(JsonArray.class);
        assertFalse(allTodos.isEmpty());
        
        //get first
        JsonObject todo = allTodos.getJsonObject(0);
        assertTrue(todo.getString("caption").startsWith("implement"));
        
        //get specific
        JsonObject dedicatedTodo = this.provider.client().target(location).request(MediaType.APPLICATION_JSON).get(JsonObject.class);
        assertTrue(dedicatedTodo.getString("caption").contains("implement"));

        //update
        JsonObject todoToUpdate = todoBuilder.add("caption", "implemented").add("description", "REST done").add("priority", (0)).build();
        this.provider.client().target(location).request(MediaType.APPLICATION_JSON).put(Entity.json(todoToUpdate));
        
        //find again
        JsonObject updatedTodo = this.provider.client().target(location).request(MediaType.APPLICATION_JSON).get(JsonObject.class);
        assertTrue(updatedTodo.getString("caption").contains("implemented"));
        
        //update status
        JsonObject statusUpdate = todoBuilder.add("done", true).build();
        this.provider.client().target(location).path("status").request(MediaType.APPLICATION_JSON).put(Entity.json(statusUpdate));
        
        //verify status
        JsonObject updatedStatus = this.provider.client().target(location).request(MediaType.APPLICATION_JSON).get(JsonObject.class);
        assertTrue(updatedStatus.getBoolean("done"));

        //update not existing status
        JsonObject notExistingUpdate = todoBuilder.add("done", true).build();
        response = this.provider.target().path("-42").path("status").request(MediaType.APPLICATION_JSON).put(Entity.json(notExistingUpdate));
        assertThat(response.getStatus(), is(400));
        assertFalse(response.getHeaderString("reason").isEmpty());
        
        //update malformed status
        notExistingUpdate = todoBuilder.add("something wrong", true).build();
        response = this.provider.client().target(location).path("status").request(MediaType.APPLICATION_JSON).put(Entity.json(notExistingUpdate));
        assertThat(response.getStatus(), is(400));
        assertFalse(response.getHeaderString("reason").isEmpty());
        
        //delete
        Response deleteResponse = this.provider.client().target(location).request(MediaType.APPLICATION_JSON).delete();
        assertThat(deleteResponse.getStatus(), is(204));
    }
}
