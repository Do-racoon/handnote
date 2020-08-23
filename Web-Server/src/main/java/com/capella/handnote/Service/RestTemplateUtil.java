package com.capella.handnote.Service;

import com.capella.handnote.Config.MultiValueMapConverter;
import com.capella.handnote.Domain.ImageString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Component
public class RestTemplateUtil {
    private static RestTemplate restTemplate;

    @Autowired
    public RestTemplateUtil(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    public static String getJsonResponse(){
        return restTemplate.getForObject("http://localhost:5000/img-string", String.class);
    }

    public String SendImage(String url, ImageString imageString) throws Exception{
        URI uri = new URI(url);
        MultiValueMap multiValueMap = new MultiValueMapConverter(imageString).convert();
        return restTemplate.postForObject(uri, multiValueMap, String.class);
    }
}
