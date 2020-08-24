package com.capella.handnote;

import com.capella.handnote.Domain.Role;
import com.capella.handnote.Domain.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HandnoteApplication {
	public static void main(String[] args) {
		SpringApplication.run(HandnoteApplication.class, args);
	}

	// 이것으로 인해 내장 WAS를 사용할 수 있게 된다.
	// 이것을 사용함으로서 이점은 언제 어디서나 배포하기 편하다는 것이다.
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
