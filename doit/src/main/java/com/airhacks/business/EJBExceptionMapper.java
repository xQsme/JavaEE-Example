/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.airhacks.business;

import javax.ejb.EJBException;
import javax.persistence.OptimisticLockException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author jabroni
 */
@Provider
public class EJBExceptionMapper implements ExceptionMapper<EJBException>
{
    @Override
    public Response toResponse(EJBException ex)
    {
        Throwable cause = ex.getCause();
        Response unknownErorr = Response.serverError().header("cause", ex.toString()).build();
        if(cause instanceof OptimisticLockException){
            return Response.status(Response.Status.CONFLICT).header("cause", "Conflict ocurred: " + cause).build();
        }
        return unknownErorr;
    }
}
