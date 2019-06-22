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
        JsonObjectBuilder todoBuilder = Json.createObjectBuilder();
        JsonObject todoToCreate = todoBuilder.add("caption", "implement").add("description", "REST").add("priority", (42)).build();
        
        Response postResponse = this.provider.target().request().post(Entity.json(todoToCreate));
        assertThat(postResponse.getStatus(), is(204));
        
        Response response = this.provider.target().request(MediaType.APPLICATION_JSON).get();
        assertThat(response.getStatus(), is(200));
        
        JsonArray allTodos = response.readEntity(JsonArray.class);
        assertFalse(allTodos.isEmpty());
        
        JsonObject todo = allTodos.getJsonObject(0);
        assertTrue(todo.getString("caption").startsWith("implement"));
        
        JsonObject dedicatedTodo = this.provider.target().path("1").request(MediaType.APPLICATION_JSON).get(JsonObject.class);
        assertTrue(dedicatedTodo.getString("caption").contains("implement"));

        Response deleteResponse = this.provider.target().path("1").request(MediaType.APPLICATION_JSON).delete();
        assertThat(deleteResponse.getStatus(), is(204));
    }
}
