package com.airhacks.business.reminders.entity;

import com.airhacks.business.ValidEntity;
import com.airhacks.business.validation.CrossCheck;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@NamedQuery(name = Profile.findAll, query = "SELECT p FROM Profile p")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@CrossCheck
public class Profile implements ValidEntity {

    @TableGenerator(name = "Profile_Gen", initialValue = 0)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Profile_Gen")
    private long id;

    private long index;

    public static final String PREFIX = "reminders.entity.Profile.";
    public static final String findAll = PREFIX + "findAll";

    @NotNull
    @Size(min = 2, max = 256)
    private String caption;

    @Column(length = 100000 )
    private String description;

    @Column( length = 1000 )
    private String image;

    @Version
    private long version;

    public Profile(String caption, String description, String image) {
        this.caption = caption;
        this.description = description;
        this.image = image;
    }

    public Profile() {
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public String getImageName() {
        String[] split = image.split("/");
        return split[split.length-1];
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public boolean isValid() {
        return !this.description.isEmpty() && !this.description.isEmpty() && !this.image.isEmpty();
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", caption='" + caption + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", version=" + version +
                '}';
    }
}
