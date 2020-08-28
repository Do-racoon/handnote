package com.capella.handnote.Service;

import com.capella.handnote.Domain.Image;
import com.capella.handnote.Domain.TextInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestTemplateService {
    private RestTemplateUtil restTemplateUtil;

    public RestTemplateService(RestTemplateUtil restTemplateUtil){
        this.restTemplateUtil = restTemplateUtil;
    }

    // Image2String 서비
    public List<TextInfo> ImageToString(String url, Image image) throws Exception{
        return restTemplateUtil.SendImage(url, image);
    }
}
