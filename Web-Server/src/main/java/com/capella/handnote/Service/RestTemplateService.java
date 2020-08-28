package com.capella.handnote.Service;

import com.capella.handnote.Domain.ImageString;
import com.capella.handnote.Domain.TextInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestTemplateService {
    private RestTemplateUtil restTemplateUtil;

    public RestTemplateService(RestTemplateUtil restTemplateUtil){
        this.restTemplateUtil = restTemplateUtil;
    }
    public List<TextInfo> ImageToString(String url, ImageString imageString) throws Exception{
        return restTemplateUtil.SendImage(url, imageString);
    }
}
