package com.capella.handnote.Interface;

import com.capella.handnote.Domain.JsonVo;
import com.capella.handnote.Service.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiController {

    @Autowired
    private RestTemplateService restTemplateService;

    @GetMapping("/img-string")
    public JsonVo getJsonData(){
        return restTemplateService.getJsonData();
    }
}
