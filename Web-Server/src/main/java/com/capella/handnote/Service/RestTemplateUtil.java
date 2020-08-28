package com.capella.handnote.Service;

import com.capella.handnote.Config.MultiValueMapConverter;
import com.capella.handnote.Domain.Image;
import com.capella.handnote.Domain.TextInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;

@Component
public class RestTemplateUtil {
    private static RestTemplate restTemplate;

    @Autowired
    public RestTemplateUtil(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    // Flask에 Image 전송 , TextInfo 반환
    public List<TextInfo> SendImage(String url, Image image) throws Exception{
        URI uri = new URI(url);
        MultiValueMap multiValueMap = new MultiValueMapConverter(image).convert();
        TextInfo[] resultClasses = restTemplate.postForObject(uri, multiValueMap, TextInfo[].class);
        List<TextInfo> list = Arrays.asList(resultClasses);

        return list;
    }
}
