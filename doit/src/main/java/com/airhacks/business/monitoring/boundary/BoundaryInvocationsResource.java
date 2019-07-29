/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.business.monitoring.boundary;

import com.airhacks.business.monitoring.entity.CallEvent;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 *
 * @author jabroni
 */
@Stateless
@Path("boundary-invocations")
public class BoundaryInvocationsResource {
    
    @Inject
    MonitorSink ms;
    
    @GET
    public List<CallEvent> expose()
    {
        return this.ms.getRecentEvents();
    }
}
