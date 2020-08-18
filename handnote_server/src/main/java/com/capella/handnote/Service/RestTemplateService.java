package com.capella.handnote.Service;

import com.capella.handnote.Domain.JsonVo;
import com.capella.handnote.Service.RestTemplateUtil;
import org.springframework.stereotype.Service;

@Service
public class RestTemplateService {
    public JsonVo getJsonData(){
        return RestTemplateUtil.getJsonResponse();
    }
}
