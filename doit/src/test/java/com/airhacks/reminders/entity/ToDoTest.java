/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.reminders.entity;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jabroni
 */
public class ToDoTest {
    
    @Test
    public void validTodo()
    {
        ToDo valid = new ToDo("", "available", 11);
        assertTrue(valid.isValid());
    }
    
    @Test
    public void invalidTodo()
    {
        ToDo valid = new ToDo("", null, 11);
        assertFalse(valid.isValid());
    }

    @Test
    public void todoWithoutDescription()
    {
        ToDo valid = new ToDo("implement", null, 10);
        assertTrue(valid.isValid());
    }
}
