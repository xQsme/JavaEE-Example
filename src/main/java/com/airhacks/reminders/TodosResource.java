package com.airhacks.reminders;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("todos")
public class TodosResource
{
    @GET
    public String hello()
    {
        return "hey " + System.currentTimeMillis();
    }
}
