package com.airhacks.presentation;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import java.util.ArrayList;
import java.util.List;

@Model
public class Index {

    private List<String> images;

    public List<String> getImages() {
        return images;
    }

    @PostConstruct
    public void init()
    {
        images = new ArrayList<>();
        for (int i = 1; i <= 14; i++) {
            images.add(i + "");
        }
    }
}
