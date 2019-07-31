/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.business.reminders.boundary;

import com.airhacks.business.encoders.JsonEncoder;
import com.airhacks.business.reminders.entity.ToDo;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.EncodeException;

/**
 *
 * @author jabroni
 */
@Singleton
@ServerEndpoint(value = "/changes", encoders = {JsonEncoder.class})
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class ToDoChangeTracker {

    private Session session;

    @OnOpen
    public void onOpen(Session session)
    {
        this.session = session;
    }

    @OnClose
    public void onClose()
    {
        this.session=null;
    }

    public void onToDoChange(@Observes(during = TransactionPhase.AFTER_SUCCESS) @ChangeEvent(ChangeEvent.Type.CREATION) ToDo todo) throws EncodeException
    {
        if(this.session != null && this.session.isOpen())
        {
            try {
                JsonObject event = Json.createObjectBuilder().add("id", todo.getId()).add("cause", "creation").build();
                this.session.getBasicRemote().sendObject(event);
            } catch (IOException e){
            }
        }
    }

}

