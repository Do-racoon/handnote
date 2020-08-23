package com.capella.handnote.Service;

import com.capella.handnote.Domain.ImageString;
import org.springframework.stereotype.Service;

@Service
public class RestTemplateService {
    private RestTemplateUtil restTemplateUtil;

    public RestTemplateService(RestTemplateUtil restTemplateUtil){
        this.restTemplateUtil = restTemplateUtil;
    }
    public String ImageToString(String url, ImageString imageString) throws Exception{
        return restTemplateUtil.SendImage(url, imageString);
    }
}
