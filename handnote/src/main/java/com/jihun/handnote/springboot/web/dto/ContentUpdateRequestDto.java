package com.jihun.handnote.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContentUpdateRequestDto {
    private String text;
    private String title;

    @Builder
    public ContentUpdateRequestDto(String text, String title){
        this.text = text;
        this.title = title;
    }

    public String getText(){
        return this.text;
    }
    public String getTitle(){
        return this.title;
    }
}
