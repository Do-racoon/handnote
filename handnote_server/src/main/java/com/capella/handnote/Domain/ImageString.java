package com.capella.handnote.Domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
@Builder
public class ImageString {
    private MultipartFile img;
}
