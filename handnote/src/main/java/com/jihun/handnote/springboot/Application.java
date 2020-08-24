package com.jihun.handnote.springboot;

import com.jihun.handnote.springboot.domain.Role;
import com.jihun.handnote.springboot.domain.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

// @SpringBootApplication이 있는 위치부터 설정을 읽어간다. 그래서 항상프로젝트 최상단에 있어야한다.
@SpringBootApplication
public class Application {
    public static void main(String[] args){
        // 이것으로 인해 내장 WAS를 사용할 수 있게 된다.
        // 이것을 사용함으로서 이점은 언제 어디서나 배포하기 편하다는 것이다.
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner init(RoleRepository roleRepository) {
        return args -> {
            Role adminRole = roleRepository.findByRole("ADMIN");
            if (adminRole == null) {
                Role newAdminRole = new Role();
                newAdminRole.setRole("ADMIN");
                roleRepository.save(newAdminRole);
            }

            Role userRole = roleRepository.findByRole("USER");
            if (userRole == null) {
                Role newUserRole = new Role();
                newUserRole.setRole("USER");
                roleRepository.save(newUserRole);
            }
        };
    }

}

// CommandLineRunner는 서버 구동 시점에 초기화 작업으로 무언가를 넣을 때 사용하는 방법이다.
// @Component로도 가능하다.
// 여기서는
