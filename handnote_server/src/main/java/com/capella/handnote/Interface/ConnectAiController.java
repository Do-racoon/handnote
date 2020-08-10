package com.capella.handnote.Interface;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpRequest;

@Controller
public class ConnectAiController {

    @RequestMapping("/ai-result")
    public RedirectView result(@RequestParam Integer A, @RequestParam Integer B){
        RedirectView redirectView = new RedirectView();
        String url = "http://localhost:8000/plus?x=" + A + "&y=" + B;
        redirectView.setUrl(url);
        return redirectView;
    }

    // application.properties => spring.mvc.view.suffix=.html
    // .html 생략 가능
    @GetMapping("/fileadd")
    public String fileAdd(){
        return "fileAdd";
    }

    @RequestMapping("/fileupload")
    public String fileUpload(HttpRequest request, @RequestParam("report") MultipartFile mFile){
        try{
            mFile.transferTo(new File("/home/jeong/Downloads/" + mFile.getOriginalFilename()));
        }catch (IllegalStateException | IOException e){
            e.printStackTrace();
        }
        return "fileUpload";
    }
}
