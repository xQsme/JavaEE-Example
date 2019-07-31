/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.doit.business.reminders.boundary;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import javax.json.JsonObject;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.WebSocketContainer;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jabroni
 */
public class ToDoChangeTrackerIT {
    
    private WebSocketContainer container;
    private ChangesListener listener;
    
    @Before
    public void initCountainer() throws URISyntaxException, DeploymentException, IOException, IOException {
        this.container = ContainerProvider.getWebSocketContainer();
        URI uri = new URI("ws://localhost:8080/doit/changes");
        this.listener = new ChangesListener();
        ClientEndpointConfig cec = ClientEndpointConfig.Builder.create().decoders(Arrays.asList(JsonDecoder.class)).build();
        this.container.connectToServer(this.listener, uri);
    }
    
    @Test
    public void receiveNotifications() throws InterruptedException 
    {
        JsonObject message = this.listener.getMessage();
        assertNotNull(message);
        System.out.println(" " + message);
    }
    
}
