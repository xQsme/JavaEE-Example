/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.business.logging.boundary;

import com.airhacks.business.monitoring.entity.CallEvent;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 *
 * @author jabroni
 */
public class BoundaryLogger 
{
    @Inject
    Event<CallEvent> monitoring;
    
    @AroundInvoke
    public Object logCall(InvocationContext ic) throws Exception
    {
        long start = System.currentTimeMillis();
        try
        {
            return ic.proceed();
        }
        finally
        {
            long duration = System.currentTimeMillis() - start;
            monitoring.fire(new CallEvent(ic.getMethod().getName(), duration));
        }
    }
}
