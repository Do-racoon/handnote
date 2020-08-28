package com.capella.handnote.Interface.dto;

import com.capella.handnote.Domain.Content;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ContentResponseDto {
    private String id;
    private String title;
    private String text;

    public ContentResponseDto(Content content){
        this.id = content.getId();
        this.title = content.getTitle();
        this.text = content.getText();
    }

    public String getId(){
        return this.id;
    }
    public String getTitle(){
        return this.title;
    }
    public String getText(){
        return this.text;
    }
}
