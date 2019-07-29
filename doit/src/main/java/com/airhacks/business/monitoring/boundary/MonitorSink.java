/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.business.monitoring.boundary;

import com.airhacks.business.logging.boundary.LogSink;
import com.airhacks.business.monitoring.entity.CallEvent;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

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

    CopyOnWriteArrayList<CallEvent> recentEvents;

    @PostConstruct
    public void init()
    {
        this.recentEvents = new CopyOnWriteArrayList<>();
    }
    
    public void onCallEvent(@Observes CallEvent event)
    {
        LOG.log(event.toString());
        this.recentEvents.add(event);
    }

    public List<CallEvent> getRecentEvents()
    {
        return this.recentEvents;
    }

    public LongSummaryStatistics getStatistics()
    {
        return this.recentEvents.stream().collect(Collectors.summarizingLong(CallEvent::getDuration));
    }
}
