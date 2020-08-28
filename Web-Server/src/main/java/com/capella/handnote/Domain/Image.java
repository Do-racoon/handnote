package com.capella.handnote.Domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;



@Data
public class Image implements Serializable {
    private MultipartFile img;

    public Image(String img_name, MultipartFile img){
        this.img = img;
    }
}
