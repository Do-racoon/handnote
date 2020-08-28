package com.capella.handnote.Interface;

import com.capella.handnote.Domain.Content;
import com.capella.handnote.Interface.dto.ContentResponseDto;
import com.capella.handnote.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentController {
    @Autowired
    private UserService userService;

    @GetMapping("/content/{id}")
    public ContentResponseDto contentResponse(@PathVariable String id){
        return userService.findById(id);
    }
}
