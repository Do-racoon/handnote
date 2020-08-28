package com.capella.handnote.Domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;



@Data
public class ImageString implements Serializable {
    private MultipartFile img;
    private ArrayList<TextInfo> textinfo;

    public ImageString(String img_name, MultipartFile img){
        this.img = img;
    }
}
