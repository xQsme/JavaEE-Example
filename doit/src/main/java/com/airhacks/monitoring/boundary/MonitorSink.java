/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.monitoring.boundary;

import com.airhacks.logging.boundary.LogSink;
import com.airhacks.monitoring.entity.CallEvent;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 *
 * @author jabroni
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class MonitorSink
{
    @Inject
    LogSink LOG;
    
    public void onCallEvent(@Observes CallEvent event)
    {
        LOG.log(event.toString());
    }
}
