package com.capella.handnote.Interface;

import com.capella.handnote.Domain.Image;
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
    public String fileUpload(MultipartFile mFile){
        Image image = null;         // 이미지 파일
        List<TextInfo> textInfo = null;     // Request 텍스트 정보
        Integer maxline = 1;                // 최대 라인 수
        String text = "";                   // 출력할 텍스트(HTML)

        try{
            System.out.println(mFile);
            image = new Image(mFile.getOriginalFilename(),mFile);
            // Flask 서버로 요청 - 객체 ( 문자, 글자크기, 라인 )
            textInfo = restTemplateService.ImageToString("http://0.0.0.0:5000/img-string", image);
            // 최대 줄라인
            maxline = textInfo.stream().max(Comparator.comparingInt(TextInfo::getLine)).get().getLine();

            // 라인에 있는 텍스트끼리 그룹핑
            Map<Integer, List<String>> str = textInfo.stream().collect(Collectors.groupingBy(
                    TextInfo::getLine,
                    Collectors.mapping(TextInfo::getText, Collectors.toList())));

            // 해당 라인에 맞게 String을 합해서주고 html 개행문자 삽입
            for(int i=1; i<=maxline; i++){
                text += String.join("", str.get(i)) + "<br>";
            }
        }catch (Exception err){
            err.printStackTrace();
        }

        return text;
    }

}
