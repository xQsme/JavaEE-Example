/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.reminders.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author jabroni
 */
@Path("todos")
public class TodosResource
{
    @GET
    public String hello()
    {
        return "Hey" + System.currentTimeMillis();
    }
}
