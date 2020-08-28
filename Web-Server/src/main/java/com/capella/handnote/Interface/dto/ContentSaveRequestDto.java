package com.capella.handnote.Interface.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ContentSaveRequestDto {
    private String text;
    private String title;

    @Builder
    public ContentSaveRequestDto(String text, String title){
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
