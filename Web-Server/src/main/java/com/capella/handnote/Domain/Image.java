package com.capella.handnote.Domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;



@Data
public class Image implements Serializable {
    private MultipartFile img;
    private Integer highlight;
    private Integer index;

    public Image(MultipartFile img, Integer index, Integer highlight){
        this.img = img;
        this.index = index;
        this.highlight = highlight;
    }
}
