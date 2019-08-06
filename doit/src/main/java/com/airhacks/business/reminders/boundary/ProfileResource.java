package com.airhacks.business.reminders.boundary;

import com.airhacks.business.reminders.entity.Profile;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;

public class ProfileResource {

    long id;
    ProfileManager manager;

    public ProfileResource(long id, ProfileManager manager) {
        this.id = id;
        this.manager = manager;
    }

    @DELETE
    public void delete()
    {
        manager.delete(id);
    }

    @GET
    public Profile find()
    {
        return manager.findById(id);
    }

    @PUT
    public Profile update(Profile profile)
    {
        profile.setId(id);
        return manager.save(profile);
    }
}
