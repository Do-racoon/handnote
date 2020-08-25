package com.capella.handnote.Interface;

import com.capella.handnote.Domain.Content;
import com.capella.handnote.Domain.User;
import com.capella.handnote.Interface.dto.ContentSaveRequestDto;
import com.capella.handnote.Interface.dto.ContentUpdateRequestDto;
import com.capella.handnote.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.ContextNotEmptyException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Stream;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }
    // 회원가입 페이지를 불러오는 것
    // 데이터 입력 받은 것을 가져오기 위해서 User 객체를 선언하는 것 같다.
    @GetMapping("/signup")
    public ModelAndView signup(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("signup");
        return modelAndView;
    }

   // @Valid로 데이터 검증, BindingResult도 같이 사용
   // 에러체크는 hasError로 한다.
    @PostMapping("/signup")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        User userExist = userService.findUserByEmail(user.getEmail());

        if(userExist != null){
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the username provided");
        }
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("signup");
        } else{
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    // 인증 후 화면
    @GetMapping("/dashboard")
    public ModelAndView dashboard(){
        ModelAndView modelAndView = new ModelAndView();
        // 인증정보를 가져온다.
        // SecurityContextHolder는 인증정보(authentication)를 가지고 있다.
        // 아래의 내용은 현재 사용자의 정보를 가져오는 것이다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(authentication.getName());
        List<Content> contents = userService.findContentAll(user.getId());
//        System.out.println("what : " + content.);

        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("name", user.getName());
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.addObject("contents", contents);
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }

    @GetMapping({"/","/home"})
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    // 새글 content 화면 불러오기
    @GetMapping("/content")
    public ModelAndView content(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("content-save");
        return modelAndView;
    }
    // 새글에서 content 저장
    @PostMapping("/content")
    public ModelAndView saveContent(@RequestBody ContentSaveRequestDto contentSaveRequestDto){
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(authentication.getName());
        Content content = new Content(user.getId(), contentSaveRequestDto.getText(), contentSaveRequestDto.getTitle());

//        content.setUserId(user.getId());
        userService.saveContent(content);
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }
    // 기존에 있던 content를 불러와서 편집하기
    @GetMapping("/content/{id}")
    public ModelAndView userContent(@PathVariable String id){
        ModelAndView modelAndView = new ModelAndView();
        // 저장된 content 가져오기(인자는 id를 통해서)
        Content content = userService.findContent(id);

        modelAndView.addObject("id", content.getId());
        modelAndView.addObject("title", content.getTitle());
        modelAndView.addObject("text", content.getText());
        modelAndView.setViewName("content-update");
        return modelAndView;
    }
    // content 업데이트
    @PutMapping("/content/{id}")
    public ModelAndView updateContent(@PathVariable String id, @RequestBody ContentUpdateRequestDto contentUpdateRequestDto){
        ModelAndView modelAndView = new ModelAndView();
        userService.updateContent(id, contentUpdateRequestDto);
        // 여기서 에러가 뜨는 경우가 있는데 그건 dashboard로 들어갈 값을 정해주지 않아서이다.
        modelAndView.setViewName("home");
        return modelAndView;
    }
    // 글 삭제
    @DeleteMapping("/content/{id}")
    public ModelAndView deleteContent(@PathVariable String id){
        ModelAndView modelAndView = new ModelAndView();

        userService.deleteContent(id);
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }
}
