/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.doit.business.reminders.boundary;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;
import javax.json.JsonObject;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

/**
 *
 * @author jabroni
 */
public class ChangesListener extends Endpoint{

    JsonObject message;
    CountDownLatch latch = new CountDownLatch(1);
    
    @Override
    public void onOpen(Session session, EndpointConfig config) {
        session.addMessageHandler(JsonObject.class, (msg) -> {
            message = msg;
            latch.countDown();
        });
    }
    
    public JsonObject getMessage() throws InterruptedException
    {
        latch.await(1, TimeUnit.MINUTES);
        return message;
    }
    
}
