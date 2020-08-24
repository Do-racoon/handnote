package com.jihun.handnote.springboot.service;

import com.jihun.handnote.springboot.domain.*;
import com.jihun.handnote.springboot.web.dto.ContentUpdateRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.ContextNotEmptyException;
import java.util.*;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ContentRepository contentRepository;

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
//    public Content findUserContent(String userId){return contentRepository.findByUserId(userId);}

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    // 작성한 글 저장
    public String saveContent(Content content){
        return contentRepository.save(content).getId();
    }
    // 작성한 글 정보 모두 가져오기
    public List<Content> findContentAll(String userId){
        return contentRepository.findAllByUserId(userId);
    }
    // content 정보 가져오기
    public Content findContent(String id){
        return contentRepository.findById(id).get();
    }
    // content 정보 업데이트 하기
    public void updateContent(String id, ContentUpdateRequestDto contentUpdateRequestDto){
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        content.update(contentUpdateRequestDto.getText(), contentUpdateRequestDto.getTitle());
        contentRepository.save(content);
    }
    // content 삭제하기
    public void deleteContent(String id){
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        contentRepository.delete(content);
    }
    // login
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user != null) {
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }

    // user role을 GrantedAuthority로 바꿔준다.
    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        });

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }
    // mongoDB를 Spring Security와 연결시켜준다.
    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
