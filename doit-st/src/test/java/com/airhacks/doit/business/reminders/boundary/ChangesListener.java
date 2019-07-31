/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.doit.business.reminders.boundary;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import sun.rmi.transport.Endpoint;

/**
 *
 * @author jabroni
 */
public class ChangesListener extends Endpoint{

    String message;
    CountDownLatch latch = new CountDownLatch(1);
    
    @Override
    public void onOpen(Session session, EndpointConfig config) {
        session.addMessageHandler(String.class, (msg) -> {
            message = msg;
            latch.countDown();
        });
    }
    
    public String getMessage() throws InterruptedException
    {
        latch.await(1, TimeUnit.MINUTES);
        return message;
    }
    
}
