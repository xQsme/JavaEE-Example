package com.airhacks.business.reminders.boundary;

import com.airhacks.business.reminders.entity.Profile;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Stateless
@Path("profiles")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ProfilesResource {

    @Inject
    ProfileManager manager;

    @Path("{id}")
    public ProfileResource find(@PathParam("id") long id)
    {
        return new ProfileResource(id, manager);
    }

    @GET
    public List<Profile> all(){
        return manager.getAll();
    }

    @POST
    public Response save(@Valid Profile profile, @Context UriInfo info){
        Profile saved = manager.save(profile);
        long id = saved.getId();
        URI uri = info.getAbsolutePathBuilder().path("/"+id).build();
        return Response.created(uri).build();
    }
}