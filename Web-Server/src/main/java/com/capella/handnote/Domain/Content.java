package com.capella.handnote.Domain;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//user_id, text, title
@NoArgsConstructor
@Document(collection = "content")
public class Content {
    @Id
    private String id;

    private String userId;// 해당 글의 작성자
    private String text;
    private String title;

    public String getId(){
        return this.id;
    }
    public String getUserId(){
        return this.userId;
    }
    public String getText(){
        return this.text;
    }
    public String getTitle(){
        return this.title;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }
    public void setText(String title){
        this.title = title;
    }
    public void setTitle(String text){
        this.text = text;
    }

    @Builder
    public Content(String userId, String text, String title) {
        this.userId = userId;
        this.text = text;
        this.title = title;
    }

    public void update(String text, String title){
        this.text = text;
        this.title = title;
    }
}
