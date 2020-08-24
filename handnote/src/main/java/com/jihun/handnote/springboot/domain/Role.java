package com.jihun.handnote.springboot.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "role")
public class Role {
    @Id
    private String id;
    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
    private String role;

    public String getId(){
        return this.id;
    }
    public String getRole(){
        return this.role;
    }
    public void setId(String id){ this.id = id;}
    public void setRole(String role){this.role = role;}
}
