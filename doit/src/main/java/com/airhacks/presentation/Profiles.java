package com.airhacks.presentation;

import com.airhacks.business.reminders.boundary.ProfileManager;
import com.airhacks.business.reminders.entity.Profile;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

@Model
public class Profiles {

    @Inject
    ProfileManager boundary;

    private Profile profile;

    private Profile profileToDelete;

    private UploadedFile file;

    String completePathFile;

    String filename;

    @Inject
    Validator validator;

    private String previous;

    @PostConstruct
    public void init()
    {
        this.profile = new Profile();
    }


    public List<Profile> getProfiles()
    {
        List<Profile> list = this.boundary.getAll();
        long index = 0;
        for (Profile p : list)
        {
            p.setIndex(index++);
        }
        return list;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfileToDelete() {
        return profileToDelete;
    }

    public void setProfileToDelete(Profile profileToDelete) {
        this.profileToDelete = profileToDelete;
    }

    private void showValidationError(String content)
    {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Validation", content);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void save()
    {
        if(completePathFile != null)
        {
            this.profile.setImage(completePathFile);
            Set<ConstraintViolation<Profile>> violations = this.validator.validate(this.profile);
            for(ConstraintViolation<Profile> violation : violations)
            {
                this.showValidationError(violation.getMessage());
            }
            if(violations.isEmpty())
            {
                profile.setId(0);
                this.boundary.save(profile);
                this.addMessage("Save Attempt", "Data Saved");
            }
        }
        else
        {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Must select a file");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void uploadReplace()
    {
        previous = profile.getImage();
        upload();
    }

    public void upload()
    {
        if(file.getFileName() != null)
        {
            try {
            filename = file.getFileName().substring(file.getFileName().lastIndexOf("\\") + 1);

            FacesContext context = FacesContext.getCurrentInstance();
            ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
            String path = servletContext.getRealPath("");
            completePathFile = path + File.separator + "resources" + File.separator + "images" + File.separator +  filename;

            InputStream in = file.getInputstream();
            FileOutputStream out = new FileOutputStream(completePathFile);

            byte[] b = new byte[1024];
            int readBytes = in.read(b);

            while (readBytes != -1) {
                out.write(b, 0, readBytes);
                readBytes = in.read(b);
            }
            in.close();
            out.close();

            } catch (IOException e) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "File: " + file.getFileName() + " not uploaded!");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    }

    public Object select()
    {
        this.addMessage("Edit", "Row Selected");
        return null;
    }

    public Object cancel()
    {
        this.addMessage("Edit", "Edition Canceled");
        return null;
    }

    public Object edit()
    {
        if(completePathFile != null && !completePathFile.equals(profile.getImage()))
        {
            File file = new File(previous);
            file.delete();
            this.profile.setImage(completePathFile);
        }
        Set<ConstraintViolation<Profile>> violations = this.validator.validate(this.profile);
        for(ConstraintViolation<Profile> violation : violations)
        {
            this.showValidationError(violation.getMessage());
        }
        if(violations.isEmpty()) {
            this.boundary.save(profile);
            this.addMessage("Edit", "Row Edited");
        }
        return null;
    }

    public Object delete()
    {
        File file = new File(profileToDelete.getImage());
        file.delete();
        this.boundary.delete(profileToDelete.getId());
        this.addMessage("Delete Attempt", "Row Deleted");
        return null;
    }

    private void addMessage(String title, String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, title, summary);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
