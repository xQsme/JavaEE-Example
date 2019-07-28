/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.logging;

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
    LogSink LOG;
    
    @AroundInvoke
    public Object logCall(InvocationContext ic) throws Exception
    {
        LOG.log("--" + ic.getMethod());
        return ic.proceed();
    }
}
