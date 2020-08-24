package com.capella.handnote.Domain;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;


@Document(collection = "user")
@Data
public class User {
    @Id
    private String id;
    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
    private String email;
    private String password;
    private String name;
    @DBRef
    private Set<Role> roles;

    public String getId(){
        return this.id;
    }
    public String getEmail(){
        return this.email;
    }
    public String getName(){
        return this.name;
    }

    public String getPassword(){
        return this.password;
    }
    public Set<Role> getRoles(){
        return this.roles;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setRoles(Set<Role> roles){
        this.roles = roles;
    }
}
