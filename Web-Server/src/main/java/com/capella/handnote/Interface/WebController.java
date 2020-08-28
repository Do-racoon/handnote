package com.capella.handnote.Interface;

import com.capella.handnote.Domain.ImageString;
import com.capella.handnote.Domain.TextInfo;
import com.capella.handnote.Service.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
public class WebController {
    private RestTemplateService restTemplateService;

    @Autowired
    public WebController(RestTemplateService restTemplateService){
        this.restTemplateService = restTemplateService;
    }

    // Form Data - Flask 서버로 전송 ( Image To String )
    @PostMapping("/fileupload")
    public String fileUpload(@RequestParam("convert") MultipartFile mFile){
        ImageString imageString = null;
        List<TextInfo> textInfo = null;
        Integer maxline = 1;
        String text = "";

        try{
            imageString = new ImageString(mFile.getOriginalFilename(),mFile);
            // Flask 서버로 요청 - 객체 ( 문자, 글자크기, 라인 )
            textInfo = restTemplateService.ImageToString("http://0.0.0.0:5000/img-string", imageString);
            // 최대 줄라인
            maxline = textInfo.stream().max(Comparator.comparingInt(TextInfo::getLine)).get().getLine();
        }catch (Exception err){
            err.printStackTrace();
        }
        // 라인에 있는 텍스트끼리 그룹핑
        Map<Integer, List<String>> str = textInfo.stream().collect(Collectors.groupingBy(
                                                                            TextInfo::getLine,
                                                                            Collectors.mapping(TextInfo::getText, Collectors.toList())));

        for(int i=1; i<=maxline; i++){
            text += String.join("", str.get(i)) + "\n";
        }
        return text;
    }

}
