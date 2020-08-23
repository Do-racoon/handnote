package com.capella.handnote.Domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.Serializable;

@Data
public class ImageString implements Serializable {
    private MultipartFile img;
    private String img_name;

    public ImageString(String img_name, MultipartFile img){
        this.img_name = img_name;
        this.img = img;
    }
}
