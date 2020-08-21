package com.capella.handnote.Interface;

import com.capella.handnote.Domain.ImageString;
import com.capella.handnote.Service.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpRequest;

@Controller
public class WebController {
    private RestTemplateService restTemplateService;

    @Autowired
    public WebController(RestTemplateService restTemplateService){
        this.restTemplateService = restTemplateService;
    }

    // application.properties => spring.mvc.view.suffix=.html
    // .html 생략 가능
    @GetMapping("/fileadd")
    public String fileAdd(){
        return "fileAdd";
    }

    // Form Data - Flask 서버로 전송 ( Image To String )
    @PostMapping("/fileupload")
    public String fileUpload(@RequestParam("report") MultipartFile mFile){

        try{
            ImageString imageString = ImageString.builder()
                                                .img(mFile).build();
            // Flask 서버로 전달 - 객체
            restTemplateService.ImageToString("http://localhost:5000/img-string", imageString);
        }catch (Exception err){
            err.printStackTrace();
        }

        return "redirect:/fileadd";
    }
}
